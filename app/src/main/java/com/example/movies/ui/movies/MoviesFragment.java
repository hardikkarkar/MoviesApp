package com.example.movies.ui.movies;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.adapter.MoviesListAdapter;
import com.example.movies.model.popular_movies.Result;
import com.example.movies.ui.moviedetail.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View,SwipeRefreshLayout.OnRefreshListener, MoviesListAdapter.ClickListner {

    private static final int PAGE_SIZE = 20;
    private String sourceId;
    private List<Result> list = new ArrayList<>();
    private MoviesListAdapter moviesListAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private MoviesPresenter presenter;
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 1;

    public MoviesFragment(String sourceId) {
        // Required empty public constructor
        this.sourceId = sourceId;
        presenter = new MoviesPresenter();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);;
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        presenter.initPresenter(this);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new SpacesItemDecoration(3,10,true));
        moviesListAdapter = new MoviesListAdapter(list,getActivity(),this);
        recyclerView.setAdapter(moviesListAdapter);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        swipeRefresh.setOnRefreshListener(this);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    page++;
                    presenter.getPopularMovies(page);
                }
            }
        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getPopularMovies(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.getPopularMovies(page);
    }

    @Override
    public void response(List<Result> list) {
        isLastPage = list.size()<PAGE_SIZE;
        if(page==1)this.list.clear();
        this.list.addAll(list);
        moviesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean isShow) {
        isLoading = isShow;
        swipeRefresh.setRefreshing(isShow);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnItemClick(int position, Result data) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("id", String.valueOf(data.getId()));
        startActivity(intent);
    }
}
