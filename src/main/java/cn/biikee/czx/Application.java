package cn.biikee.czx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 代码生成器
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月19日 下午9:12:58
 */
@SpringBootApplication
@MapperScan("cn.biikee.czx.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
