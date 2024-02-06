package com.perfree.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * mybatis-plus字段填充
 */
@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        boolean hasCreateTime = metaObject.hasGetter("createTime");
        if (hasCreateTime) {
            Object createTime = getFieldValByName("createTime", metaObject);
            if (Objects.isNull(createTime)) {
                setFieldValByName("createTime", new Date(), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean updateTime = metaObject.hasGetter("updateTime");
        if (updateTime) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}
