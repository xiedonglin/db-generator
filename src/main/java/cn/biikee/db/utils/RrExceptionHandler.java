package cn.biikee.db.utils;

import com.alibaba.fastjson.JSON;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年10月27日 下午10:16:19
 */
@Component
public class RrExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		R r = new R();
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			if (ex instanceof RrException) {
				r.put("code", ((RrException) ex).getCode());
				r.put("msg", ((RrException) ex).getMessage());
            }else if(ex instanceof ClientAbortException){
                r = R.error("网页断开连接");
			}else if(ex instanceof DuplicateKeyException){
				r = R.error("数据库中已存在该记录");
			}else{
				r = R.error();
	            //记录异常日志
	            logger.error(ex.getMessage(), ex);
			}
			String json = JSON.toJSONString(r);
			response.getWriter().print(json);
		} catch (Exception e) {
			logger.error("RRExceptionHandler 异常处理失败", e);
		}
		return new ModelAndView();
	}
}
