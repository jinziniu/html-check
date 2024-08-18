package main.java.com.example.checker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {
    private List<String> results = new ArrayList<>();
    private List<String> lines = new ArrayList<>();

    /**
     * 扫描指定目录下的所有HTML文件
     * @param path 目录路径
     */
    public void scanDirectory(String path) {
        try {
            List<File> fileList = new ArrayList<File>();
            collectFiles(new File(path), fileList);

            for (File file : fileList) {
                if (file.getName().endsWith(".html")) {
                    scanFile(file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            handleError(ErrorCode.FILE_ACCESS_ERROR, e);
        }
    }

    /**
     * 递归收集目录中的所有文件
     * @param dir 目录文件
     * @param fileList 文件列表
     * @throws IOException 如果访问文件时出错
     */
    private void collectFiles(File dir, List<File> fileList) throws IOException {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectFiles(file, fileList);
                } else {
                    fileList.add(file);
                }
            }
        }
    }

    /**
     * 扫描指定的HTML文件
     * @param path 文件路径
     */
    public void scanFile(String path) {
        System.out.println("Scanning file: " + path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            StringBuilder contentBuilder = new StringBuilder();

            // 逐行读取文件并保存行信息
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                contentBuilder.append(line).append("\n");
            }

            // 获取完整的HTML内容
            String content = contentBuilder.toString();

            // 通过HTMLParser执行检查
            HTMLParser parser = new HTMLParser();
            List<String> fileResults = parser.parse(content, lines);
            results.addAll(fileResults);
        } catch (IOException e) {
            handleError(ErrorCode.FILE_NOT_FOUND, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // 忽略关闭流的异常
                }
            }
        }
    }

    /**
     * 获取检查结果
     * @return 结果列表
     */
    public List<String> getResults() {
        return results;
    }

    /**
     * 处理扫描过程中的错误
     * @param errorCode 错误代码
     * @param e 异常信息
     */
    private void handleError(int errorCode, Exception e) {
        System.err.println("Error Code: " + errorCode);
        e.printStackTrace();
    }
}