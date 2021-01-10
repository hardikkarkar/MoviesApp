package com.example.movies.network;

import com.example.movies.model.GuestSessionResponse;
import com.example.movies.model.movie_detail.MovieDetailResponse;
import com.example.movies.model.popular_movies.PopularMoviesResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMovies(@Query("api_key") String apiKey,
                                                 @Query("language") String language,
                                                 @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMoviesDetail(@Path("movie_id") String movie_id,
                                              @Query("api_key") String apiKey,
                                              @Query("language") String language);

    @GET("authentication/guest_session/new")
    Call<GuestSessionResponse> getGuestSession(@Query("api_key") String apiKey);

    @POST("movie/{movie_id}/rating")
    Call<ResponseBody> postRating(@Path("movie_id") String movie_id,
                                  @Query("api_key") String apiKey,
                                  @Query("guest_session_id") String guestSessionId,
                                  @Body Map<String,Object> map);

}