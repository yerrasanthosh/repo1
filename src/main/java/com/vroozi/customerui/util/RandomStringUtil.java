package com.vroozi.customerui.util;

import java.util.Random;

public class RandomStringUtil {
	private final static String validChars= "0123456789abcdefghijklmnopqrstuvwxyz";

	public static String createToken() {
		String token = generateRandomToken();
		while (!isValidToken(token)) {
			token = generateRandomToken();
		}
		return token;
	}

	public static String generateRandomToken() {
		StringBuilder randomPassword = new StringBuilder();
		Random randomGenerator = new Random();
		try {
			int index = 0;
			int lengthOfChars = validChars.length();
			while (randomPassword.length() < 16) {
				index = randomGenerator.nextInt(lengthOfChars - 1);
				randomPassword.append(validChars.charAt(index));
			}
		} catch (Exception e) {
		}
		return randomPassword.toString();
	}

	private static boolean isValidToken(String token) {

		boolean hasNumber = false;
		boolean hasLowerCase = false;
		char currentChar;
		for (int index = 0; index < token.length(); index++) {
			currentChar = token.charAt(index);
			if (currentChar >= 'a' && currentChar <= 'z') {
				hasLowerCase = true;
			}
			if (currentChar >= '0' && currentChar <= '9') {
				hasNumber = true;
			}
			if (hasLowerCase && hasNumber) {
				return true;
			}
		}
		return false;
	}
	
}
