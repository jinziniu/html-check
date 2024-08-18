package main.java.com.example.checker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class InternationalizationChecker implements CheckerPlugin {
    @Override
    public void check(String content, List<String> results, List<String> lines) {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.body().getAllElements();

        for (Element element : elements) {
            String text = element.ownText();
            if (containsChineseCharacters(text)) {
                int[] position = findPosition(element.outerHtml(), lines);
                results.add("Non-internationalized text at line " + position[0] + ", column " + position[1] + ": " + text);
            }
        }
    }

    private boolean containsChineseCharacters(String text) {
        for (char ch : text.toCharArray()) {
            if (Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                return true;
            }
        }
        return false;
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