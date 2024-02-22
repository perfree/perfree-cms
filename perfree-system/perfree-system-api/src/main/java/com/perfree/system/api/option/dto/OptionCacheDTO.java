package com.perfree.system.api.option.dto;

import lombok.Data;

@Data
public class OptionCacheDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String value;
}
