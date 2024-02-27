package com.perfree.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.perfree.system.api.option.dto.OptionCacheDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Perfree
 * @description 配置缓存相关
 * @date 15:35 2023/9/28
 */
@Service
public class OptionCacheService {
    private final Cache<String, OptionCacheDTO> optionCache;

    public OptionCacheService() {
        optionCache = CacheBuilder.newBuilder().build();
    }

    public void putOption(String key, OptionCacheDTO optionCacheDTO) {
        optionCache.put(key, optionCacheDTO);
    }

    public OptionCacheDTO getOption(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return optionCache.getIfPresent(key);
    }

    public void removeOption(String key) {
        optionCache.invalidate(key);
    }

    public List<OptionCacheDTO> getAllOption() {
        return optionCache.asMap().values().stream().toList();
    }
}
