package the.pixel.factory.unicode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by dhruvesh on 13/2/18.
 */

public class MovieDescription extends AppCompatActivity {
    TextView movieTitle, overview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        movieTitle = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        Intent intent= getIntent();
        String title = intent.getStringExtra("movieTitle");
        String description = intent.getStringExtra("description");
        movieTitle.setText(title);
        overview.setText(description);
    }
}
