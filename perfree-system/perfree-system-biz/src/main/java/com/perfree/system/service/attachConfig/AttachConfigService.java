package com.perfree.system.service.attachConfig;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfree.commons.common.PageResult;
import com.perfree.system.model.AttachConfig;
import com.perfree.system.vo.attachConfig.AttachConfigCreateVO;
import com.perfree.system.vo.attachConfig.AttachConfigPageReqVO;
import com.perfree.system.vo.attachConfig.AttachConfigUpdateVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface AttachConfigService extends IService<AttachConfig> {


    /**
     * 获取所有配置
     * @return List<AttachConfig>
     */
    List<AttachConfig> getAll();

    /**
     * 新增附件配置
     * @param attachConfigCreateVO attachConfigCreateVO
     * @return AttachConfig
     */
    AttachConfig add(AttachConfigCreateVO attachConfigCreateVO);

    /**
     * 修改附件配置
     * @param attachConfigUpdateVO attachConfigUpdateVO
     * @return AttachConfig
     */
    Boolean updateAttachConfig(AttachConfigUpdateVO attachConfigUpdateVO);

    /**
     * 删除附件配置
     * @param id id
     * @return Boolean
     */
    Boolean del(Integer id);

    /**
     * 分页查询附件配置
     * @param pageVO pageVO
     * @return PageResult<AttachConfig>
     */
    PageResult<AttachConfig> attachConfigPage(AttachConfigPageReqVO pageVO);

}
