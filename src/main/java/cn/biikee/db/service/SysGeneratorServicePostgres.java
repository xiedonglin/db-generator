package cn.biikee.db.service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.biikee.db.config.PostgresConfig;
import cn.biikee.db.dao.SysGeneratorDaoPostgres;
import cn.biikee.db.entity.ColumnEntity;
import cn.biikee.db.utils.Constant;
import cn.biikee.db.utils.DateUtils;
import cn.biikee.db.utils.FormatDirective;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 * 代码生成器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月19日 下午3:33:38
 */
@Service
public class SysGeneratorServicePostgres {
    @Autowired
    private SysGeneratorDaoPostgres sysGeneratorDao;
    @Autowired
    private PostgresConfig cfg;

    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return sysGeneratorDao.queryList(map);
    }

    public int queryTotal(Map<String, Object> map) {
        return sysGeneratorDao.queryTotal(map);
    }

    public List<Map<String, String>> queryTable(String schema) {
        return sysGeneratorDao.queryTable(schema);
    }

    public List<Map<String, String>> queryColumns(String tablename) {
        return sysGeneratorDao.queryColumns(tablename);
    }

    public byte[] generatorCode(String[] schemas) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        try {
            // 创建配置实例
            Configuration configuration = new Configuration(new Version("2.3.26"));
            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
            // ftl模板文件
            configuration.setClassForTemplateLoading(SysGeneratorServicePostgres.class, "/template/");
            configuration.setSharedVariable(FormatDirective.DIRECTIVE_NAME, new FormatDirective());
            Map<String, Object> map = new HashMap<>();
            map.put("packagename", cfg.getPackagename());

            // 获取模板
            Template template = null;
            template = configuration.getTemplate(Constant.POSTGRES_BASEMOD);
            // 输出文件
            File outFile = new File(getFileName(Constant.POSTGRES_BASEMOD, "",
                cfg.getPackagename(), ""));
            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            // 将模板和数据模型合并生成文件
            Writer out;
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            // 生成文件
            try {
                template.process(map, out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            // 关闭流
            out.flush();
            out.close();
            
            // 获取模板
            template = configuration.getTemplate(Constant.POSTGRES_BASEANNO);
            // 输出文件
            outFile = new File(getFileName(Constant.POSTGRES_BASEANNO, "",
                cfg.getPackagename(), ""));
            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
           
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            // 生成文件
            try {
                template.process(map, out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            // 关闭流
            out.flush();
            out.close();            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String schema : schemas) {
            // 查询表信息
            List<Map<String, String>> tables = queryTable(schema);
            // 生成代码
            for (Map<String, String> table : tables) {
                // 查询列信息
                List<Map<String, String>> columns = queryColumns(table.get("tablename"));
                Map<String, String> params = new HashMap<String, String>();
                params.put("tablename", table.get("tablename"));
                List<Map<String, String>> seqcolName = sysGeneratorDao.getSeqs(params);
                String seqcolumnname = "";
                if (!seqcolName.isEmpty()) {
                    seqcolumnname = seqcolName.get(0).get("seqcolumnname");
                }
                generatorPostgres(table, columns, cfg, seqcolumnname);
            }
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    private Pattern pattern = Pattern.compile("(.*?)_.");
    private PostgresConfig config;

    public List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(Constant.POSTGRES_XML);
        templates.add(Constant.POSTGRES_MOD);
        templates.add(Constant.POSTGRES_DAO);

        return templates;
    }

    public void generatorPostgres(Map<String, String> table, List<Map<String, String>> columns, PostgresConfig cfg,
        String seqcolName) {
        config = cfg;
        // 表的列名(不包含主键)
        List<ColumnEntity> tablecolumns = new ArrayList<>();

        // 表的主Key
        List<ColumnEntity> tablekeycolumns = new ArrayList<>();

        // 列信息
        for (Map<String, String> column : columns) {
            if (column.get("datatype").contains("_")) {
                continue;
            }

            ColumnEntity c = new ColumnEntity();
            c.setColumnName(column.get("columnname"));
            c.setDataType(column.get("datatype"));
            c.setComments(column.get("columncomment"));
            c.setIsKey(column.get("iskey"));
            // 列名转换成Java属性名
            c.setAttrname(c.getColumnName());
            c.setJavaGetSetName(columnToJava(c.getColumnName()));
            // 列的数据类型，转换成Java类型
            c.setDbType(c.getDataType());
            c.setJdbcType(config.getJdbcType().get(c.getDataType()));
            c.setJavaType(config.getJavaType().get(c.getDataType()));

            // 保存主键
            if ("1".equals(column.get("iskey"))) {
                tablekeycolumns.add(c);
            }

            // 保存字段
            tablecolumns.add(c);
        }
        // 封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("classname", delUnderline((table.get("tablename").toUpperCase())));
        map.put("tableName", table.get("tablename").toLowerCase());
        map.put("comments", table.get("tablecomment"));
        map.put("columns", tablecolumns);
        map.put("keycolumns", tablekeycolumns);
        map.put("packagename", config.getPackagename());
        map.put("seqcolumnName", seqcolName);

        // 内容 与 匹配规则 的测试
        Matcher matcher = pattern.matcher(table.get("tablename"));
        if (matcher.find()) {
            map.put("moduleName", matcher.group(1));
        } else {
            map.put("moduleName", "x");
        }
        map.put("author", config.getAuthor());
        map.put("email", config.getEmail());
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        try {
            // 创建配置实例
            Configuration configuration = new Configuration(new Version("2.3.26"));
            // 设置编码
            configuration.setDefaultEncoding("UTF-8");
            // ftl模板文件
            configuration.setClassForTemplateLoading(SysGeneratorServicePostgres.class, "/template/");
            configuration.setSharedVariable(FormatDirective.DIRECTIVE_NAME, new FormatDirective());
            // 获取模板
            Template template = null;
            for (String str : getTemplates()) {
                template = configuration.getTemplate(str);
                // 输出文件
                File outFile = new File(getFileName(str, map.get("classname").toString(), config.getPackagename(),
                    map.get("moduleName").toString()));
                // 如果输出目标文件夹不存在，则创建
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
                // 将模板和数据模型合并生成文件
                Writer out;
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
                // 生成文件
                try {
                    template.process(map, out);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                // 关闭流
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 列名转换成Java属性名
     */
    public String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[] {'_'}).replace("_", "");
    }

    /**
     * 类名转换成Java名
     */
    public String classToJava(String className) {
        String classnm = "";
        String rex = "[a-z]*[^_]";
        Matcher m = Pattern.compile(rex).matcher(className);
        if (m.matches()) {
            if (m.groupCount() == 3) {
                classnm = classnm + WordUtils.capitalizeFully(m.group(1), new char[] {'_'}) + "_";
                classnm = classnm + m.group(2) + "_";
                classnm = classnm + WordUtils.capitalizeFully(m.group(3), new char[] {'_'}) + "_";
            } else {
                classnm = classnm + WordUtils.capitalizeFully(m.group(1), new char[] {'_'}) + "_";
                classnm = classnm + WordUtils.capitalizeFully(m.group(2), new char[] {'_'}) + "_";
            }
        }
        return classnm.substring(0, classnm.length() - 1);
    }

    public String delUnderline(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return WordUtils.capitalizeFully(result.toString());
    }

    /**
     * 表名转换成Java类名
     */
    public String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取文件名
     */
    public String getFileName(String template, String className, String packageName, String moduleName) {
        String packagePath = cfg.getSrcPath() + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains(Constant.POSTGRES_BASEANNO)) {
            return packagePath + "annotation" + File.separator + "PrimaryKey.java";
        }
        
        if (template.contains(Constant.POSTGRES_BASEMOD)) {
            return packagePath + "model" + File.separator + "BaseModel.java";
        }

        if (template.contains(Constant.POSTGRES_MOD)) {
            return packagePath + "model" + File.separator + moduleName + File.separator + className + "Model.java";
        }

        if (template.contains(Constant.POSTGRES_DAO)) {
            return packagePath + "dao" + File.separator + moduleName + File.separator + className + "Dao.java";
        }

        if (template.contains(Constant.POSTGRES_XML)) {
            return config.getSrcPath() + File.separator + "main" + File.separator + "resources" + File.separator + "sql"
                + File.separator + "common" + File.separator + moduleName + File.separator + className + "Dao.xml";
        }

        return null;
    }

}
