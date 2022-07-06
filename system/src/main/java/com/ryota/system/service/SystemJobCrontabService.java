package com.ryota.system.service;

import com.ryota.common.common.Result;
import com.ryota.system.entity.SystemJobCrontab;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryota.system.param.CronParam;
import com.ryota.system.param.PageSearch;
import com.ryota.system.param.PageSearchParam;

import java.util.List;

/**
 * <p>
 * 定时任务执行周期设定 服务类
 * </p>
 *
 * @author qyf
 * @since 2022-06-04
 */
public interface SystemJobCrontabService extends IService<SystemJobCrontab> {

    /**
     * 创建定时任务
     * @param cronParam
     * @return
     */
    Result add(CronParam cronParam);

    /**
     * 获取定时任务列表
     * @return
     */
    Result getList(PageSearch pageSearch);

    /**
     * 停止定时任务
     * @param id
     * @return
     */
    Result stop(String id);

    /**
     * 启动定时任务 不带参数
     * @param id
     * @return
     */
    Result startTask(String id);

    Result testStartTask(String id);

    /**
     * 删除定时任务
     * @param id
     * @return
     */
    Result del(String id);

    /**
     * 条件分页查询
     * @param pageSearchParam
     * @return
     */
    Result getListByParam(PageSearchParam pageSearchParam);

    /**
     * 详情页
     * @param id
     * @return
     */
    Result get(String id);

    /**
     * 批量删除
     * @param id
     * @return
     */
    Result deleteList(List<String> id);

}
