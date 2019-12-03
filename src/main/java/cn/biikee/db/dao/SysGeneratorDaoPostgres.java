package cn.biikee.db.dao;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月19日 下午3:32:04
 */
public interface SysGeneratorDaoPostgres {
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	List<Map<String, String>> queryTable(String schema);
	
	List<Map<String, String>> queryColumns(String tablename);
}
