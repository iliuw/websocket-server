package com.robby.app.wsserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@SpringBootApplication(exclude = {
        com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure.class
//        org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration.class
})
@MapperScan(basePackages = {"com.robby.app.wsserver.mapper"})
public class WsServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(WsServerLauncher.class, args);
    }

}
