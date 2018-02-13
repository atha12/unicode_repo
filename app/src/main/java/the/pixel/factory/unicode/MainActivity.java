package the.pixel.factory.unicode;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageView poster;
TextView movieName;

 String movieTitle, movieDesc, moviePosterUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        poster = (ImageView) findViewById(R.id.poster);
        movieName = (TextView) findViewById(R.id.moviename);
        poster.setOnClickListener(this);


        String url = new Uri.Builder()
                .scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", "ed2438a29c839f52d9419c2a4446e937")
                .build()
                .toString();



        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray movies = response.getJSONArray("results");
                            JSONObject popularMovie = movies.getJSONObject(0);
                            movieTitle = popularMovie.getString("title");
                            movieDesc = popularMovie.getString("overview");
                            moviePosterUri = popularMovie.getString("poster_path");
                            moviePosterUri = moviePosterUri.substring(1);
                            Log.e("movieTitle", movieTitle);
                            Log.e("movieDesc", movieDesc);
                            Log.e("moviePoster", moviePosterUri);

                            String posterUrl = new Uri.Builder()
                                    .scheme("https")
                                    .authority("image.tmdb.org")
                                    .appendPath("t")
                                    .appendPath("p")
                                    .appendPath("w342")
                                    .appendPath(moviePosterUri).build().toString();

                            Picasso.with(getApplicationContext())
                                    .load(posterUrl)
                                    .into(poster);

                            movieName.setText(movieTitle);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error - volley", error.getMessage());

                    }


                });

        AppController.getInstance().addToRequestQueue(jsObjRequest);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this,MovieDescription.class);
        i.putExtra("movieTitle", movieTitle);
        i.putExtra("description", movieDesc);
        startActivity(i);
    }





}
