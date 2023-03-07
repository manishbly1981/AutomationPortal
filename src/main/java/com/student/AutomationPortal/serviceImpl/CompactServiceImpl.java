package com.student.AutomationPortal.serviceImpl;

public class CompactServiceImpl {
	public static String generateRandNumberAsStr(int min, int max) {
		return Integer.toString((int)Math.floor(Math.random() * (max-min)+1));
	}
	
	public static String generateConfirmationCode() {
		return generateRandNumberAsStr(1000, 9999);
	}
}
