package main.java.com.example.checker;

import java.util.List;

public interface CheckerPlugin {
    void check(String content, List<String> results, List<String> lines);
}