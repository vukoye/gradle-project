package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.vukoye.Joke;
import com.vukoye.jokes.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Nemanja on 1/14/2017.
 */

public class GetJokeAsyncTask extends AsyncTask<Context, Void, String> {
    MyApi mApiService = null;
    Context mContext;
    JokeReceivedListener mListener;

    public interface JokeReceivedListener {
        void onJokeReady(Joke joke);
    }

    public void setListener(JokeReceivedListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(final Context... params) {
        if (mApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://gradle-test-project.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(final AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            mApiService = builder.build();
        }
        mContext = params[0];
        try {
            return mApiService.getJoke().execute().getText();
        } catch (IOException e) {
            return  "";
            //return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String jokeText) {
        if (mListener != null) {
            Joke joke = null;
            if (!TextUtils.isEmpty(jokeText)) {
                joke = new Joke(jokeText);
            }
            mListener.onJokeReady(joke);
        }
    }
}

