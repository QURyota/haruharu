package com.ryota.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.ryota.common.common.PageFormat;
import com.ryota.common.common.Result;
import com.ryota.system.config.SendSignHttpsClient;
import com.ryota.system.entity.SystemJob;
import com.ryota.system.param.CronParam;
import com.ryota.system.param.PageSearch;
import com.ryota.system.param.PageSearchParam;
import com.ryota.system.param.TestParam;
import com.ryota.system.service.ScheduleTaskService;
import com.ryota.system.service.SystemJobCrontabService;
import com.ryota.system.service.SystemJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 定时任务执行周期设定 前端控制器
 * </p>
 *
 * @author qyf
 * @since 2022-06-04
 */
@RestController
@RequestMapping("/system-job-crontab")
@Api(tags = "定时任务操作")
public class SystemJobCrontabController {


    @Autowired
    private SystemJobCrontabService systemJobCrontabService;

    @PostMapping("listPage")
    @ApiOperation("条件分页查询定时任务")
    public Result list(@RequestBody PageSearchParam pageSearchParam){
        return systemJobCrontabService.getListByParam(pageSearchParam);
    }

    @PostMapping("add")
    @ApiOperation("创建-更新定时任务")
    public Result add(@RequestBody CronParam cronParam){
        return systemJobCrontabService.add(cronParam);
    }

    @PostMapping("startTask")
    @ApiOperation("启动定时任务")
    public Result startTask(@RequestParam String id ){
        return systemJobCrontabService.startTask(id);
    }

    @PostMapping("testStartTask")
    @ApiOperation("测试启动定时任务")
    public Result testStartTask(@RequestParam String id ){
        return systemJobCrontabService.testStartTask(id);
    }

    @DeleteMapping("stopTask")
    @ApiOperation("停止定时任务")
    public Result stop(@RequestParam String id){
        return systemJobCrontabService.stop(id);
    }

    @DeleteMapping("del")
    @ApiOperation("删除定时任务")
    public Result del(@RequestBody List<String> id){
        return systemJobCrontabService.deleteList(id);
    }


    @GetMapping("get")
    @ApiOperation("详情页")
    public Result get(@RequestParam String id){
        return systemJobCrontabService.get(id);
    }

    @PostMapping("testSchedulePost")
    public Result testSchedulePost(){
        List<String> strings = new ArrayList<>();
        strings.add("123");
        strings.add("234");
        return Result.success("调用成功",new PageFormat(strings));
    }

    @PostMapping("testPost")
    public Result testParam(@RequestBody TestParam testParam){
        System.out.println(testParam.getName());
        System.out.println(testParam.getValue());
        return Result.success("testPost",testParam);
    }



}

