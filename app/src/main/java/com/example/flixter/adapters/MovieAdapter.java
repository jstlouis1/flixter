package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.DetailActivity2;
import com.example.flixter.R;
import com.example.flixter.databinding.ForbackdropplayoutBinding;
import com.example.flixter.databinding.ItemMovieBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static Context context ;
    List<Movie>movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 5) {
            ItemMovieBinding movieView = DataBindingUtil.inflate(inflater,R.layout.item_movie, parent, false);
            return new ViewHolder(movieView);
        } else {
            ForbackdropplayoutBinding movieView = DataBindingUtil.inflate(inflater,R.layout.forbackdropplayout, parent, false);
            return new newview(movieView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("MovieAdapter","OnBindViewHolder" + position);

        Movie movie= movies.get(position);
        if(holder.getItemViewType()==5){
            ViewHolder nic=(ViewHolder) holder;
            nic.bind(movie);
        }else{
           newview jil=(newview)  holder;
            jil.bind(movie);
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemMovieBinding movieBinding;
        public ViewHolder(@NonNull ItemMovieBinding itemView) {

            super(itemView.getRoot());
            movieBinding=itemView;



        }


        public void bind(Movie movie) {
           movieBinding.setMovie(movie);
           movieBinding.executePendingBindings();
            // 1. register Clicklistener on the whole row
            movieBinding.container.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   // 2.navigate to a new activity on tap

                   Intent o =new Intent(context, DetailActivity2.class);
                   o.putExtra("title",movie.getTitle());
                   o.putExtra("movie", Parcels.wrap(movie));


                   context.startActivity(o);
               }
           });

        }
    }
    public class newview extends RecyclerView.ViewHolder{


        ForbackdropplayoutBinding popularBinding;

        public newview(@NonNull ForbackdropplayoutBinding backdropBinding) {

            super(backdropBinding.getRoot());

            popularBinding=backdropBinding;



        }


        public void bind(Movie movie) {
            popularBinding.setMovie(movie);
            popularBinding.executePendingBindings();

            // 1. register Clicklistener on the whole row

            //2.
            popularBinding.container2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2.navigate to a new activity on tap

                    Intent o =new Intent(context, DetailActivity2.class);
                    o.putExtra("title",movie.getTitle());
                    o.putExtra("movie", Parcels.wrap(movie));


                    context.startActivity(o);
                }
            });





        }

    }

    @Override
    public int getItemViewType(int position) {
        int tip;

       if(movies.get(position).getRating() <5){
           return 5;
       } else{
           return 10;

       }
     }
    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrl"})
        public static void loadImage(ImageView view, String url) {
            Glide.with(context).load(url).
                    placeholder(R.drawable.yon)
                    .into(view);
        }
    }
}
