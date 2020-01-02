package cn.biikee.db.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.biikee.db.service.SysGeneratorServicePostgres;
import cn.biikee.db.utils.PageUtils;
import cn.biikee.db.utils.Query;
import cn.biikee.db.utils.R;

/**
 * 代码生成器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorControllerPostgres {

	@Autowired
	private SysGeneratorServicePostgres sysGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] schema = new String[]{};
		String schemas = request.getParameter("schemas");
		schema = JSON.parseArray(schemas).toArray(schema);
		
		byte[] data = sysGeneratorService.generatorCode(schema);
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"x-springboot.zip\"");
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());  
	}
	
	
	
	
	
}
