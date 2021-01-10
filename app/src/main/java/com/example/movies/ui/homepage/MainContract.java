package com.example.movies.ui.homepage;

import com.example.movies.base.BasePresenter;
import com.example.movies.base.BaseView;

public interface MainContract {

    interface View extends BaseView {

        /**
         * <p>Success response of news sources</p>
         * */
        void response();
    }

    interface Presenter extends BasePresenter{
        /**
         * <p>This method used to initialize presenter</p>
         * @param view
         */
        void initPresenter(MainContract.View view);
    }
}
