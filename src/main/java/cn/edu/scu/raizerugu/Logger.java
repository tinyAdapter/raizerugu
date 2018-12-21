package cn.edu.scu.raizerugu;

import java.util.Date;

public class Logger {
	public static void fatal(String prompt) {
		System.err.printf("FATAL [%s] %s\n\n", new Date(), prompt);
		Main.printUsage();
		System.exit(1);
	}
	
	public static void error(String prompt) {
		System.err.printf("ERROR [%s] %s\n", new Date(), prompt);
	}
	
	public static void warn(String prompt) {
		System.out.printf("WARN  [%s] %s\n", new Date(), prompt);
	}
	
	public static void info(String prompt) {
		System.out.printf("INFO  [%s] %s\n", new Date(), prompt);
	}
	
	public static void debug(String prompt) {
		System.out.printf("DEBUG [%s] %s\n", new Date(), prompt);
	}
}
