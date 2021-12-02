//package edu.sdut.Config.Oauth2;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
///**
// * 资源服务器配置
// * @author qingyun
// */
//@Configuration
//@EnableResourceServer
////@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
////方法拦截器
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//
////    //资源服务令牌解析服务 配置远程ResourceServerTokenServices后，可不用设置yml远程security.oauth2配置
////    @Bean
////    public ResourceServerTokenServices tokenService() {
////        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
////        RemoteTokenServices service=new RemoteTokenServices();
////        service.setCheckTokenEndpointUrl("http://localhost:9090/oauth/check_token");
////        service.setClientId("client");
////        service.setClientSecret("client");
////        return service;
////    }
////
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////        super.configure(http);
////    }
////
////    @Override
////    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
////        super.configure(resources);
////    }
//
//
//}