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
 * 定时任务参数
 * </p>
 *
 * @author qyf
 * @since 2022-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SystemJobParam对象", description="定时任务参数")
public class SystemJobParam implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "任务ID")
    @TableField("JOB_ID")
    private String jobId;

    @ApiModelProperty(value = "参数Key")
    @TableField("PARAM_KEY")
    private String paramKey;

    @ApiModelProperty(value = "参数Value")
    @TableField("PARAM_VALUE")
    private String paramValue;


}
