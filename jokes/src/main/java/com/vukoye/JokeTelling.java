package com.vukoye;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import jdk.nashorn.internal.scripts.JO;

public class JokeTelling {

    ArrayList<Joke> jokes = new ArrayList<>();

    public Joke getRandomJoke(){
        Random rand = new Random();
        int randomNum = rand.nextInt(jokes.size());
        return jokes.get(randomNum);
    }


    public void addJoke(Joke joke) {
        jokes.add(joke);
    }

    public void addJoke(String jokeText) {
        Joke joke = new Joke(jokeText);
        jokes.add(joke);
    }

    public JokeTelling() {
        generateSampleJokes();
    }

    private void generateSampleJokes() {
        addJoke("I dreamt I was forced to eat a giant marshmallow. When I woke up, my pillow was gone");
        addJoke("Doctor: \'I\'m sorry but you suffer from a terminal illness and have only 10 to live.\'\n Patient: \'What do you mean, 10? 10 what? Months? Weeks?!"
                + "\nDoctor: \'Nine.\'");
        addJoke("My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away");

    }
}
