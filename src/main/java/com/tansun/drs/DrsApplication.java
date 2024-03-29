package com.tansun.drs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.tansun.drs.mapper")
@SpringBootApplication
public class DrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrsApplication.class, args);
	}

}
