package ${packagename}.dao.${moduleName};

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.github.pagehelper.Page;
import java.util.Date;
<#list columns as column>
	<#if column.javaType == "BigDecimal">
		import java.math.BigDecimal;<#lt>
		<#break>
    </#if>
</#list>
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
	${classname}Model selectByPrimaryKey(<#list keycolumns as column>@Param("${column.attrname}") ${column.javaType} ${column.attrname}<#if column_has_next>,</#if></#list>);<#lt>
	
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
	 * レコード一括物理削除処理を行います.
	 * </pre>
	 * 
	 * @param record
	 *            削除条件のエンティティ
	 * @return　処理件数
	 */
	int deleteBatchByPrimaryKey(List<${classname}Model> record);
	
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
