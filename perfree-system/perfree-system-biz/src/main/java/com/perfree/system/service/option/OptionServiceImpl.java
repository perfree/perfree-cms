package com.perfree.system.service.option;

import com.perfree.system.model.Option;
import com.perfree.system.mapper.OptionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option> implements OptionService {

    @Resource
    private OptionMapper optionMapper;

    @Override
    public List<Option> getAllOption() {
        return optionMapper.selectList();
    }
}
