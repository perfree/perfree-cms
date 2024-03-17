package com.perfree.plugin.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;

public class PluginClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    private ClassLoader parent;

    private static final String JAVA_PACKAGE_PREFIX = "java.";

    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.parent = parent;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.parent = null;
    }

    public PluginClassLoader addJar(File jarFileOrDir) {
        if (isJarFile(jarFileOrDir)) {
            return this.addURL(jarFileOrDir);
        } else {
            List<File> jars = loopJar(jarFileOrDir);
            Iterator var3 = jars.iterator();

            while(var3.hasNext()) {
                File jar = (File)var3.next();
                this.addURL(jar);
            }

            return this;
        }
    }

    public void addURL(URL url) {
        super.addURL(url);
    }

    public PluginClassLoader addURL(File dir) {
        super.addURL(URLUtil.getURL(dir));
        return this;
    }

    private static List<File> loopJar(File file) {
        return FileUtil.loopFiles(file, PluginClassLoader::isJarFile);
    }

    private static boolean isJarFile(File file) {
        return !FileUtil.isFile(file) ? false : file.getPath().toLowerCase().endsWith(".jar");
    }


    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(className)) {
            if (className.startsWith(JAVA_PACKAGE_PREFIX)) {
                return findSystemClass(className);
            }

            if (className.startsWith("com.perfree")) {
                return getParent().loadClass(className);
            }

            Class<?> loadedClass = findLoadedClass(className);
            if (loadedClass != null) {
                return loadedClass;
            }

            if (className.startsWith("com.baomidou.mybatisplus")) {
                return getParent().loadClass(className);
            }

            Class<?> c = findClass(className);
            if (c != null) {
                return c;
            }
            c = super.loadClass(className);
            if (c != null) {
                return c;
            }
        }
        throw new ClassNotFoundException(className);
    }
}
