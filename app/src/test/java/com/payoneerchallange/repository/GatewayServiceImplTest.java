package com.payoneerchallange.repository;

import static com.google.common.truth.Truth.assertThat;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.payoneerchallange.data.ApiResponse;
import com.payoneerchallange.data.Gateway;
import com.payoneerchallange.data.GatewayMap;
import com.payoneerchallange.util.LiveDataTestUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class GatewayServiceImplTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

   private Retrofit retrofit;
    String baseUrl = "https://raw.githubusercontent.com/";
    String badUrl = "https://raw.githubusercontent.co.uk/";
    private MockWebServer mockWebServer;
    private MockResponse response;
    private Gson gson;


    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run,
                                             long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run,true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


   @Before
   public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.url("/");
        response = new MockResponse()
               .addHeader("ContentType","application/json")
               .setResponseCode(200)
               .setBody(getMockResponse("/test.json"));
       mockWebServer.enqueue(response);

        gson = new GsonBuilder()
               .excludeFieldsWithoutExposeAnnotation()
               .create();
   }


   @Test
   public void parser_Should_Return_List() {
       GatewayMap mockResponse = gson.fromJson(response.getBody().readUtf8(),GatewayMap.class);
       assertThat(mockResponse.getGateways()).isNotEmpty();
   }



   @Test
    public void listGateway_Returns_Actual_Result() throws Exception {

       GsonBuilder gsonBuilder = new GsonBuilder()
               .excludeFieldsWithoutExposeAnnotation();

       OkHttpClient.Builder client = new OkHttpClient.Builder();

       retrofit = new Retrofit.Builder()
               .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .baseUrl(baseUrl)
               .client(client.build())
               .build();

       PaymentApi  paymentApi = retrofit.create(PaymentApi.class);
       GatewayServiceImpl gatewayService = new GatewayServiceImpl(paymentApi);


       ApiResponse apiResponse =  LiveDataTestUtil
               .getOrAwaitValue(gatewayService.listPaymentGateway());



       GatewayMap mockResponse = gson.fromJson(response.getBody().readUtf8(),GatewayMap.class);
       assertThat(mockResponse.getGateways()).isEqualTo(apiResponse.getDataMap().getGateways());

   }

   @Test
   public void listGateway_Should_Handle_Exception_Returns_String()throws Exception{

       GsonBuilder gsonBuilder = new GsonBuilder()
               .excludeFieldsWithoutExposeAnnotation();

       OkHttpClient.Builder client = new OkHttpClient.Builder();

       retrofit = new Retrofit.Builder()
               .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .baseUrl(badUrl)
               .client(client.build())
               .build();

       PaymentApi  paymentApi = retrofit.create(PaymentApi.class);
       GatewayServiceImpl gatewayService = new GatewayServiceImpl(paymentApi);
       ApiResponse apiResponse =  LiveDataTestUtil
               .getOrAwaitValue(gatewayService.listPaymentGateway());
       //System.out.println(apiResponse.getError());

       assertThat(apiResponse.getError()).isNotEmpty();
   }




   @After
   public void cleanUP()throws Exception{
        mockWebServer.shutdown();
   }


   private String getMockResponse(String name) throws IOException {
       File resultJson = new File(getClass().getResource(name).getPath());
       FileInputStream fin = new FileInputStream(resultJson);
       ByteArrayOutputStream result = new ByteArrayOutputStream();
       byte[] buffer = new byte[1024];
       for (int length; (length = fin.read(buffer)) != -1; ) {
           result.write(buffer, 0, length);
       }
       return result.toString("UTF-8");


   }



}