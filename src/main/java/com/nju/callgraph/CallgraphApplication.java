package com.nju.callgraph;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@MapperScan(value = "com.nju.callgraph.mapper")
public class CallgraphApplication {

    public static void main(String[] args) {

        SpringApplication.run(CallgraphApplication.class,args);
    }

}
