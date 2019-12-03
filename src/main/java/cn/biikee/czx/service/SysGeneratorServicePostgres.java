package cn.biikee.czx.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.biikee.czx.config.PostgresConfig;
import cn.biikee.czx.dao.SysGeneratorDaoPostgres;
import cn.biikee.czx.utils.GenPostgresUtils;

/**
 * 代码生成器
 * 
 * @author czx
 * @email object_czx@163.com
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
				gen.generatorPostgres(table, columns,cfg);
			}
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
}
