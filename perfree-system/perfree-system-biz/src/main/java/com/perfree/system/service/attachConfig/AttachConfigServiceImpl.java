package com.perfree.system.service.attachConfig;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfree.cache.AttachConfigCacheService;
import com.perfree.commons.common.PageResult;
import com.perfree.system.api.attachConfig.dto.AttachConfigCacheDTO;
import com.perfree.system.convert.attachConfig.AttachConfigConvert;
import com.perfree.system.mapper.AttachConfigMapper;
import com.perfree.system.model.AttachConfig;
import com.perfree.system.vo.attachConfig.AttachConfigCreateVO;
import com.perfree.system.vo.attachConfig.AttachConfigPageReqVO;
import com.perfree.system.vo.attachConfig.AttachConfigUpdateMasterVO;
import com.perfree.system.vo.attachConfig.AttachConfigUpdateVO;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class AttachConfigServiceImpl extends ServiceImpl<AttachConfigMapper, AttachConfig> implements AttachConfigService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachConfigServiceImpl.class);

    @Resource
    private AttachConfigMapper attachConfigMapper;

    @Resource
    private AttachConfigCacheService attachConfigCacheService;

    @Override
    public List<AttachConfig> getAll() {
        return attachConfigMapper.getAll();
    }

    @Override
    @Transactional
    public AttachConfig add(AttachConfigCreateVO attachConfigCreateVO) {
        AttachConfig attachConfig = AttachConfigConvert.INSTANCE.convertCreateVO(attachConfigCreateVO);
        attachConfigMapper.insert(attachConfig);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), AttachConfigConvert.INSTANCE.convertCacheDTO(attachConfig));
        return attachConfig;
    }

    @Override
    @Transactional
    public Boolean updateAttachConfig(AttachConfigUpdateVO attachConfigUpdateVO) {
        AttachConfig attachConfig = AttachConfigConvert.INSTANCE.convertUpdateVO(attachConfigUpdateVO);
        attachConfigMapper.updateById(attachConfig);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), AttachConfigConvert.INSTANCE.convertCacheDTO(attachConfig));
        return true;
    }

    @Override
    @Transactional
    public Boolean del(Integer id) {
        attachConfigMapper.deleteById(id);
        attachConfigCacheService.removeAttachConfig(id);
        return true;
    }

    @Override
    public PageResult<AttachConfig> attachConfigPage(AttachConfigPageReqVO pageVO) {
        return attachConfigMapper.attachConfigPage(pageVO);
    }

    @Override
    public Boolean updateMaster(AttachConfigUpdateMasterVO attachConfigUpdateMasterVO) {
        attachConfigMapper.clearMaster();
        attachConfigMapper.updateMaster(attachConfigUpdateMasterVO.getId());

        AttachConfigCacheDTO masterAttachConfig = attachConfigCacheService.getMasterAttachConfig();
        masterAttachConfig.setMaster(false);
        attachConfigCacheService.putAttachConfig(masterAttachConfig.getId(), masterAttachConfig);

        AttachConfigCacheDTO attachConfig = attachConfigCacheService.getAttachConfig(attachConfigUpdateMasterVO.getId());
        attachConfig.setMaster(true);
        attachConfigCacheService.putAttachConfig(attachConfig.getId(), attachConfig);
        return true;
    }
}
