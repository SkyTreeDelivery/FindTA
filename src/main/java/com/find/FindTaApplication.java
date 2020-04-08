package com.find;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.find.mapper")
@SpringBootApplication
public class FindTaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindTaApplication.class, args);
	}

}
