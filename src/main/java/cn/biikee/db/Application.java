package cn.biikee.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * 代码生成器
 * 
 * @author czx
 * @email 34412447@qq.com
 * @date 2016年12月19日 下午9:12:58
 */
@SpringBootApplication
@MapperScan("cn.biikee.db.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
