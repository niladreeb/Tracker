package com.demo.tracker.api;

import android.util.Base64;

import com.demo.tracker.common.Const;
import com.demo.tracker.model.JourneyEntity;
import com.demo.tracker.model.LocationEntity;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public class ApiManager {

    private interface JourneyApiService {

        @GET(Const.API_JOURNEYS)
        List<JourneyEntity> getItem();
    }
    private interface LocationApiService {

        @POST(Const.API_LOCATION)
        void postItem(@Body LocationEntity mLocationEntity, Callback<PostResponse> cb);
    }


    // Gson
    private static Gson journeyJson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    // Retrofit Adapter
    private static RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(Const.API_ENDPOINT)
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // concatenate username and password with colon for authentication
                    String credentials = Const.USERNAME + ":" + Const.PASSWORD;
                    // create Base64 encodet string
                    final String basic =
                            "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Authorization", basic);
                    request.addHeader("Accept", "application/json");
                }
            })
            .setConverter(new GsonConverter(journeyJson))
            .build();

    public static List<JourneyEntity> getJourneyItem() {

        JourneyApiService journeyApiService = restAdapter.create(JourneyApiService.class);
        return journeyApiService.getItem(); // Issue an API
    }
    public static void postLocationItem(LocationEntity inputLocationEntity) {
        LocationApiService locationApiService = restAdapter.create(LocationApiService.class);
        String json = journeyJson.toJson(inputLocationEntity);

        locationApiService.postItem(inputLocationEntity, new Callback<PostResponse>() {

            @Override
            public void success(PostResponse postResponse, Response response) {
                System.out.println("Post Success::::::"+response.getStatus());

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Post Failure::::::"+error);
            }
        });


    }

}