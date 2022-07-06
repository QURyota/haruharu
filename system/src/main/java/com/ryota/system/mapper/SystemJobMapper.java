package com.ryota.system.mapper;

import com.ryota.system.entity.SystemJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Mapper
public interface SystemJobMapper extends BaseMapper<SystemJob> {

}
