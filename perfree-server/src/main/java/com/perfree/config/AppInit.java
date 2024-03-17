package com.perfree.config;

import com.perfree.cache.AttachConfigCacheService;
import com.perfree.cache.OptionCacheService;
import com.perfree.file.FileHandleStorageHolder;
import com.perfree.file.handle.local.FileLocalHandleImpl;
import com.perfree.file.handle.s3.FileS3HandleImpl;
import com.perfree.plugin.PluginDevManager;
import com.perfree.plugin.PluginInfo;
import com.perfree.plugin.PluginInfoHolder;
import com.perfree.plugin.PluginManager;
import com.perfree.system.api.attachConfig.dto.AttachConfigCacheDTO;
import com.perfree.system.api.option.dto.OptionCacheDTO;
import com.perfree.convert.attachConfig.AttachConfigConvert;
import com.perfree.convert.option.OptionConvert;
import com.perfree.mapper.AttachConfigMapper;
import com.perfree.model.AttachConfig;
import com.perfree.model.Option;
import com.perfree.service.option.OptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Perfree
 * @description 初始化执行
 * @date 15:39 2023/9/28
 */
@Component
public class AppInit implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunner.class);

    @Value("${server.port}")
    private String port;

    private final PluginManager pluginManager;

    private final PluginDevManager pluginDevManager;

    private final AttachConfigMapper attachConfigMapper;

    private final OptionService optionService;

    private final OptionCacheService optionCacheService;

    private final AttachConfigCacheService attachConfigCacheService;

    public AppInit(PluginManager pluginManager, PluginDevManager pluginDevManager, OptionService optionService,
                   OptionCacheService optionCacheService, AttachConfigMapper attachConfigMapper,AttachConfigCacheService attachConfigCacheService) {
        this.pluginManager = pluginManager;
        this.pluginDevManager = pluginDevManager;
        this.optionService = optionService;
        this.optionCacheService = optionCacheService;
        this.attachConfigMapper = attachConfigMapper;
        this.attachConfigCacheService = attachConfigCacheService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initFileHandle();
        initOptions();
        initAttachConfig();
        pluginManager.initPlugins();
        //   pluginManager.initPluginsByPf4j();
        String command = System.getProperty("sun.java.command");
        if (command != null && !command.contains(".jar")) {
            // 源码运行,加载本地插件
           // pluginDevManager.initPlugins();
        }
        List<PluginInfo> pluginInfoList = PluginInfoHolder.getAllPluginInfo();
        StringBuilder successPluginStr = new StringBuilder();
        pluginInfoList.forEach(pluginInfo -> successPluginStr.append("Success Load Plugin -> ").append(pluginInfo.getPluginPath()).append("\n"));
        String banner = """
                ----------------------------------------------------------------------------------
                                         __                     \s
                                        / _|                    \s
                  _ __     ___   _ __  | |_   _ __    ___    ___\s
                 | '_ \\   / _ \\ | '__| |  _| | '__|  / _ \\  / _ \\
                 | |_) | |  __/ | |    | |   | |    |  __/ |  __/
                 | .__/   \\___| |_|    |_|   |_|     \\___|  \\___|
                 | |                                            \s
                 |_|                                            \s
                 
                 %s
                 Successfully started!
                 access port: %s
                ----------------------------------------------------------------------------------
                """.formatted(successPluginStr, port);
        System.out.println(banner);
    }

    /**
     * 初始化文件上传处理类
     */
    private void initFileHandle() {
        FileHandleStorageHolder.putFileHandleStorage(0, new FileLocalHandleImpl());
        FileHandleStorageHolder.putFileHandleStorage(1, new FileS3HandleImpl());
    }

    /**
     * 初始化附件配置
     */
    private void initAttachConfig() {
        List<AttachConfig> all = attachConfigMapper.getAll();
        List<AttachConfigCacheDTO> attachConfigCacheDTOS = AttachConfigConvert.INSTANCE.convertCacheListDTO(all);
        for (AttachConfigCacheDTO attachConfig : attachConfigCacheDTOS) {
            attachConfigCacheService.putAttachConfig(attachConfig.getId(), attachConfig);
        }
    }

    /**
     * 初始化配置
     */
    private void initOptions() {
        LOGGER.info("start init option cache");
        List<Option> optionList = optionService.getAllOption();
        List<OptionCacheDTO> options = OptionConvert.INSTANCE.convertCacheDTO(optionList);
        for (OptionCacheDTO option : options) {
            optionCacheService.putOption(option.getKey(), option);
        }
        LOGGER.info("init option cache success");
    }
}
