package com.perfree.plugin.core;

import com.perfree.plugin.commons.PluginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    private static final String JAVA_PACKAGE_PREFIX = "java.";


    public PluginClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    public void addURL(URL url) {
        super.addURL(url);
    }

    public void addFile(File file) {
        try {
            File codeFile = new File(file.getAbsolutePath() + File.separator + PluginUtils.CODE_DIR);
            addURL(codeFile.getCanonicalFile().toURI().toURL());
            File libFile = new File(file.getAbsolutePath() + File.separator + PluginUtils.LIB_DIR);
            if (libFile.exists() && libFile.listFiles() != null) {
                File[] files = libFile.listFiles();
                if (null == files || files.length == 0) {
                    return;
                }
                for (File jarFile : files) {
                    addURL(jarFile.toURI().toURL());
                }
            }
        } catch (IOException e) {
            LOGGER.error("PluginClassLoader addFile error", e);
            throw new RuntimeException(e);
        }
    }



    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(className)) {
            if (className.startsWith(JAVA_PACKAGE_PREFIX)) {
                return findSystemClass(className);
            }

            Class<?> loadedClass = findLoadedClass(className);
            if (loadedClass != null) {
                return loadedClass;
            }

            try {
                Class<?> c = findClass(className);
                if (c != null) {
                    return c;
                }
            }catch (Exception e) {
                return super.loadClass(className);
            }
        }
        throw new ClassNotFoundException();
    }


}
