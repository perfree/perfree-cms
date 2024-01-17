package com.perfree.system.service.plugins;

import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Plugins;
import com.perfree.system.mapper.PluginsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.system.vo.plugins.PluginsPageReqVO;
import jakarta.annotation.Resource;
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
public class PluginsServiceImpl extends ServiceImpl<PluginsMapper, Plugins> implements PluginsService {

    @Resource
    private PluginsMapper pluginsMapper;


    @Override
    public PageResult<Plugins> pluginsPage(PluginsPageReqVO pageVO) {
        return pluginsMapper.selectPage(pageVO);
    }
}
