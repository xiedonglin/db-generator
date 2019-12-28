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
    /**
     * 取得数据库
     * @param paramMap 
     * @return 数据库リスト
     */
	List<Map<String, Object>> queryList(Map<String, Object> map);
	
    /**
     * 取得数据库数量
     * @param paramMap 
     * @return 数据库数量
     */
	int queryTotal(Map<String, Object> map);
	
    /**
     * 取得数据库表
     * @param schema 
     * @return 数据库表
     */
	List<Map<String, String>> queryTable(String schema);
	
    /**
     * 取得表字段
     * @param 表名 
     * @return 取得所有表字段
     */
	List<Map<String, String>> queryColumns(String tablename);

    /**
     * 取得Seq
     * @param 表名 
     * @return 取得Seq
     */
	List<Map<String, String>> getSeqs(Map<String, String> map);
}
