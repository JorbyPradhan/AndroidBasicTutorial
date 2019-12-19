package com.example.jb.demoreg.nrc;

public enum Language {

    MYANMAR("\u104a\u200b", "\u104b\u200b"),
    ENGLISH(",\u0020", ".");

    private String comma;
    private String period;

    Language(String comma, String period) {
        this.comma = comma;
        this.period = period;
    }

    public String getComma() {
        return this.comma;
    }

    public String getPeriod() {
        return this.period;
    }
}
