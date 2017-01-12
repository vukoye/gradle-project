package com.vukoye.jokeviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class JokeViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_viewer);
        TextView jokeText = (TextView) findViewById(R.id.jokeText);
        String jokeString = getIntent().getStringExtra("JOKE");
        if (TextUtils.isEmpty(jokeString)) {
            jokeString = getString(R.string.no_joke);
        }
        jokeText.setText(jokeString);
    }
}
