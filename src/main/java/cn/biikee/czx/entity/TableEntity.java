package cn.biikee.czx.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 表数据
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月20日 上午12:02:55
 */
public class TableEntity {
	//表的名称
	private String tableName;
	//表的备注
	private String comments;

	//表的列名(不包含主键)
	private List<ColumnEntity> columns = new ArrayList<ColumnEntity>();
	
	//表的主Key
	private List<ColumnEntity> kyscolumns = new ArrayList<ColumnEntity>();
	
	//类名(第一个字母小写)，如：sys_user => sysUser
	private String classname;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<ColumnEntity> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnEntity> columns) {
		this.columns = columns;
	}

	public List<ColumnEntity> getKyscolumns() {
		return kyscolumns;
	}
	public void setKyscolumns(List<ColumnEntity> kyscolumns) {
		this.kyscolumns = kyscolumns;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
}
