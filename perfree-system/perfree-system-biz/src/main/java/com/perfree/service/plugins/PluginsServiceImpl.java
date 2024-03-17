package com.perfree.service.plugins;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.commons.common.PageResult;
import com.perfree.mapper.PluginsMapper;
import com.perfree.model.Plugins;
import com.perfree.plugin.PluginManager;
import com.perfree.vo.plugins.PluginsPageReqVO;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

    private final static Logger LOGGER = LoggerFactory.getLogger(PluginsServiceImpl.class);
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
            LOGGER.error("", e);
            throw new RuntimeException(e);
        }
    }
}
