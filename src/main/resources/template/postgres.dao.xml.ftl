<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packagename}.dao.${moduleName}.${classname}Dao">
    <resultMap id="BaseResultMap" type="${packagename}.model.${moduleName}.${classname}Model">
<#list columns as column>
	<#if column.isKey == "1"><#t>
		<id column="${column.attrname}" property="${column.attrname}" jdbcType="${column.jdbcType}" />
    </#if>
</#list>
<#list columns as column>
	<#if column.isKey == "0"><#t>
        <result column="${column.columnName}" property="${column.attrname}" jdbcType="${column.jdbcType}" />
    </#if>
</#list>
	</resultMap>

 <sql id="Base_Column_List">
<#list columns as column>
	${column.attrname}<#if column_has_next>,</#if><#t>
</#list>

 </sql>

 <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="map">
  select 
  <include refid="Base_Column_List" />
  from ${tableName}
    where 
	<#list keycolumns as column>
	        <#if column_index != 0 >and </#if>${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
 </select>
 <select id="selectByModel" resultMap="BaseResultMap" parameterType="${packagename}.model.${moduleName}.${classname}Model">
  select 
  <include refid="Base_Column_List" />
  from ${tableName}
  <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
  	<if test="${column.columnName} != null" >
  		and ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
    </if>
	</#list>
  </trim>
   <if test="sortorder != null and sortorder != ''">
    order by <#noparse>${</#noparse>sortorder<#noparse>}</#noparse>
   </if>    
 </select>

 <select id="selectCount" resultType="int" parameterType="${packagename}.model.${moduleName}.${classname}Model">
  select 
    count(1)
  from ${tableName}
  <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
  	<if test="${column.columnName} != null" >
  		and ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
    </if>
	</#list>
  </trim>
 </select>

 <insert id="insertSelective" parameterType="${packagename}.model.${moduleName}.${classname}Model" >
	<#if seqcolumnName?? && seqcolumnName != "">
		<selectKey keyProperty="${seqcolumnName}" resultType="java.lang.Long" order="AFTER">
			SELECT currval('${tableName}_${seqcolumnName}_seq')
		</selectKey>
 	</#if>
    insert into ${tableName}
  <trim prefix="(" suffix=")" suffixOverrides="," >
  	<#list columns as column>
	<#if seqcolumnName != column.columnName>
  	<if test="${column.columnName} != null" >
  		${column.columnName},
    </if>
    </#if>
	</#list>  
   </trim>
   <trim prefix="values (" suffix=")" suffixOverrides="," >
  	<#list columns as column>
	<#if seqcolumnName != column.columnName>
  	<if test="${column.columnName} != null" >
  		<#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,
    </if>
    </#if>
	</#list>	 	 
   </trim>
 </insert>
 <update id="updateByPrimaryKeySelective" parameterType="${packagename}.model.${moduleName}.${classname}Model" >
    update ${tableName}
  <set >
  	<#list columns as column>
  	<if test="${column.columnName} != null" >
  		<#if column.columnName == 'ihaitacounter'>
	  		${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse> + 1,
  		<#else>
	  		${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,
  		</#if>
    </if>
	</#list>  
  </set>
  <trim prefix="WHERE" prefixOverrides="AND|OR">
	<#list keycolumns as column>
		and ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
   <if test="ihaitacounter != null and ihaitacounter != ''" >
      and ihaitacounter = <#noparse>#{</#noparse>ihaitacounter,jdbcType=DECIMAL<#noparse>}</#noparse> 
   </if>
  </trim>
 </update>
 <delete id="deleteByPrimaryKey" parameterType="${packagename}.model.${moduleName}.${classname}Model" >
    delete from ${tableName}
  <trim prefix="WHERE" prefixOverrides="AND|OR">
	<#list keycolumns as column>
	        and ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
  </trim>
 </delete>
 
 <delete id="deleteByModel" parameterType="${packagename}.model.${moduleName}.${classname}Model" >
  delete from ${tableName}
  <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
  	<if test="${column.columnName} != null" >
  		and ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
    </if>
	</#list>
  </trim>
 </delete>	 	 	 	 	 
	 	 	 	 
 <insert id="insertBatchSelective" parameterType="java.util.List" useGeneratedKeys="false" >
    insert into ${tableName}
  <trim prefix="(" suffix=")" suffixOverrides="," >
	<#list columns as column>
		${column.attrname},
	</#list>  
  </trim>
  <foreach collection="list" item="item" index="index" separator="union all">
  (
    SELECT 
    <trim prefix="" suffix="" suffixOverrides="," >
	<#list columns as column>
    <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>},</#noparse>
	</#list>  
    </trim>
    FROM DUAL
  )
  </foreach> 
 </insert>
 <update id="updateBatchByPrimaryKeySelective" parameterType="java.util.List" >
  begin
  <foreach collection="list" item="item" index="index" open="" close="" separator=";">
    update ${tableName}
    <set >     	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	 
  	<#list columns as column>
  	<if test="item.${column.columnName} != null" >
  		<#if column.columnName == 'ihaitacounter'>
	  		${column.columnName} = <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse> + 1,
  		<#else>
	  		${column.columnName} = <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,
  		</#if>
    </if>
	</#list>       	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	 
    </set>
    where
	<#list keycolumns as column>
        <#if column_index != 0 >and </#if>${column.columnName} = <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
   <if test="ihaitacounter != null and ihaitacounter != ''" >
      and ihaitacounter = <#noparse>#{</#noparse>item.ihaitacounter,jdbcType=DECIMAL<#noparse>}</#noparse> 
   </if>
  </foreach>
 ;end;
 </update>
 <delete id="deleteBatchByPrimaryKey" parameterType="java.util.List" >
    delete from ${tableName}
  where
  <trim prefix="(" suffix=")" suffixOverrides="," >
	<#list keycolumns as column>
	        ${column.columnName},
	</#list>
  </trim>
  in
  <foreach collection="list" item="item" index="index" separator="," open="(" close=")">
    <trim prefix="(" suffix=")" suffixOverrides="," >
	<#list keycolumns as column>
	   <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,
	</#list>    
    </trim>
  </foreach>
 </delete>
</mapper>
