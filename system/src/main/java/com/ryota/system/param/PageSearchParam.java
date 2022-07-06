package com.ryota.system.param;

import lombok.Data;

/**
 * @author ex_quyf
 * @Classname PageSearchParam
 * @Description
 * @Date 2022/6/6 16:02
 */
@Data
public class PageSearchParam {

    private Integer pageSize;
    private Integer pageNum;
    private String interfaceUrl;
    private String desc;
    private Boolean status;



}
