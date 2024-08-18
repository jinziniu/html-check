package main.java.com.example.checker;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<CheckerPlugin> plugins = new ArrayList<>();

    public void registerPlugin(CheckerPlugin plugin) {
        plugins.add(plugin);
    }

    public List<String> runChecks(String content, List<String> lines) {
        List<String> results = new ArrayList<>();
        for (CheckerPlugin plugin : plugins) {
            plugin.check(content, results, lines);
        }
        return results;
    }
}