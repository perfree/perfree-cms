package com.perfree.plugin;

import com.perfree.plugin.core.PluginClassLoader;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Perfree
 * @description 定义JarClassLoader管理集合
 * @date 15:35 2023/9/28
 */
public abstract class PluginClassLoaderHolder {

	private final static Map<String, PluginClassLoader> pluginJarClassLoader = new ConcurrentHashMap<>();

	/**
	 * 新增jarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:07
	 * @param pluginId 插件id
	 * @param jarClassLoader jarClassLoader
	 */
	public static void addPluginJarClassLoader(String pluginId, PluginClassLoader jarClassLoader) {
		pluginJarClassLoader.put(pluginId, jarClassLoader);
	}

	/**
	 * 根据插件id移除JarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:44
	 * @param pluginId 插件id
	 */
	public static void removePluginJarClassLoader(String pluginId) throws IOException {
		PluginClassLoader remove = pluginJarClassLoader.remove(pluginId);
		if (remove != null) {
			((Closeable) remove).close();
		}
		System.gc();
	}

	/**
	 * 根据插件id获取JarClassLoader
	 * @author perfree
	 * @date 2023-09-27 14:09:02
	 * @param pluginId 插件id
	 * @return JarClassLoader
	 */
	public static PluginClassLoader getJarClassLoader(String pluginId) {
		return pluginJarClassLoader.get(pluginId);
	}

	public static String getPluginId(PluginClassLoader pluginClassLoader) {
		for (String s : pluginJarClassLoader.keySet()) {
			if (pluginJarClassLoader.get(s).equals(pluginClassLoader)) {
				return s;
			}

		}
		return null;
	}
}
