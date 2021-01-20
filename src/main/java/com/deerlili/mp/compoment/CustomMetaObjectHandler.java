package com.deerlili.mp.compoment;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author deerlili
 * @version v1.0
 * @description 自定义字段填充
 * @Time 2021-01-20 23:31
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入时填充,实体中的属性名,不是数据库中的字段名
        boolean hasSetter = metaObject.hasSetter("createTime");
        if (hasSetter) {// 有自动填充字段调用，没有不调用
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时填充,实体中的属性名,不是数据库中的字段名
        boolean hasSetter = metaObject.hasSetter("updateTime");
        if (hasSetter) {// 有自动填充字段调用，没有不调用
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
