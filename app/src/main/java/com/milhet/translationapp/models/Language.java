package com.milhet.translationapp.models;

import androidx.annotation.NonNull;

/**
 * Classe représentant une langue avec son code et son nom
 * @author Milhet et Ghys
 */
public class Language {

        private final String language; // code de la langue
        private final String name; // nom de la langue

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

