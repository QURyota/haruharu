package com.ryota.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author quyifan
 * @create 2022/6/5 21:38
 */
@Data
public class SystemJobVO {

    private String id;

    @ApiModelProperty(value = "任务ID")
    private String jobId;

    @ApiModelProperty(value = "任务描述")
    private String jobDesc;

    @ApiModelProperty(value = "执行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date execTime;

    @ApiModelProperty(value = "结果类型 1:正常 2:异常")
    private Integer resultType;

    @ApiModelProperty(value = "结果内容")
    private String result;
}
