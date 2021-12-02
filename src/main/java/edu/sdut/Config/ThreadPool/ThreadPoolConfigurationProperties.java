package edu.sdut.Config.ThreadPool;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供了获取application.properties配置文件属性的功能，
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 15:59
 */
@Data
@ConfigurationProperties(prefix = "monitor.threadpool")
public class ThreadPoolConfigurationProperties {
    private List<ThreadPoolProperties> executors = new ArrayList<>();
}
