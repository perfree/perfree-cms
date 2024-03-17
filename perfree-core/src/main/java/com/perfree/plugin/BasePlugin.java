package com.perfree.plugin;

import org.pf4j.Plugin;

public class BasePlugin extends Plugin {

    public String scanPackage(){
        return this.getClass().getPackage().getName();
    }
}
