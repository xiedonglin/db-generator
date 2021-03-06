package ${packagename}.dao.${moduleName};

<#list keycolumns as column>
	<#if column.javaType == "BigDecimal">
		import java.math.BigDecimal;<#lt>
		<#break>
    </#if>
</#list>
<#list keycolumns as column>
	<#if column.javaType == "Date">
		import java.util.Date;<#lt>
		<#break>
    </#if>
</#list>
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import ${packagename}.model.${moduleName}.${classname}Model;

/**
 *  ${comments}
 * @author ${author}
 * @email ${email}
 * @version V1.0 
 */
public interface ${classname}Dao {
	/**
	* <pre>
	* 主キーと一致するレコードの取得を行います.
	* 
	* </pre>
	* 
	<#list keycolumns as column><#t>
	* @param ${column.attrname} ${column.comments}
	</#list>
	* @return　取得レコード　
	*/
	<#if keycolumns?? && (keycolumns?size > 0)>
	${classname}Model selectByPrimaryKey(
	<#list keycolumns as column>
    <#if column_index == 0>
    <@format blank="4">@Param("${column.attrname}") ${column.javaType} ${column.attrname}<#if column_has_next>,</#if></@format>
    <#else>
	@Param("${column.attrname}") ${column.javaType} ${column.attrname}<#if column_has_next>,</#if>
	</#if>
	</#list>);
	<#else>
	${classname}Model selectByPrimaryKey();
	</#if>
		
	/**
	 * <pre>
	 * 検索条件と一致するレコードの取得を行います.
	 * 
	 * </pre>
	 * 
	 * @param record
	 *            検索条件のエンティティ
	 * @return　取得レコード
	 */
	Page<${classname}Model> selectByModel(${classname}Model record);
	
	/**
	 * <pre>
	 * レコード数の統計 
	 * </pre>
	 * 
	 * @param record
	 *            追加対象のエンティティ
	 * @return　件数
	 */
	int selectCount(${classname}Model record);
		
	/**
	 * <pre>
	 * レコード追加処理を行います. 
	 *         指定のフィールドがNULLの場合、挿入しない
	 * </pre>
	 * 
	 * @param record
	 *            追加対象のエンティティ
	 * @return　処理件数
	 */
	int insertSelective(${classname}Model record);
	
	/**
	 * <pre>
	 * レコード更新処理を行います.
	 *            指定のフィールドがNULLの場合、更新しない
	 * </pre>
	 * 
	 * @param record
	 *            更新条件のエンティティ
	 * @return　処理件数
	 */
	int updateByPrimaryKeySelective(${classname}Model record);

	/**
	 * <pre>
	 * レコード物理削除処理を行います.
	 * 
	 * </pre>
	 * 
	 * @param record
	 *            削除条件のエンティティ
	 * @return　処理件数
	 */
	int deleteByPrimaryKey(${classname}Model record);

	/**
	 * <pre>
	 * レコード物理削除処理を行います.
	 * 
	 * </pre>
	 * 
	 * @param record
	 *            削除条件のエンティティ
	 * @return　処理件数
	 */
	int deleteByModel(${classname}Model record);
		
	/**
	 * <pre>
	 * レコード一括追加処理を行います. 
	 * </pre>
	 * 
	 * @param record
	 *            追加対象のエンティティ
	 * @return　処理件数
	 */
	int insertBatchSelective(List<${classname}Model> record);
	
	/**
	 * <pre>
	 * レコード一括更新処理を行います.
	 * </pre>
	 * 
	 * @param record
	 *            更新条件のエンティティ
	 * @return　処理件数
	 */
	int updateBatchByPrimaryKeySelective(List<${classname}Model> record);
}
