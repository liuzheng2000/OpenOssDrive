package edu.sdut.Config.ThreadPool;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 创建线程池配置
 * @author qingyun
 * @version 1.0
 * @date 2021/10/30 14:44
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {


//    ThreadPoolExecutor  ThreadPoolTaskExecutor  区别与不同


//
//    分析下继承关系：
//
//   1、ThreadPoolTaskExecutor extends (2)ExecutorConfigurationSupport
//	implements (3)AsyncListenableTaskExecutor, (4)SchedulingTaskExecutor
//2、 ExecutorConfigurationSupport extends CustomizableThreadFactory implements BeanNameAware, InitializingBean, DisposableBean
//
//3、public interface AsyncListenableTaskExecutor extends AsyncTaskExecutor
//4、public interface SchedulingTaskExecutor extends AsyncTaskExecutor
//
//从上继承关系可知：
//    ThreadPoolExecutor是一个java类不提供spring生命周期和参数装配。
//    ThreadPoolTaskExecutor实现了InitializingBean, DisposableBean ，xxaware等，具有spring特性
//
//    AsyncListenableTaskExecutor提供了监听任务方法(相当于添加一个任务监听，提交任务完成都会回调该方法)
//
//    简单理解：
//            1、ThreadPoolTaskExecutor使用ThreadPoolExecutor并增强，扩展了更多特性
//2、ThreadPoolTaskExecutor只关注自己增强的部分，任务执行还是ThreadPoolExecutor处理。
//            3、前者spring自己用着爽，后者离开spring我们用ThreadPoolExecutor爽。
//    注意：ThreadPoolTaskExecutor 不会自动创建ThreadPoolExecutor需要手动调initialize才会创建
//    如果@Bean 就不需手动，会自动InitializingBean的afterPropertiesSet来调initialize



//    @Autowired
//    ThreadPoolForMonitorManager threadPoolForMonitorManager;

    /**
     *   默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     *    当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     *  当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
     */

    /**
     * 核心线程数（默认线程数）
     */
    public static final int corePoolSize = 5;

    /**
     * 最大线程数
     */
    private static final int maxPoolSize = 30;

    /**
     * 允许线程空闲实际按单位（默认：秒）
     */
    private static final int KeepAliveTime = 30;

    /**
     * 缓冲队列请求
     */
    private static final int queueCapacity = 10000;

    /**
     * 线程池前缀
     */
    private static final String threadNamePrefix = "edu-sdut-service-";

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor  taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(KeepAliveTime);
        executor.setThreadNamePrefix(threadNamePrefix);
        //线程池对于拒绝任务的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutorForMonitor.CallerRunsPolicy());
        //初始化
        executor.initialize();
        return executor;
    }
}
