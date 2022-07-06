package com.ryota.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.ryota.common.common.PageFormat;
import com.ryota.common.common.Result;
import com.ryota.system.entity.SystemJob;
import com.ryota.system.entity.SystemJobLog;
import com.ryota.system.mapper.SystemJobLogMapper;
import com.ryota.system.param.PageSearch;
import com.ryota.system.service.SystemJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ryota.system.service.SystemJobService;
import com.ryota.system.vo.SystemJobVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 定时任务执行结果 服务实现类
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Service
public class SystemJobLogServiceImpl extends ServiceImpl<SystemJobLogMapper, SystemJobLog> implements SystemJobLogService {

    @Autowired
    private SystemJobService systemJobService;

    @Override
    public Result getList(PageSearch pageSearch) {
        List<SystemJobVO> jobVOList = new ArrayList<>();
        PageHelper.startPage(pageSearch.getPageNum(), pageSearch.getPageSize());
        List<SystemJobLog> list = this.list();
        for (SystemJobLog systemJobLog : list) {
            SystemJobVO systemJobVO = new SystemJobVO();
            SystemJob systemJob = systemJobService.getOne(new QueryWrapper<SystemJob>().eq("id", systemJobLog.getJobId()));
            BeanUtils.copyProperties(systemJobLog,systemJobVO);
            systemJobVO.setJobDesc(systemJob.getDescription());
            jobVOList.add(systemJobVO);
        }
        return Result.success("查询成功", new PageFormat(jobVOList));
    }
}
