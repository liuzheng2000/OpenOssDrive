package edu.sdut.Config.ThreadPool;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 线程池的核心属性声明。
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 16:02
 */
@Data
public class ThreadPoolProperties {
    private String poolName;
    /**
     * 核心线程数（默认线程数）
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maxNumPoolSize=Runtime.getRuntime().availableProcessors();

    /**
     * 允许线程空闲时间按单位（默认：秒）
     */
    private long keepAliveTime  = 30 ;
    /**
     * 允许线程空闲单位（默认：秒）
     */
    private TimeUnit unit = TimeUnit.SECONDS;
    /**
     * 缓冲队列请求
     */
    private int queueCapacity=Integer.MAX_VALUE;
}
