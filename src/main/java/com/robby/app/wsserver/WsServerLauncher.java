package com.robby.app.wsserver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created @ 2019/11/11
 *
 * @author liuwei
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, ShiroAutoConfiguration.class})
@MapperScan(basePackages = {"com.robby.app.wsserver.mapper"})
public class WsServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(WsServerLauncher.class, args);
    }

}
