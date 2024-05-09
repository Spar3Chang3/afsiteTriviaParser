package com.sparechange;

public class Main {
    public static void main(String[] args) {
        TriviaParser parser = new TriviaParser("./src/main/resources/trivia.txt", "./src/main/resources/trivia.json");

        parser.convert();
    }
}