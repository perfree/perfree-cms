package com.perfree.system.mapper;

import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.system.model.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface CategoryMapper extends BaseMapperX<Category> {

}
