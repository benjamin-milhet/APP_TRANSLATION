package com.milhet.translationapp;

public class Language {

        private String language;
        private String name;

        public Language(String language, String name) {
            this.language = language;
            this.name = name;
        }
        public String getLanguage() {
            return language;
        }
        public String getName() {
            return name;
        }

        public String toString() {
            String res = name;// + " (" + language + ")";
            return  res;}
    }

