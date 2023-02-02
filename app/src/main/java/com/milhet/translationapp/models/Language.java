package com.milhet.translationapp.models;

import androidx.annotation.NonNull;

public class Language {

        private final String language;
        private final String name;

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
        @NonNull
        @Override
        public String toString() {
            return this.name;
        }
    }

