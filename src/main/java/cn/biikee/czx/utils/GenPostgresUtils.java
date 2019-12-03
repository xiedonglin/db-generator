package cn.biikee.czx.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.biikee.czx.config.PostgresConfig;
import cn.biikee.czx.entity.ColumnEntity;
import cn.biikee.czx.entity.TableEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 代码生成器 工具类
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenPostgresUtils {
    // 匹配规则
    private Pattern pattern = Pattern.compile("(.*?)_.");
    private PostgresConfig config;

    public List<String> getTemplates() {
        List<String> templates = new ArrayList<String>();
        templates.add("postgres.dao.xml.ftl");
        templates.add("postgres.model.java.ftl");
        templates.add("postgres.dao.java.ftl");

        return templates;
    }

    public void generatorPostgres(Map<String, String> table, List<Map<String, String>> columns,PostgresConfig cfg) {
        config = cfg;
        // 表的列名(不包含主键)
        List<ColumnEntity> tablecolumns = new ArrayList<ColumnEntity>();

        // 表的主Key
        List<ColumnEntity> tablekeycolumns = new ArrayList<ColumnEntity>();

        // 列信息
        for (Map<String, String> column : columns) {
            System.out.println(column.get("columnname") + ":" + column.get("datatype"));
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
            System.out.println(c.getDataType() + "-----------------" + config.getJavaType().get(c.getDataType()));
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
//        map.put("classname", table.get("tablename").toUpperCase());
        map.put("classname", UnderlineToHump((table.get("tablename").toUpperCase())));
        map.put("tableName", table.get("tablename").toLowerCase());
        map.put("comments", table.get("tablecomment"));
        map.put("columns", tablecolumns);
        map.put("keycolumns", tablekeycolumns);
        map.put("packagename", config.getPackagename());

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
            Configuration configuration = new Configuration();

            // 设置编码
            configuration.setDefaultEncoding("UTF-8");

            // ftl模板文件
            configuration.setClassForTemplateLoading(GenPostgresUtils.class, "/template/");

            // 获取模板
            Template template = null;
            for (String str : getTemplates()) {
                template = configuration.getTemplate(str);
                // 输出文件
                System.out.println(getFileName(str, map.get("classname").toString(), config.getPackagename(),
                    map.get("moduleName").toString()));
                File outFile = new File(getFileName(str, map.get("classname").toString(), config.getPackagename(),
                    map.get("moduleName").toString()));
                // 如果输出目标文件夹不存在，则创建
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }
                // 将模板和数据模型合并生成文件
                Writer out;
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
                // 生成文件
                template.process(map, out);
                // 关闭流
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TemplateException e) {
            System.out.println(e.getMessage());
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
        Pattern pattern = Pattern.compile("[a-z]*[^_]");
        Matcher m = pattern.matcher(className);
        if (m.matches()) {
            if (m.groupCount() == 3) {
                classnm = classnm + WordUtils.capitalizeFully(m.group(1), new char[] {'_'}) + "_";
                classnm = classnm + m.group(2) + "_";
                classnm = classnm + WordUtils.capitalizeFully(m.group(3), new char[] {'_'}) + "_";
            }else {
                classnm = classnm + WordUtils.capitalizeFully(m.group(1), new char[] {'_'}) + "_";
                classnm = classnm + WordUtils.capitalizeFully(m.group(2), new char[] {'_'}) + "_";
            }
        }
        // while (m.find()) {
        // classnm = classnm + WordUtils.capitalizeFully(m.group(), new char[] {'_'}) + "_";
        // }
        //CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "aaa_name")-->aaaName
        return classnm.substring(0, classnm.length() - 1);
    }
    public String UnderlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
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
        String packagePath = config.getSrcPath() + File.separator + "main" + File.separator
            + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("postgres.model.java.ftl")) {
            return packagePath + "model" + File.separator + moduleName + File.separator + className + "Model.java";
        }

        if (template.contains("postgres.dao.java.ftl")) {
            return packagePath + "dao" + File.separator + moduleName + File.separator + className + "Dao.java";
        }

        if (template.contains("postgres.dao.xml.ftl")) {
            return config.getSrcPath() + File.separator + "main" + File.separator
                + "resources" + File.separator + "sql" + File.separator + "common" + File.separator + moduleName
                + File.separator + className + "Dao.xml";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
