package com.ryota.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 定时任务执行周期设定
 * </p>
 *
 * @author qyf
 * @since 2022-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SystemJobCrontab对象", description="定时任务执行周期设定")
public class SystemJobCrontab implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "定时任务ID")
    @TableField("JOB_ID")
    private String jobId;

    @ApiModelProperty(value = "执行周期ID")
    @TableField("CRONTAB_ID")
    private String crontabId;

    @ApiModelProperty(value = "描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private Boolean status;

    @ApiModelProperty(value = "重试次数")
    @TableField("RETRY_NUM")
    private Integer retryNum;


}
