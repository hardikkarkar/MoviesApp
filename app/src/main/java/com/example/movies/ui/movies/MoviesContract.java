package com.example.movies.ui.movies;

import com.example.movies.base.BasePresenter;
import com.example.movies.base.BaseView;
import com.example.movies.model.popular_movies.Result;

import java.util.List;

public interface MoviesContract {

    interface View extends BaseView {

        /**
         * <p>When successful response fetch</p>
         * @param body
         * */
        void response(List<Result> body);
    }

    interface Presenter extends BasePresenter {

        /**
         * <p>This method used to initialize presenter</p>
         * @param view
         */
        void initPresenter(MoviesContract.View view);

        /**
         * <p>Used to get list of Article by albumId</p>
         * @param page*/
        void getPopularMovies(int page);
    }
}
