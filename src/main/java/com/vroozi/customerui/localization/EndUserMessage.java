package com.vroozi.customerui.localization;

/**
 * User: SURYA MANDADAPU
 * Date: 11/7/12
 * Time: 10:20 PM
 */
public enum EndUserMessage {
    SEARCH_FAILED("search-failed");

    private String localizationKey;

    EndUserMessage(String localizationKey) {
        this.localizationKey = localizationKey;
    }

    @Override
    public String toString() {
        return localizationKey;
    }
}
