package cn.biikee.db.entity;

/**
 * 列的属性
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月20日 上午12:01:45
 */
public class ColumnEntity {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列名类型
     */
    private String dataType;

    /**
     * 列名备注
     */
    private String comments;

    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrname;
    private String javaGetSetName;

    /**
     * 属性类型
     */
    private String dbType;
    private String jdbcType;
    private String javaType;

    /**
     * 是否为主Key
     */
    private String isKey;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getJavaGetSetName() {
        return javaGetSetName;
    }

    public void setJavaGetSetName(String javaGetSetName) {
        this.javaGetSetName = javaGetSetName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

}
