package com.ryota.system.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TODO
 *
 * @Author：lh
 * @description ：时间填充值
 * @Date： 2021/1/815:42
 * @email：liuhui57@cnooc.com.cn
 */
@Component
@Slf4j
public class CustomizeMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time",new Date(),metaObject);
        this.setFieldValByName("update_time",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time",new Date(),metaObject);
    }
}
