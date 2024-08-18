package main.java.com.example.checker;

import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public void generateReport(String path, String content) {
        System.out.println("尝试创建报告文件: " + path);

        FileWriter writer = null;

        try {
            writer = new FileWriter(path);
            writer.write(content);
            System.out.println("报告已成功保存到 " + path);
        } catch (IOException e) {
            handleError(ErrorCode.FILE_ACCESS_ERROR, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    handleError(ErrorCode.FILE_ACCESS_ERROR, e);
                }
            }
        }
    }

    private void handleError(int errorCode, Exception e) {
        System.err.println("Error Code: " + errorCode);
        System.err.println("Error Message: " + e.getMessage());
        e.printStackTrace();
    }
}