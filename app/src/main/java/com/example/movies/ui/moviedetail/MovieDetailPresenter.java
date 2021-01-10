package com.example.movies.ui.moviedetail;

import android.util.Log;
import android.widget.Toast;

import com.example.movies.MyApplication;
import com.example.movies.model.movie_detail.MovieDetailResponse;
import com.example.movies.model.popular_movies.PopularMoviesResponse;
import com.example.movies.network.GetDataService;
import com.example.movies.network.RetrofitClientInstance;
import com.example.movies.utilities.Constant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    private MovieDetailContract.View view;

    @Override
    public void initPresenter(MovieDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getMoviesDetail(String movieId) {
        view.showProgress(true);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<MovieDetailResponse> call = service.getMoviesDetail(movieId,Constant.API_KEY,Constant.LANGUAGE);
        call.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                view.showProgress(false);
                if(response.code()==200) {
                    if (response.body() != null) {
                        view.response(response.body());
                    }
                }else {
                    try {
                        Log.d("API", "onError: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                view.showProgress(false);
            }
        });
    }

    public void postRating(String movieId, float rating) {
        view.showProgress(true);
        Map<String,Object> map = new HashMap<>();
        map.put("value",rating);
        Log.d("TOKEN" ,MyApplication.getInstance().getGuestSessionId());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.postRating(movieId,Constant.API_KEY
                , MyApplication.getInstance().getGuestSessionId()
                ,map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showProgress(false);
                if(response.code()==201) {
                    if (response.body() != null) {
                        view.ratingSubmitted();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.showProgress(false);
            }
        });
    }
}
