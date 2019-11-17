package com.robby.app.wsserver.configures;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.robby.app.commons.properties.db.DataSourceProperties;
import com.robby.app.commons.properties.db.DruidProperties;
import com.robby.app.commons.properties.db.StateViewProperties;
import com.robby.app.commons.utils.DataBaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 数据库及连接池配置
 * Created @ 2019/11/17
 * @author liuwei
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class DatasourceConfiguration implements EnvironmentAware {

    DataSourceProperties dataSourceProperties;
    DruidProperties druidProperties;
    StateViewProperties stateViewProperties;
    DataBaseUtil util;
    String baseUrl;

    @Override
    public void setEnvironment(Environment environment) {
        Binder binder = Binder.get(environment);
        this.dataSourceProperties = binder.bind("spring.datasource.master", DataSourceProperties.class).get();
        this.druidProperties = binder.bind("spring.datasource.druid", DruidProperties.class).get();
        this.stateViewProperties = binder.bind("spring.datasource.druid.stat-view-servlet", StateViewProperties.class).get();
        this.util = new DataBaseUtil(dataSourceProperties, druidProperties, stateViewProperties);
        this.baseUrl = binder.bind("server.servlet.context-path", String.class).get();
    }

    @Bean(destroyMethod = "close", initMethod = "init")
    @Primary
    public DataSource dataSource() throws SQLException, ClassNotFoundException {
        // 首先创建数据库
        util.initDataBase();
        return util.createDruidSource();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        bean.addUrlPatterns("/*");
        bean.addInitParameter("exclustions", "*.js,*.gif,*.jpg,*.png,*.bmp,*.css,*.ico,/druid/*");
        return bean;
    }

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return util.createStatInterceptor();
    }

    /**
     * Druid可视化监控
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean();

        bean.setServlet(new StatViewServlet());
        bean.addUrlMappings("/druid/*");

        Map<String, String> params = new LinkedHashMap<>();
        params.put("loginUserName", stateViewProperties.getLoginUsername());
        params.put("loginPassword", stateViewProperties.getLoginPassword());

        if(Optional.ofNullable(stateViewProperties.getAllow()).isPresent()) {
            params.put("allow", stateViewProperties.getAllow());
        }
        if(Optional.ofNullable(stateViewProperties.getDeny()).isPresent()) {
            params.put("deny", stateViewProperties.getDeny());
        }
        bean.setInitParameters(params);

        return bean;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setProxyTargetClass(true);
        creator.setInterceptorNames("druid-stat-interceptor");
        return creator;
    }

}
