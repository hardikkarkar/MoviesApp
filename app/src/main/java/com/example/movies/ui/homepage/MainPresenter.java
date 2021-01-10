package com.example.movies.ui.homepage;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void initPresenter(MainContract.View view) {
        this.view = view;
    }

}
