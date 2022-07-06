package com.ryota.system.service.impl;

import com.ryota.system.entity.SystemJobParam;
import com.ryota.system.mapper.SystemJobParamMapper;
import com.ryota.system.service.SystemJobParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 定时任务参数 服务实现类
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Service
public class SystemJobParamServiceImpl extends ServiceImpl<SystemJobParamMapper, SystemJobParam> implements SystemJobParamService {

}
