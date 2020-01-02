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
<@format blank="8">SELECT</@format>
<@format blank="12"><include refid="Base_Column_List" /></@format>
<@format blank="8">FROM ${tableName}</@format>
<@format blank="8">WHERE</@format>
	<#list keycolumns as column>
<@format blank="12"><#if column_index != 0 >AND </#if>${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse></@format>
	</#list>
    </select>
    
    <select id="selectByModel" resultMap="BaseResultMap" parameterType="${packagename}.model.${moduleName}.${classname}Model">
<@format blank="8">SELECT</@format>
<@format blank="12"><include refid="Base_Column_List" /></@format>
<@format blank="8">FROM ${tableName}</@format>
    <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
<@format blank="8"><if test="${column.columnName} != null"></@format>
<@format blank="12">AND ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse></@format>
<@format blank="8"></if></@format>
	</#list>
    </trim>
    
    <if test="sortorder != null and sortorder != ''">
<@format blank="8">ORDER BY <#noparse>${</#noparse>sortorder<#noparse>}</#noparse></@format>
    </if>
    </select>

    <select id="selectCount" resultType="int" parameterType="${packagename}.model.${moduleName}.${classname}Model">
    SELECT 
        COUNT(1)
    FROM ${tableName}
    <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
<@format blank="8"><if test="${column.columnName} != null"></@format>
<@format blank="12">AND ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse></@format>
<@format blank="8"></if></@format>
	</#list>
    </trim>
    </select>

    <insert id="insertSelective" parameterType="${packagename}.model.${moduleName}.${classname}Model">
	<#if seqcolumnName?? && seqcolumnName != "">
        <selectKey keyProperty="${seqcolumnName}" resultType="java.lang.Long" order="AFTER">
            SELECT currval('${tableName}_${seqcolumnName}_seq')
        </selectKey>
    </#if>
    INSERT INTO ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
  	<#list columns as column>
		<#if seqcolumnName != column.columnName>
			<#if column.columnName == "dtcreatedatetime" || column.columnName == "dtupdatedatetime">
<@format blank="8">${column.columnName},</@format>
		  	<#else>
<@format blank="8"><if test="${column.columnName} != null"></@format>
<@format blank="12">${column.columnName},</@format>
<@format blank="8"></if></@format>
		    </#if>
	    </#if>
	</#list>  
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
  	<#list columns as column>
		<#if seqcolumnName != column.columnName>
			<#if column.columnName == "dtcreatedatetime" || column.columnName == "dtupdatedatetime">
<@format blank="8">now(),</@format>
		  	<#else>
<@format blank="8"><if test="${column.columnName} != null"></@format>
<@format blank="12"><#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,</@format>
<@format blank="8"></if></@format>
		    </#if>
	    </#if>
	</#list>	 	 
    </trim>
    </insert>

    <update id="updateByPrimaryKeySelective" parameterType="${packagename}.model.${moduleName}.${classname}Model">
    UPDATE ${tableName}
    <set>
  	<#list columns as column>
		<#if column.columnName == "ccreateuserid" || column.columnName == "dtcreatedatetime">
	  	<#else>
			<#if column.columnName == "dtupdatedatetime" >
<@format blank="8">${column.columnName} = now(),</@format>
		  	<#else>
		  		<#if column.columnName == "ihaitacounter">
<@format blank="8">ihaitacounter = ihaitacounter + 1,</@format>
		  		<#else>
<@format blank="8"><if test="${column.columnName} != null"></@format>
<@format blank="12">${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,</@format>
<@format blank="8"></if></@format>
		  		</#if>
		    </#if>
	    </#if>
	</#list>  
    </set>
    <trim prefix="WHERE" prefixOverrides="AND|OR">
	<#list keycolumns as column>
        AND ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
	    <if test="ihaitacounter != null">
	        AND ihaitacounter = <#noparse>#{</#noparse>ihaitacounter,jdbcType=DECIMAL<#noparse>}</#noparse> 
	    </if>
    </trim>
    </update>
    
    <delete id="deleteByPrimaryKey" parameterType="${packagename}.model.${moduleName}.${classname}Model">
    DELETE FROM ${tableName}
    <trim prefix="WHERE" prefixOverrides="AND|OR">
	<#list keycolumns as column>
        AND ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
	    <if test="ihaitacounter != null">
	        AND ihaitacounter = <#noparse>#{</#noparse>ihaitacounter,jdbcType=DECIMAL<#noparse>}</#noparse> 
	    </if>
    </trim>
    </delete>
 
    <delete id="deleteByModel" parameterType="${packagename}.model.${moduleName}.${classname}Model">
    DELETE FROM ${tableName}
    <trim prefix="WHERE" prefixOverrides="AND|OR">
  	<#list columns as column>
  	<if test="${column.columnName} != null">
        AND ${column.columnName} = <#noparse>#{</#noparse>${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
    </if>
	</#list>
    </trim>
    </delete>	 	 	 	 	 
	 	 	 	 
    <insert id="insertBatchSelective" parameterType="java.util.List" useGeneratedKeys="false">
    INSERT INTO ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
	<#list columns as column>
		<#if seqcolumnName != column.columnName >
<@format blank="8">${column.columnName},</@format>
	    </#if>
	</#list>  
    </trim>
    VALUES
    <foreach collection="list" item="item" index="index" separator=",">
    <trim prefix="(" suffix=")" suffixOverrides=",">
	<#list columns as column>
		<#if seqcolumnName != column.columnName >
			<#if column.columnName == "dtcreatedatetime" || column.columnName == "dtupdatedatetime">
<@format blank="8">now(),</@format>
		  	<#else>
<@format blank="8"><#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>},</#noparse></@format>
		    </#if>
	    </#if>
	</#list>  
    </trim>
    </foreach> 
    </insert>

    <update id="updateBatchByPrimaryKeySelective" parameterType="java.util.List">
    <foreach collection="list" item="item" index="index" open="" close="" separator=";">
    UPDATE ${tableName}
    <set>
  	<#list columns as column>
	<#if column.columnName == "ccreateuserid" || column.columnName == "dtcreatedatetime">
  	<#else>
		<#if seqcolumnName != column.columnName >
			<#if column.columnName == "dtupdatedatetime" >
<@format blank="8">${column.columnName} = now(),</@format>
		  	<#else>
		  		<#if column.columnName == "ihaitacounter">
<@format blank="8">ihaitacounter = ihaitacounter + 1,</@format>
		  		<#else>
<@format blank="8"><if test="item.${column.columnName} != null"></@format>
<@format blank="12">${column.columnName} = <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>,</@format>
<@format blank="8"></if></@format>
		  		</#if>
		    </#if>
	    </#if>
	</#if>
	</#list>       	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	  	 	 	 	 	 	 	 	 	 	 	 	 
    </set>
    WHERE
	<#list keycolumns as column>
        <#if column_index != 0 >and </#if>${column.columnName} = <#noparse>#{</#noparse>item.${column.columnName},jdbcType=${column.jdbcType}<#noparse>}</#noparse>
	</#list>
<@format blank="8"><if test="item.ihaitacounter != null"></@format>
<@format blank="12">AND ihaitacounter = <#noparse>#{</#noparse>item.ihaitacounter,jdbcType=DECIMAL<#noparse>}</#noparse></@format> 
<@format blank="8"></if></@format>
    </foreach>
    </update>
</mapper>
