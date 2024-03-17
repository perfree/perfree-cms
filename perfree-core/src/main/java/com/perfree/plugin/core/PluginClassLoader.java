package com.perfree.plugin.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import org.pf4j.ClassLoadingStrategy;
import org.pf4j.PluginDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginClassLoader extends URLClassLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginClassLoader.class);

    private static final String JAVA_PACKAGE_PREFIX = "java.";

    private final ClassLoadingStrategy classLoadingStrategy;

    public PluginClassLoader(ClassLoader parent) {
        this(parent,ClassLoadingStrategy.PDA);
    }

    @Deprecated
    public PluginClassLoader(ClassLoader parent, ClassLoadingStrategy classLoadingStrategy) {
        super(new URL[0], parent);
        this.classLoadingStrategy = classLoadingStrategy;
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
            addURL(file.getCanonicalFile().toURI().toURL());
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
    }


    @Override
    public URL getResource(String name) {
        for (ClassLoadingStrategy.Source classLoadingSource : classLoadingStrategy.getSources()) {
            URL url = null;
            switch (classLoadingSource) {
                case APPLICATION:
                    url = super.getResource(name);
                    break;
                case PLUGIN:
                    url = findResource(name);
                    break;
            }

            if (url != null) {
                return url;
            }
        }

        return null;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        List<URL> resources = new ArrayList<>();

        for (ClassLoadingStrategy.Source classLoadingSource : classLoadingStrategy.getSources()) {
            switch (classLoadingSource) {
                case APPLICATION:
                    if (getParent() != null) {
                        resources.addAll(Collections.list(getParent().getResources(name)));
                    }
                    break;
                case PLUGIN:
                    resources.addAll(Collections.list(findResources(name)));
                    break;
            }
        }

        return Collections.enumeration(resources);
    }


    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(className)) {
            if (className.startsWith(JAVA_PACKAGE_PREFIX)) {
                return findSystemClass(className);
            }

           /* if (className.startsWith("org.pf4j.")) {
                return getParent().loadClass(className);
            }*/

            Class<?> loadedClass = findLoadedClass(className);
            if (loadedClass != null) {
                return loadedClass;
            }

            for (ClassLoadingStrategy.Source classLoadingSource : classLoadingStrategy.getSources()) {
                Class<?> c = null;
                try {
                    c = switch (classLoadingSource) {
                        case APPLICATION -> super.loadClass(className);
                        case PLUGIN -> findClass(className);
                        default -> c;
                    };
                } catch (ClassNotFoundException ignored) {
                }

                if (c != null) {
                    return c;
                }
            }

            throw new ClassNotFoundException(className);
        }
    }


}
