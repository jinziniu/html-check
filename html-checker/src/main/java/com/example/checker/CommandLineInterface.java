package main.java.com.example.checker;

import java.io.File;
import java.util.List;

public class CommandLineInterface {
    private String reportFilePath;

    public void parseArguments(String[] args) {
        if (args.length < 2) {
            showHelp(ErrorCode.COMMAND_LINE_ERROR);
            return;
        }

        String option = args[0];
        String path = args[1];

        if (args.length > 2 && (args[2].equals("--report-file") || args[2].equals("-r"))) {
            if (args.length > 3) {
                reportFilePath = args[3];
            } else {
                showHelp(ErrorCode.COMMAND_LINE_ERROR);
                return;
            }
        }

        switch (option) {
            case "--directory":
            case "-d":
                if (new File(path).isDirectory()) {
                    scanDirectory(path);
                } else {
                    System.out.println("错误：指定的目录不存在。请检查路径是否正确。");
                }
                break;
            case "--file":
            case "-f":
                if (new File(path).isFile()) {
                    scanFile(path);
                } else {
                    System.out.println("错误：指定的文件不存在。请检查路径是否正确。");
                }
                break;
            case "--help":
            case "-h":
                showHelp(0);
                break;
            default:
                showHelp(ErrorCode.COMMAND_LINE_ERROR);
                break;
        }
    }

    private void scanDirectory(String path) {
        FileScanner scanner = new FileScanner();
        scanner.scanDirectory(path);
        List<String> results = scanner.getResults();

        printResults(results);

        if (reportFilePath != null) {
            saveResultsToFile(results);
        }
    }

    private void scanFile(String path) {
        FileScanner scanner = new FileScanner();
        scanner.scanFile(path);
        List<String> results = scanner.getResults();

        printResults(results);

        if (reportFilePath != null) {
            saveResultsToFile(results);
        }
    }

    private void printResults(List<String> results) {
        System.out.println("扫描完成。");
        for (String result : results) {
            System.out.println(result);
        }
    }

    private void saveResultsToFile(List<String> results) {
        StringBuilder contentBuilder = new StringBuilder();
        for (String result : results) {
            contentBuilder.append(result).append(System.lineSeparator());
        }

        String content = contentBuilder.toString();

        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport(reportFilePath, content);
    }

    private void showHelp(int errorCode) {
        if (errorCode != 0) {
            System.out.println("Error Code: " + errorCode);
        }
        System.out.println("Usage: scan-html [options]");
        System.out.println("--directory, -d <path>   Scan all HTML files in the specified directory");
        System.out.println("--file, -f <file-path>   Scan the specified single HTML file");
        System.out.println("--report-file, -r <report-path>   Save scan results to file");
        System.out.println("--help, -h   Show help information");
    }
}