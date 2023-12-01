package com.perfree.plugin;

import lombok.Data;

import java.util.List;

/**
 * @author Perfree
 * @description 插件信息
 * @date 15:35 2023/9/28
 */
@Data
public class PluginInfo {

    // 插件ID
    private Integer pluginId;

    // 插件内Class集合
    private List<Class<?>> classList;
}
