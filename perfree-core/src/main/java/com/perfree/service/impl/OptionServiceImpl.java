package com.perfree.service.impl;

import com.perfree.model.Option;
import com.perfree.mapper.OptionMapper;
import com.perfree.service.IOptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements IOptionService {

}
