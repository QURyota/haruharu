package com.ryota.system.service.impl;

import com.ryota.system.entity.SystemCrontab;
import com.ryota.system.mapper.SystemCrontabMapper;
import com.ryota.system.service.SystemCrontabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务执行周期 服务实现类
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Service
public class SystemCrontabServiceImpl extends ServiceImpl<SystemCrontabMapper, SystemCrontab> implements SystemCrontabService {

}
