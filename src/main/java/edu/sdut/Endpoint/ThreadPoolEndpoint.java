package edu.sdut.Endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qingyun
 * @version 1.0
 * @date 2021/11/12 14:22
 */
@Configuration
@Endpoint(id = "taskExecutor-thread-pool")
public class ThreadPoolEndpoint {
    /**
     * 需要监听的线程池
     */
    @Autowired
    @Qualifier(value = "taskExecutor")
    private ThreadPoolTaskExecutor tpe;

    @ReadOperation
    public Map<String,Object> threadPoolMetric(){
        HashMap<String, Object> metricMap = new HashMap<>();
        List<Map> threadPools=new ArrayList<>();
//        ThreadPoolExecutorForMonitor tpe = (ThreadPoolExecutorForMonitor) threadPools;
        //此处可返回线程池信息 TODO
        Map<String,Object> poolInfo=new HashMap<>();
        poolInfo.put("thread.pool.name",tpe.toString());

        poolInfo.put("thread.pool.core.size",tpe.getCorePoolSize());

//        poolInfo.put("thread.pool.largest.size",tpe.getLargestPoolSize());
//        poolInfo.put("thread.pool.max.size",tpe.getMaximumPoolSize());

        poolInfo.put("thread.pool.thread.count",tpe.getPoolSize());
        poolInfo.put("thread.pool.active.count",tpe.getActiveCount());

//        poolInfo.put("thread.pool.completed.taskCount",tpe.getCompletedTaskCount());
//        poolInfo.put("thread.pool.queue.name",tpe.getQueue().getClass().getName());
//        poolInfo.put("thread.pool.rejected.name",tpe.getRejectedExecutionHandler().getClass().getName());
//        poolInfo.put("thread.pool.task.count",tpe.getTaskCount());

        threadPools.add(poolInfo);

        metricMap.put("threadPools",threadPools);
        return metricMap;
    }
}
