package com.fedbacknow.fedbacknow.util;

public class TextPreprocessor {

    public static String normalize(String text) {

        if (text == null || text.isBlank()) {
            return "";
        }

        text = text.toLowerCase();

        // remove URLs
        text = text.replaceAll("http\\S+|www\\S+", "");

        // remove @mentions
        text = text.replaceAll("@\\w+", "");

        // hashtags viram palavras
        text = text.replace("#", "");

        // emojis mais comuns (ajuste conforme uso real)
        text = text
                .replace("😡", " raiva ")
                .replace("😠", " raiva ")
                .replace("🤬", " raiva ")
                .replace("😍", " bom ")
                .replace("❤️", " amor ")
                .replace("🔥", " ruim ");

        // remove caracteres inválidos
        text = text.replaceAll("[^a-zà-ú0-9 ]", " ");

        // remove espaços duplicados
        text = text.replaceAll("\\s+", " ").trim();

        return text;
    }
}
