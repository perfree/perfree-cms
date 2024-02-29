package com.perfree.system.service.plugins;

import com.perfree.commons.common.PageResult;
import com.perfree.system.model.Plugins;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.system.vo.plugins.PluginsPageReqVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface PluginsService extends IService<Plugins> {

    /**
     * 插件分页列表
     * @param pageVO pageVO
     * @return PageResult<Plugins>
     */
    PageResult<Plugins> pluginsPage(PluginsPageReqVO pageVO);

    /**
     * 安装插件
     * @param file file
     * @return Boolean
     */
    Boolean installPlugin(MultipartFile file);

}
