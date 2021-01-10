package com.example.movies;

import android.app.Application;
import android.util.Log;
import com.example.movies.model.GuestSessionResponse;
import com.example.movies.network.GetDataService;
import com.example.movies.network.RetrofitClientInstance;
import com.example.movies.utilities.Constant;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplication extends Application {

    String guestSessionId;

    static MyApplication single_instance;
    public static MyApplication getInstance()
    {
        return single_instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        single_instance = this;
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<GuestSessionResponse> call = service.getGuestSession(Constant.API_KEY);
        call.enqueue(new Callback<GuestSessionResponse>() {
            @Override
            public void onResponse(Call<GuestSessionResponse> call, Response<GuestSessionResponse> response) {
                if(response.code()==200) {
                    if (response.body() != null) {
                       setGuestSessionId(response.body().getGuestSessionId());
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
            public void onFailure(Call<GuestSessionResponse> call, Throwable t) {
            }
        });
    }

    public String getGuestSessionId() {
        return guestSessionId;
    }
    public void setGuestSessionId(String guestSessionId) {
        this.guestSessionId = guestSessionId;
    }
}
