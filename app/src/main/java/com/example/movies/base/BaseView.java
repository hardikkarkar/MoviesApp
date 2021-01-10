package com.example.movies.base;

public interface BaseView {
    /**
     * This method used to show progress in view
     * @param isShow
     */
     void showProgress(boolean isShow);

    /**
     * This method used to show message in view
     * @param msg
     */
     void showMessage(String msg);
}
