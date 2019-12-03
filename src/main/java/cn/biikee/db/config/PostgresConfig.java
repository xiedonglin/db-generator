package cn.biikee.db.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
 
/**
 * 代码生成器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月19日 下午9:12:58
 */
@Configuration
@PropertySource(value = "classpath:generator.properties")
@ConfigurationProperties(prefix="postgres")
public class PostgresConfig {
	private String mainPath;
	private String packagename;
	private String moduleName;
	private String author;
	private String email;
	private String tablePrefix;
	private String srcPath;
    private Map<String, String> javaType = new HashMap<>();
    private Map<String, String> jdbcType = new HashMap<>();
    
    public String getSrcPath() {
        return srcPath;
    }
    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }
    public String getMainPath() {
		return mainPath;
	}
	public void setMainPath(String mainPath) {
		this.mainPath = mainPath;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTablePrefix() {
		return tablePrefix;
	}
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}
	public Map<String, String> getJavaType() {
		return javaType;
	}
	public void setJavaType(Map<String, String> javaType) {
		this.javaType = javaType;
	}
	public Map<String, String> getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(Map<String, String> jdbcType) {
		this.jdbcType = jdbcType;
	}
}