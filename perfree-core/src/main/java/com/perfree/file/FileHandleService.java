package com.perfree.file;

import com.perfree.cache.OptionCacheService;
import com.perfree.commons.exception.ServiceException;
import com.perfree.commons.utils.SpringBeanUtil;
import com.perfree.enums.OptionEnum;
import com.perfree.file.handle.BaseFileHandle;
import com.perfree.file.handle.FileLocalHandleImpl;
import com.perfree.plugin.PluginApplicationContextHolder;
import com.perfree.system.api.option.dto.OptionCacheDTO;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件处理器
 * @create 2021-08-17 16:07
 */
@Service
public class FileHandleService {

    @Resource
    private OptionCacheService optionCacheService;

    /**
     * 获取文件处理器
     * @return BaseFileHandle
     */
    public BaseFileHandle getFileHandle() {
        OptionCacheDTO option = optionCacheService.getOption(OptionEnum.DEFAULT_FILE_HANDLE.getKey());
        if (null == option) {
            return SpringBeanUtil.context.getBean(FileLocalHandleImpl.class);
        }
        boolean masterHasBean = SpringBeanUtil.context.containsBean(option.getValue());
        if (masterHasBean) {
            return (BaseFileHandle) SpringBeanUtil.context.getBean(option.getValue());
        }
        // 如果主程序未匹配到,去尝试匹配插件的文件处理器
        List<AnnotationConfigApplicationContext> allPluginApplicationContext = PluginApplicationContextHolder.getAllPluginApplicationContext();
        for (AnnotationConfigApplicationContext annotationConfigApplicationContext : allPluginApplicationContext) {
            boolean pluginHasBean = annotationConfigApplicationContext.containsBean(option.getValue());
            if (pluginHasBean) {
                return (BaseFileHandle) annotationConfigApplicationContext.getBean(option.getValue());
            }
        }
       throw new ServiceException(500, "未匹配到文件处理器,请检查文件处配置!");
    }

}
