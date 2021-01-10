package com.example.movies.ui.movies;

import com.example.movies.model.popular_movies.PopularMoviesResponse;
import com.example.movies.network.GetDataService;
import com.example.movies.network.RetrofitClientInstance;
import com.example.movies.utilities.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesPresenter implements MoviesContract.Presenter{

    private MoviesContract.View view;

    @Override
    public void initPresenter(MoviesContract.View view){
        this.view = view;
    }

    @Override
    public void getPopularMovies(int page) {
        view.showProgress(true);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PopularMoviesResponse> call = service.getPopularMovies(Constant.API_KEY,Constant.LANGUAGE,page);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                view.showProgress(false);
                if(response.code()==200) {
                    if (response.body() != null) {
                        view.response(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                view.showProgress(false);
            }
        });
    }
}
