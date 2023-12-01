package com.perfree.service.impl;

import com.perfree.model.Category;
import com.perfree.mapper.CategoryMapper;
import com.perfree.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
