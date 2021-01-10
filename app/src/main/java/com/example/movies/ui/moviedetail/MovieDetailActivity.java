package com.example.movies.ui.moviedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.movies.R;
import com.example.movies.model.movie_detail.MovieDetailResponse;
import com.example.movies.utilities.Constant;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

    View progressBar;
    MovieDetailPresenter presenter;

    CollapsingToolbarLayout collapsingTooBar;
    ImageView imageView;
    TextView tvTagline,tvDesc,tvRating;
    AppCompatRatingBar rating;
    Button btnSubmit;
    String movieId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieId  = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        collapsingTooBar = findViewById(R.id.collapsingTooBar);
        imageView = findViewById(R.id.imageView);
        tvTagline = findViewById(R.id.tvTagline);
        tvDesc = findViewById(R.id.tvDesc);
        rating = findViewById(R.id.rating);
        tvRating = findViewById(R.id.tvRating);
        btnSubmit = findViewById(R.id.btnSubmit);
        presenter = new MovieDetailPresenter();
        presenter.initPresenter(this);
        progressBar = findViewById(R.id.progressBar);
        presenter.getMoviesDetail(movieId);
    }


    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(MovieDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void response(MovieDetailResponse response) {
        collapsingTooBar.setTitle(response.getTitle());
        tvTagline.setText(response.getTagline());
        tvDesc.setText(response.getOverview());
        Glide.with(this)
                .load(Constant.IMAGE_BASE+response.getPosterPath())
                .thumbnail(0.3f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.default_image)
                .into(imageView);
        final float rate = (float) response.getVoteAverage();
        tvRating.setText("Rating: "+rate+"");

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.postRating(movieId,rating.getRating());
            }
        });
    }

    @Override
    public void ratingSubmitted() {
        Toast.makeText(this,"Your Rating Submitted.",Toast.LENGTH_LONG).show();
    }
}
