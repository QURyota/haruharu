package com.ryota.system.controller;


import com.github.pagehelper.PageHelper;
import com.ryota.common.common.PageFormat;
import com.ryota.common.common.Result;
import com.ryota.system.entity.SystemJobLog;
import com.ryota.system.param.PageSearch;
import com.ryota.system.service.SystemJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 定时任务执行结果 前端控制器
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@RestController
@RequestMapping("/system-job-log")
@Api(tags = "日志操作")
public class SystemJobLogController {

    @Autowired
    private SystemJobLogService systemJobLogService;

    @PostMapping("list")
    @ApiOperation("返回日志查询")
    public Result getList(@RequestBody PageSearch pageSearch){
       return systemJobLogService.getList(pageSearch);
    }

}

