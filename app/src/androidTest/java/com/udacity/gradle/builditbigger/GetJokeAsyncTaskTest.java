package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.vukoye.Joke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class GetJokeAsyncTaskTest {
    @Test
    public void execute() throws Exception {
        GetJokeAsyncTask asyncTask = new GetJokeAsyncTask();
        asyncTask.setListener(new GetJokeAsyncTask.JokeReceivedListener() {
            @Override
            public void onJokeReady(Joke joke) {
                assertNotNull(joke);
            }
        });
        asyncTask.execute(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

}