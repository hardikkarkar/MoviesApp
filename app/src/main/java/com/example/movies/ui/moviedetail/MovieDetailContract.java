package com.example.movies.ui.moviedetail;

import com.example.movies.base.BasePresenter;
import com.example.movies.base.BaseView;
import com.example.movies.model.movie_detail.MovieDetailResponse;

public interface MovieDetailContract {

    interface View extends BaseView {

        /**
         * <p>Success response of news sources</p>
         *
         * @param response*/
        void response(MovieDetailResponse response);

        void ratingSubmitted();
    }

    interface Presenter extends BasePresenter{
        /**
         * <p>This method used to initialize presenter</p>
         * @param view
         */
        void initPresenter(View view);

        void getMoviesDetail(String movieId);
    }
}
