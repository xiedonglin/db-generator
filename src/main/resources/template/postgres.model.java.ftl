package ${packagename}.model.${moduleName};

import java.util.Date;

<#list columns as column>
	<#if column.javaType == "BigDecimal">
		import java.math.BigDecimal;<#lt>
		<#break>
    </#if>
</#list>
import ${packagename}.annotation.PrimaryKey;
import ${packagename}.model.BaseModel;

/**
 * ${comments}
 * 
 * @author ${author}
 * @email ${email}
 */
public class ${classname}Model extends BaseModel {

	private static final long serialVersionUID = 1L;

	<#list keycolumns as column>
	/**
	 * ${column.comments}
	 */
	@PrimaryKey(name="${column.attrname}")
	private ${column.javaType} ${column.attrname};
	</#list>

	<#list columns as column>
	<#if column.isKey != "1"><#t>
	/**
	 * ${column.comments}
	 */
	private ${column.javaType} ${column.attrname};
    </#if>
	</#list>

<#list columns as column>
	/**
	 * 设置：${column.comments}
	 */
	public void set${column.javaGetSetName}(${column.javaType} ${column.attrname}) {
		this.${column.attrname} = ${column.attrname};
	}
	/**
	 * 获取：${column.comments}
	 */
	public ${column.javaType} get${column.javaGetSetName}() {
		return ${column.attrname};
	}
</#list>
}
