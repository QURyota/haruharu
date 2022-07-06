package com.ryota.common.controller;

import com.ryota.common.common.Result;
import com.ryota.common.vo.ResultVO;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author quyifan
 * @create 2022/6/4 11:34
 */
@RestController("commonController")
public class CommonController {

    @PostMapping("test")
    public ResultVO test(){
        System.out.println("test");
        return ResultVO.success("测试成功",new String("123"));
    }

}
