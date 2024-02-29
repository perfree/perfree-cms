package com.perfree.system.service.plugins;

import cn.hutool.core.io.FileUtil;
import com.perfree.commons.common.PageResult;
import com.perfree.plugin.PluginManager;
import com.perfree.system.model.Plugins;
import com.perfree.system.mapper.PluginsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.system.vo.plugins.PluginsPageReqVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @Resource
    private PluginManager pluginManager;

    @Value("${perfree.temp-dir}")
    private String tempDir;


    @Override
    public PageResult<Plugins> pluginsPage(PluginsPageReqVO pageVO) {
        return pluginsMapper.selectPage(pageVO);
    }

    @Override
    public Boolean installPlugin(MultipartFile file) {
        try {
            File dir = new File(tempDir + File.separator + "plugin");
            if (!dir.exists()) {
                FileUtil.mkdir(dir.getAbsolutePath());
            }
            File pluginFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            file.transferTo(pluginFile);
            pluginManager.installPlugin(pluginFile);
            FileUtil.del(pluginFile);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
