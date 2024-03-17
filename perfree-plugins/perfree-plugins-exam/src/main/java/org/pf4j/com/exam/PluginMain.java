package org.pf4j.com.exam;

import org.pf4j.Plugin;
import org.springframework.stereotype.Service;

@Service
public class PluginMain extends Plugin {

    @Override
    public void start() {
        System.out.println("start");
        super.start();
    }

    @Override
    public void stop() {
        System.out.println("stop");
        super.stop();
    }

    @Override
    public void delete() {
        System.out.println("delete");
        super.delete();
    }
}
