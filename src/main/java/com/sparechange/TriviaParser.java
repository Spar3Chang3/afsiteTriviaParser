package com.sparechange;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import com.google.gson.*;

import java.io.IOException;
import java.util.Scanner;


public class TriviaParser {

    private static final String DEFAULT_PATH = "";
    private static final String separator = "!";

    private String jsonPath;
    private String txtPath;
    private Gson gson;
    private JsonArray jArray;
    private FileReader fr;
    private FileWriter fw;
    private Scanner txtReader;

    public void setup() {
        try {
            fr = new FileReader(txtPath);
            jArray = new JsonArray();
            gson = new GsonBuilder().setPrettyPrinting().create();
            txtReader = new Scanner(fr);
        } catch (FileNotFoundException e) {
            System.err.println("Could not locate file with path: " + txtPath);
            fr = null;
        }
    }

    public TriviaParser() {
        this(DEFAULT_PATH, DEFAULT_PATH);
    }

    public TriviaParser(String txtPath, String jsonPath) {
        this.txtPath = txtPath;
        this.jsonPath = jsonPath;

        setup();
    }

    public void setTxtPath(String txtPath) {
        this.txtPath = txtPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public void convert() {
        while (txtReader.hasNext()) {
            String line = txtReader.nextLine();
            addToJsonObject(line.split(separator));
        }

        try {
            fw = new FileWriter(jsonPath);
            gson.toJson(jArray, fw);
            fw.close();
        } catch (IOException e) {
            System.err.println("Could not write to file: " + jsonPath);
        }
    }

    private void addToJsonObject(String[] line) {
        String question = line[0];
        String[] answers = new String[]{line[1], line[2], line[3], line[4]};
        String correctAnswer = line[1];
        String incorrectMessage = line[5];
        String correctMessage = line[6];
        JsonObject toAdd = new JsonObject();
        JsonArray answersToAdd = gson.toJsonTree(answers).getAsJsonArray();
        toAdd.addProperty("question", question);
        toAdd.add("answers", answersToAdd);
        toAdd.addProperty("correctAnswer", correctAnswer);
        toAdd.addProperty("incorrectMessage", incorrectMessage);
        toAdd.addProperty("correctMessage", correctMessage);

        jArray.add(toAdd);
    }

}
