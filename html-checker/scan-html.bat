@echo off
set CLASSPATH=D:\html-checker\lib\jsoup-1.13.1.jar;D:\html-checker\build
java -cp "%CLASSPATH%" main.java.com.example.checker.Main %*