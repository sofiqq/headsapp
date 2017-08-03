package com.miras.mirasapp;

import java.io.Serializable;

/**
 * Created by sofiq on 8/2/17.
 */

public class Quiz implements Serializable{

    private String question, words;

    public Quiz() {

    }

    public Quiz (String question, String words) {
        this.question = question;
        this.words = words;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
