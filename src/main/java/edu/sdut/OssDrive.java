package edu.sdut;

import edu.sdut.Config.ThreadPool.ThreadPoolConfigurationProperties;
import edu.sdut.Netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 在一个Spring Boot应用中，可以使用@EnableAuthorizationServer注解实现授权服务器，使用@EnableResourceServer注解实现资源服务器。
 *
 * 搭建一个建议的开源文件云盘
 * @author qingyun
 * @version 1.0
 * @date 2021/10/27 19:26
 */



@EnableConfigurationProperties(ThreadPoolConfigurationProperties.class)
@SpringBootApplication
public class OssDrive {
    public static void main(String[] args) {
        SpringApplication.run(OssDrive.class,args);

        try {
            new NettyServer(12345).start();
            System.out.println("http://127.0.0.1:6688/netty-websocket/index");
        }catch(Exception e) {
            System.out.println("NettyServerError:"+e.getMessage());
        }

    }
}
