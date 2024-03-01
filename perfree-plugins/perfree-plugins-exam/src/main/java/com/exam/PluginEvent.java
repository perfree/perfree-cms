package com.exam;

import com.perfree.plugin.BasePluginEvent;
import org.springframework.stereotype.Service;

@Service
public class PluginEvent implements BasePluginEvent {
    @Override
    public void onStart() {
        System.out.println("插件onStart");
    }

    @Override
    public void onStop() {
        System.out.println("插件onStop");
    }

    @Override
    public void onUpdate() {
        System.out.println("插件onUpdate");
    }

    @Override
    public void onInstall() {
        System.out.println("插件onInstall");
    }

    @Override
    public void onUnInstall() {
        System.out.println("插件onUnInstall");
    }
}
