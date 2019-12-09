package cn.biikee.db.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.biikee.db.config.PostgresConfig;
import cn.biikee.db.dao.SysGeneratorDaoPostgres;
import cn.biikee.db.utils.GenPostgresUtils;

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
		GenPostgresUtils gen = new GenPostgresUtils();
		for (String schema : schemas) {
			// 查询表信息
			List<Map<String, String>> tables = queryTable(schema);
			// 生成代码
			for (Map<String, String> table : tables) {
				// 查询列信息
				List<Map<String, String>> columns = queryColumns(table.get("tablename"));
				Map<String, String> params = new HashMap<String,String>();
				params.put("tablename", table.get("tablename"));
				List<Map<String, String>> seqcolName = sysGeneratorDao.getSeqs(params);
				String seqcolumnname = "";
				if (!seqcolName.isEmpty()) {
				    seqcolumnname = seqcolName.get(0).get("seqcolumnname");
				}
				gen.generatorPostgres(table, columns,cfg,seqcolumnname);
			}
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
