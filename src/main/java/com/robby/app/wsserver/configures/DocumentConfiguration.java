package com.robby.app.wsserver.configures;

import com.robby.app.commons.properties.docs.DocProperties;
import com.robby.app.commons.utils.DocUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.Parameter;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger配置
 * Created @ 2019/11/17
 * @author liuwei
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableWebMvc
public class DocumentConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "swagger")
    public DocProperties docProperties() {
        return new DocProperties();
    }

    @Bean
    public UiConfiguration webSocketServerUiConfiguration() {
        return DocUtil.getUiConfiguration();
    }

    @Bean
    public Docket webSocketDocket(@Autowired DocProperties docProperties) {
        Map<String, Object> headerMap = new LinkedHashMap<>();
        List<Parameter> headers = DocUtil.genHeader(headerMap);
        return DocUtil.genDocket(docProperties, headers);
    }

}
