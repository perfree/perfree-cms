package com.perfree.system.service.option;

import com.perfree.system.model.Option;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
public interface OptionService extends IService<Option> {

    /**
     * 获取所有配置
     * @return List<Option>
     */
    List<Option> getAllOption();

}
