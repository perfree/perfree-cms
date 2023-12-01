package com.perfree.plugin;

import cn.hutool.core.lang.JarClassLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Perfree
 * @description 定义JarClassLoader管理集合
 * @date 15:35 2023/9/28
 */
public abstract class PluginClassLoaderHolder {

	private final static Map<Integer, JarClassLoader> pluginJarClassLoader = new ConcurrentHashMap<>();

	/**
	 * 新增jarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:07
	 * @param pluginId 插件id
	 * @param jarClassLoader jarClassLoader
	 */
	public static void addPluginJarClassLoader(Integer pluginId, JarClassLoader jarClassLoader) {
		pluginJarClassLoader.put(pluginId, jarClassLoader);
	}

	/**
	 * 根据插件id移除JarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:44
	 * @param pluginId 插件id
	 */
	public static void removePluginJarClassLoader(Integer pluginId) throws IOException {
		pluginJarClassLoader.get(pluginId).close();
		pluginJarClassLoader.remove(pluginId);
	}

	/**
	 * 根据插件id获取JarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:02
	 * @param pluginId 插件id
	 * @return JarClassLoader
	 */
	public static JarClassLoader getJarClassLoader(Integer pluginId) {
		return pluginJarClassLoader.get(pluginId);
	}

}
