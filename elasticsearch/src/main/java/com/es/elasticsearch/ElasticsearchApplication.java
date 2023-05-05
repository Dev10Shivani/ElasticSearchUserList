package com.es.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ElasticsearchApplication <T>{

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}
}
