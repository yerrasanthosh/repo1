package com.vroozi.customerui.util;

public class StringUtils {
    /**
     * Filters out any non alpha-numeric characters from the input string.
     * @param input
     * @return
     */
    public static String stringCleanser(String input) {
        if (null==input ||  input.isEmpty()){
            return input;
        }
        return input.replaceAll("[^a-zA-Z0-9]+","");
    }
}
