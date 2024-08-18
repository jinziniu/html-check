package main.java.com.example.checker;

public class Main {
    public static void main(String[] args) {
       try{
        CommandLineInterface cli = new CommandLineInterface();
        cli.parseArguments(args);}
       catch (Exception e ){
           System.err.println("error");
           e.printStackTrace();
       }
     }
}

