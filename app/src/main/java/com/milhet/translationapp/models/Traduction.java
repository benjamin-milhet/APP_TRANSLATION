package com.milhet.translationapp.models;

public class Traduction {
private final String lang_source;
private final String lang_target;
private final String text_source;
private final String text_target;

public Traduction(String lang_source, String lang_target, String text_source, String text_target) {
    this.lang_source = lang_source;
    this.lang_target = lang_target;
    this.text_source = text_source;
    this.text_target = text_target;
}

    public String getLang_source() {
        return lang_source;
    }

    public String getLang_target() {
        return lang_target;
    }

    public String getText_source() {
        return text_source;
    }

    public String getText_target() {
        return text_target;
    }

}
