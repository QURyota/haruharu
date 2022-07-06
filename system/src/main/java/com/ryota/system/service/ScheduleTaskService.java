package com.ryota.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ryota.system.config.SendSignHttpsClient;
import com.ryota.system.entity.SystemCrontab;
import com.ryota.system.entity.SystemJob;
import com.ryota.system.entity.SystemJobCrontab;
import com.ryota.system.entity.SystemJobParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * @Author quyf
 * @create 2022/6/4 13:37
 */
@Slf4j
@Component
public class ScheduleTaskService {
    /**
     * 定时任务容器
     */
    private static final Map<String, ScheduledFuture<?>> TASK_FUTURES = new ConcurrentHashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private SystemJobCrontabService systemJobCrontabService;

    @Autowired
    private SendSignHttpsClient sendSignHttpsClient;

    @Autowired
    private SystemJobService systemJobService;

    @Autowired
    private SystemJobParamService systemJobParamService;

    @Autowired
    private SystemCrontabService systemCrontabService;

    public void startTask(String taskName,Runnable task,String cron){
        stopTask(taskName);
        log.info("开启定时任务"+taskName);
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(task, new CronTrigger(cron));
        TASK_FUTURES.put(taskName,scheduledFuture);
    }
    public void stopTask(String taskName){
        log.info("停止定时任务"+taskName);
        ScheduledFuture<?> scheduledFuture = TASK_FUTURES.get(taskName);
        if(null != scheduledFuture){
            scheduledFuture.cancel(true);
        }
    }

    public List<Map<String, Object>> listTaskName() {
        return TASK_FUTURES.entrySet().stream().map(i -> {
            Map<String,Object> map = new HashMap<>();
            map.put("taskName",i.getKey());
            map.put("status",i.getValue().isCancelled()?"已取消":"正在执行");
            return map;
        }).collect(Collectors.toList());
    }

    public String delTask(String taskName){
        if (TASK_FUTURES.containsKey(taskName)){
            TASK_FUTURES.remove(taskName);
            if (TASK_FUTURES.containsKey(taskName)) {
                return "删除失败";
            }
            return "删除成功";
        }else {
            return "删除成功";
        }
    }

    @PostConstruct
    public void init() {
        log.info("容器启动后自动启动定时任务");
        List<SystemJobCrontab> list = systemJobCrontabService.list();
        List<SystemJobCrontab> collect = list.stream().filter(item -> item.getStatus().booleanValue() == true).collect(Collectors.toList());
        for (SystemJobCrontab systemJobCrontab : collect) {
            SystemJob systemJob = systemJobService.getById(systemJobCrontab.getJobId());
//            SystemJobParam systemJobParam = systemJobParamService.getOne(new QueryWrapper<SystemJobParam>().eq("job_id", systemJobCrontab.getJobId()));
            SystemCrontab systemCrontab = systemCrontabService.getById(systemJobCrontab.getCrontabId());
            List<SystemJobParam> systemJobParamList = systemJobParamService.list(new QueryWrapper<SystemJobParam>().eq("job_id", systemJob.getId()));

            startTask(systemJobCrontab.getId(),
                    () -> sendSignHttpsClient.doPostForJson(systemJob.getInterfaceUrl(),systemJobParamList,systemJob.getId()),
                    systemCrontab.getCrontab());
        }
    }
}
