package ru.mihena.VKBot.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
@Setter
public class Answer {
    private ArrayList<String> urls = new ArrayList<>();
    private String text = "";

    private ArrayList<String> docs = new ArrayList<>();

    public Answer(String text) {
        this.text = text;
    }

    public boolean hasUrls() {
        return this.urls != null;
    }

    private ArrayList<File> files = new ArrayList<>();

    public boolean hasFiles() {
        return this.files.size() > 0;
    }
}
