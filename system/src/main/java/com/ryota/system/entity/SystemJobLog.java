package com.ryota.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 定时任务执行结果
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SystemJobLog对象", description="定时任务执行结果")
public class SystemJobLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "任务ID")
    @TableField("JOB_ID")
    private String jobId;

    @ApiModelProperty(value = "执行时间")
    @TableField("EXEC_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date execTime;

    @ApiModelProperty(value = "结果类型 1:正常 2:异常")
    @TableField("RESULT_TYPE")
    private Integer resultType;

    @ApiModelProperty(value = "结果内容")
    @TableField("RESULT")
    private String result;


}
