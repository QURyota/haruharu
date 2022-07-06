package com.ryota.system.service.impl;

import com.ryota.system.entity.SystemJob;
import com.ryota.system.mapper.SystemJobMapper;
import com.ryota.system.service.SystemJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Service
public class SystemJobServiceImpl extends ServiceImpl<SystemJobMapper, SystemJob> implements SystemJobService {

}
