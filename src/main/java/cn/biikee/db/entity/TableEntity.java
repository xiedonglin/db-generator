package cn.biikee.db.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 表数据
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月20日 上午12:02:55
 */
public class TableEntity {
	private String tableName;
	private String comments;
	private List<ColumnEntity> columns = new ArrayList<>();
	private List<ColumnEntity> kyscolumns = new ArrayList<>();
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
