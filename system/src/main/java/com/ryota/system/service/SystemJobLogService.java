package com.ryota.system.service;

import com.ryota.common.common.Result;
import com.ryota.system.entity.SystemJobLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ryota.system.param.PageSearch;

/**
 * <p>
 * 定时任务执行结果 服务类
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
public interface SystemJobLogService extends IService<SystemJobLog> {

    /**
     * 获取返回日志list
     * @param pageSearch
     * @return
     */
    Result getList(PageSearch pageSearch);

}
