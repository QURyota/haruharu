package com.ryota.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.ryota.common.common.PageFormat;
import com.ryota.common.common.Result;
import com.ryota.system.config.SendSignHttpsClient;
import com.ryota.system.entity.SystemCrontab;
import com.ryota.system.entity.SystemJob;
import com.ryota.system.entity.SystemJobCrontab;
import com.ryota.system.entity.SystemJobParam;
import com.ryota.system.mapper.SystemJobCrontabMapper;
import com.ryota.system.param.CronParam;
import com.ryota.system.param.PageSearch;
import com.ryota.system.param.PageSearchParam;
import com.ryota.system.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryota.system.vo.SystemJobCronVO;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 定时任务执行周期设定 服务实现类
 * </p>
 *
 * @author qyf
 * @since 2022-06-04
 */
@Service
@Log4j
public class SystemJobCrontabServiceImpl extends ServiceImpl<SystemJobCrontabMapper, SystemJobCrontab> implements SystemJobCrontabService {
    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Autowired
    private SystemJobService systemJobService;

    @Autowired
    private SystemCrontabService systemCrontabService;

    @Autowired
    private SendSignHttpsClient sendSignHttpsClient;

    @Autowired
    private SystemJobParamService systemJobParamService;


    @Autowired
    private SystemJobCrontabMapper systemJobCrontabMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemJobCrontabServiceImpl.class);

    @Override
    public Result add(CronParam cronParam) {

        //截取cron表达式最后一部分
        String cron = cronParam.getCron().substring(0, cronParam.getCron().lastIndexOf(" "));
//        if(!CronExpression.isValidExpression(cronParam.getCron())){
//            return Result.error("表达式错误"+cronParam.getCron());
//        }

        String id = cronParam.getId();

        if(StringUtils.isNotEmpty(id)){
            del(cronParam.getId());
        }
        //定时任务内容持久化
        SystemJob systemJob = new SystemJob();
        systemJob.setSystemCode(cronParam.getSystemCode());
        systemJob.setDescription(cronParam.getInterfaceUrlDesc());
        systemJob.setInterfaceUrl(cronParam.getInterfaceUrl());
        if(!systemJobService.save(systemJob)){
            return Result.error();
        }

        //定时任务Cron持久化
        SystemCrontab systemCrontab = new SystemCrontab();
        systemCrontab.setCrontab(cron);
        systemCrontab.setDescription(cronParam.getCronDesc());
        if(!systemCrontabService.save(systemCrontab)){
            return Result.error();
        }

        //定时任务参数持久化
//        List<SystemJobParam> systemJobParamList = new ArrayList<>();

        if(CollectionUtils.isNotEmpty(cronParam.getParams())){
            List<SystemJobParam> systemJobParamList = cronParam.getParams();
            for (SystemJobParam systemJobParam : systemJobParamList) {
                systemJobParam.setJobId(systemJob.getId());
            }
            if(!systemJobParamService.saveBatch(systemJobParamList)){
                return Result.error();
            }
        }
        //持久化定时任务管理
        SystemJobCrontab systemJobCrontab = new SystemJobCrontab();
        systemJobCrontab.setCrontabId(systemCrontab.getId());
        systemJobCrontab.setJobId(systemJob.getId());
        systemJobCrontab.setRetryNum(cronParam.getRetryNum());
        systemJobCrontab.setDescription(cronParam.getDescription());

        if(StringUtils.isNotEmpty(id)){
            systemJobCrontab.setId(id);
        }
        if(this.save(systemJobCrontab)){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    @Override
    public Result getList(PageSearch pageSearch) {

        List<SystemJobCronVO> voList = new ArrayList<>();
        PageHelper.startPage(pageSearch.getPageNum(), pageSearch.getPageSize());
        List<SystemJobCrontab> list = this.list();
        for (SystemJobCrontab systemJobCrontab : list) {
            SystemJobCronVO systemJobCronVO = new SystemJobCronVO();
            SystemCrontab systemCrontab = systemCrontabService.getById(systemJobCrontab.getCrontabId());
            SystemJob systemJob = systemJobService.getById(systemJobCrontab.getJobId());
            systemJobCronVO.setId(systemJobCrontab.getId());
            systemJobCronVO.setCron(systemCrontab.getCrontab());
            systemJobCronVO.setCronDesc(systemCrontab.getDescription());
            systemJobCronVO.setStatus(systemJobCrontab.getStatus());
            systemJobCronVO.setInterfaceUrl(systemJob.getInterfaceUrl());
            systemJobCronVO.setInterfaceUrlDesc(systemJob.getDescription());
            systemJobCronVO.setDescription(systemJobCrontab.getDescription());
            voList.add(systemJobCronVO);
        }


        return Result.success("查询成功",new PageFormat(voList));
    }

    @Override
    public Result stop(String id) {
        scheduleTaskService.stopTask(id);
        SystemJobCrontab systemJobCrontab = this.getById(id);
        systemJobCrontab.setStatus(false);
        if(!this.updateById(systemJobCrontab)){
            return Result.error();
        }else {
            return Result.success();
        }
    }

    @Override
    public Result startTask(String id) {
        SystemJobCrontab systemJobCrontab = this.getById(id);
        //cron表达式
        SystemCrontab systemCrontab = systemCrontabService.getById(systemJobCrontab.getCrontabId());
        //定时接口
        SystemJob systemJob = systemJobService.getById(systemJobCrontab.getJobId());

//        SystemJobParam systemJobParam = systemJobParamService.getOne(new QueryWrapper<SystemJobParam>().eq("job_id",systemJobCrontab.getJobId()));
//        scheduleTaskService.startTask(systemJobCrontab.getId(),
//                () -> sendSignHttpsClient.doPostForJson(systemJob.getInterfaceUrl(),systemJobParam.getParamKey(),systemJob.getId()),
//                systemCrontab.getCrontab());
        List<SystemJobParam> systemJobParamList = systemJobParamService.list(new QueryWrapper<SystemJobParam>().eq("job_id", systemJob.getId()));
        //启动定时任务
        scheduleTaskService.startTask(systemJobCrontab.getId(),
                () -> sendSignHttpsClient.doPostForJson(systemJob.getInterfaceUrl(),systemJobParamList,systemJob.getId()),
                systemCrontab.getCrontab());
        systemJobCrontab.setStatus(true);
        this.updateById(systemJobCrontab);
        return Result.success();
    }


    @Override
    public Result testStartTask(String id) {
        SystemJobCrontab systemJobCrontab = this.getById(id);
        //cron表达式
        SystemCrontab systemCrontab = systemCrontabService.getById(systemJobCrontab.getCrontabId());
        //定时接口
        SystemJob systemJob = systemJobService.getById(systemJobCrontab.getJobId());

        List<SystemJobParam> systemJobParamList = systemJobParamService.list(new QueryWrapper<SystemJobParam>().eq("job_id", systemJob.getId()));
        //启动定时任务并发送
        scheduleTaskService.startTask(systemJobCrontab.getId(),
                () -> sendSignHttpsClient.doPostForJson(systemJob.getInterfaceUrl(),systemJobParamList,systemJob.getId()),
                systemCrontab.getCrontab());
        systemJobCrontab.setStatus(true);
        this.updateById(systemJobCrontab);
        return Result.success();
    }

    @Override
    public Result del(String id) {
        SystemJobCrontab systemJobCrontab = this.getById(id);
        //判断是否有参数,如果有参则删除
        QueryWrapper<SystemJobParam> job_id = new QueryWrapper<SystemJobParam>().eq("job_id", systemJobCrontab.getJobId());
        List<SystemJobParam> list = systemJobParamService.list(job_id);
        if(CollectionUtils.isNotEmpty(list)){
            systemJobParamService.remove(job_id);
        }
        systemJobService.remove(new QueryWrapper<SystemJob>().eq("id", systemJobCrontab.getJobId()));
        systemCrontabService.remove(new QueryWrapper<SystemCrontab>().eq("id", systemJobCrontab.getCrontabId()));
        this.removeById(id);
        String result = scheduleTaskService.delTask(id);
        if(!result.equals("删除成功")){
            return Result.error(result);
        }return Result.success(result);
    }

    @Override
    public Result getListByParam(PageSearchParam pageSearchParam) {
        List<SystemJobCronVO> list = systemJobCrontabMapper.getListByParam(pageSearchParam);
        return Result.success(new PageFormat(list));
    }

    @Override
    public Result get(String id) {
        SystemJobCrontab systemJobCrontab = this.getById(id);
        SystemJob systemJob = systemJobService.getById(systemJobCrontab.getJobId());
        SystemJobCronVO systemJobCronVO = new SystemJobCronVO();
        systemJobCronVO.setId(systemJobCrontab.getId());
        systemJobCronVO.setDescription(systemJobCrontab.getDescription());
        systemJobCronVO
                .setCron(systemCrontabService.getById(systemJobCrontab.getCrontabId()).getCrontab());
        systemJobCronVO
                .setCronDesc(systemCrontabService.getById(systemJobCrontab.getCrontabId()).getDescription());
        systemJobCronVO
                .setInterfaceUrl(systemJobService.getById(systemJobCrontab.getJobId()).getInterfaceUrl());
        systemJobCronVO
                .setInterfaceUrlDesc(systemJobService.getById(systemJobCrontab.getJobId()).getDescription());
        systemJobCronVO.setCreateTime(systemJob.getCreateTime());
        systemJobCronVO.setStatus(systemJobCrontab.getStatus());
        systemJobCronVO.setSystemCode(systemJob.getSystemCode());
        systemJobCronVO.setCreateUser(systemJob.getCreateUser());
        systemJobCronVO.setRetryNum(systemJobCrontab.getRetryNum());
        return Result.success("查询成功",systemJobCronVO);
    }

    @Override
    public Result deleteList(List<String> id) {
        for (String s : id) {
            SystemJobCrontab systemJobCrontab = this.getById(s);
            systemJobParamService.remove(new QueryWrapper<SystemJobParam>().eq("job_id",systemJobCrontab.getJobId()));
            systemJobService.remove(new QueryWrapper<SystemJob>().eq("id", systemJobCrontab.getJobId()));
            systemCrontabService.remove(new QueryWrapper<SystemCrontab>().eq("id", systemJobCrontab.getCrontabId()));
            this.removeById(s);
            scheduleTaskService.delTask(s);
        }
        return Result.success("删除成功");
    }
}
