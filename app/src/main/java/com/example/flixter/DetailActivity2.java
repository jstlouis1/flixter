package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityDetail2Binding;
import com.example.flixter.databinding.ForbackdropplayoutBinding;
import com.example.flixter.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity2 extends YouTubeBaseActivity {
    private static String YOUTUBE_API_KEY= "AIzaSyCDPptZRbMIb25K0oZyGN4sjXzCMGbhPqU";
    public static final String VIDEOS_URL="https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    float vote;


    YouTubePlayerView youTubePlayerView;
    ActivityDetail2Binding detail2Binding;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detail2Binding = DataBindingUtil.setContentView(this,R.layout.activity_detail2);


        youTubePlayerView=findViewById(R.id.player);

       String title= getIntent().getStringExtra("title");
        Movie movie= Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        vote=(float)movie.getRating();
        detail2Binding.setMovie(movie);
        detail2Binding.executePendingBindings();

        AsyncHttpClient client=new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                   JSONArray results= json.jsonObject.getJSONArray("results");
                   if(results.length()==0){
                       return;
                   }
                   final String youtubeKey=  results.getJSONObject(0).getString("key");
                   Log.d("DetailActivity",youtubeKey);
                   initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse json",e);


                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });



    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onInitializationSuccess");
                if(vote>5){
                    youTubePlayer.loadVideo(youtubeKey);

                }else{
                    youTubePlayer.cueVideo(youtubeKey);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });
    }
}