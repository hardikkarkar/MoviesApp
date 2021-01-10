package com.example.movies.ui.homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import com.example.movies.R;
import com.example.movies.adapter.SourcesPagerAdapter;
import com.example.movies.ui.movies.MoviesFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    TabLayout tabLayout;
    ViewPager viewPager;
    SourcesPagerAdapter sourcesPagerAdapter;
    private View progressBar;
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        presenter = new MainPresenter();
        presenter.initPresenter(this);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        progressBar = findViewById(R.id.progressBar);
        sourcesPagerAdapter = new SourcesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sourcesPagerAdapter);
        setupViewPager();
    }



    /**
     * <p>Setup viewpager after successful response</p>
     * */
    private void setupViewPager() {
        sourcesPagerAdapter.addFragment(new MoviesFragment(getString(R.string.popular_movies)), getString(R.string.popular_movies),0);
        sourcesPagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void response() {
        setupViewPager();
    }
}
