package main.java.com.example.checker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * MissingIdChecker插件用于检测HTML内容中缺少ID属性的交互元素。
 * 它会查找按钮、输入框、选择器和带有ng-click指令的元素，确保它们都有ID属性。
 */
public class MissingIdChecker implements CheckerPlugin {

    /**
     * 检查给定的HTML内容，查找没有ID属性的交互元素。
     *
     * @param content HTML内容字符串。
     * @param results 结果列表，用于存储发现的缺少ID属性的元素信息。
     */
    @Override
    public void check(String content, List<String> results, List<String> lines) {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("button, input, select, [ng-click]");

        for (Element element : elements) {
            if (!element.hasAttr("id")) {
                int[] position = findPosition(element.outerHtml(), lines);
                results.add("Missing ID at line " + position[0] + ", column " + position[1] + ": <" + element.tagName() + "> with attributes " + element.attributes());
            }
        }
    }

    private int[] findPosition(String elementHtml, List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            int col = lines.get(i).indexOf(elementHtml);
            if (col != -1) {
                return new int[]{i + 1, col + 1};  // 返回行号（从1开始）和列号（从1开始）
            }
        }
        return new int[]{-1, -1};  // 如果找不到，返回-1
    }
}