package com.ryota.system.mapper;

import com.ryota.system.entity.SystemJobCrontab;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ryota.system.param.PageSearchParam;
import com.ryota.system.vo.SystemJobCronVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 定时任务执行周期设定 Mapper 接口
 * </p>
 *
 * @author qyf
 * @since 2022-06-04
 */
@Mapper
public interface SystemJobCrontabMapper extends BaseMapper<SystemJobCrontab> {

    List<SystemJobCronVO> getListByParam(PageSearchParam pageSearchParam);

}
