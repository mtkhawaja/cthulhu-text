package com.cthulhutext.api.models;

public record CursedText(String content) {

    @Override
    public String toString() {
        return "CursedText{" +
                "content='" + content + '\'' +
                '}';
    }
}
