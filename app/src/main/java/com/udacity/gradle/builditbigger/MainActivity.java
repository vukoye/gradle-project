package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.vukoye.JokeTelling;
import com.vukoye.jokes.backend.myApi.MyApi;
import com.vukoye.jokeviewer.JokeViewerActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void tellJoke(View view) {
        JokeTelling jokeTelling = new JokeTelling();

        Toast.makeText(this, jokeTelling.getRandomJoke().getText(), Toast.LENGTH_SHORT).show();
    }

    public void tellJokeNewActivity(View view) {
        JokeTelling jokeTelling = new JokeTelling();
        startJokeActivity(jokeTelling.getRandomJoke().getText());
    }

    public void tellJokeFromBackend(View view) {
        new JokesBackEndAsyncTask().execute(this);
    }

    class JokesBackEndAsyncTask extends AsyncTask<Context, Void, String> {
        MyApi mApiService = null;
        Context mContext;
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
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(final String joke) {
            startJokeActivity(joke);
        }
    }

    private void startJokeActivity(String joke) {
        Intent intent = new Intent(this, JokeViewerActivity.class);
        intent.putExtra("JOKE", joke);
        startActivity(intent);
    }

}
