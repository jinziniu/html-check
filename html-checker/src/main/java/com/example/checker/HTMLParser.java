package main.java.com.example.checker;

import java.util.List;

public class HTMLParser {
    private PluginManager pluginManager = new PluginManager();

    public HTMLParser() {
        // 注册插件
        pluginManager.registerPlugin(new MissingIdChecker());
        pluginManager.registerPlugin(new InternationalizationChecker());
    }

    public List<String> parse(String content, List<String> lines) {
        return pluginManager.runChecks(content, lines);
    }
}