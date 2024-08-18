package main.java.com.example.checker;

public interface ErrorCode {
    int COMMAND_LINE_ERROR = 2001;
    int FILE_NOT_FOUND = 2002;
    int FILE_FORMAT_ERROR = 2003;
    int PARSING_ERROR = 2004;
    int FILE_ACCESS_ERROR = 2005;
    int PLUGIN_LOAD_ERROR = 2006;
}