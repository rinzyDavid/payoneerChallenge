package com.payoneerchallange.repository;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * A general purpose exception class that handles api exceptions and returns
 * a human readable string through getError() method
 */
public class ApiException {

    private final Throwable throwable;

    public ApiException(Throwable throwable){
        this.throwable = throwable;
    }

    /**
     * Handles the throwable exception object
     * @return human readable string.
     * If the Throwable is null, it returns empty string.
     * If it is an instance of HttpException, translate it using Http Codes,
     * else check whether it is a socket timeout or no network connection error and return the
     * appropriate string
     */
    private String handleError(){

        if(throwable == null)
            return "";


        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException)throwable;

            return getErrorStatusMessage(httpException);
        } else if (throwable instanceof SocketTimeoutException) {
            return "Network time out, please try again";
        } else if (throwable instanceof IOException) {

            return "No network connection, please check your network";
        }
        else{
          throwable.printStackTrace();
            return "System error occurred: "+throwable.getLocalizedMessage();
        }

    }

    /**
     * Translates HtpException based on Http Code
     * @param exception - HttpException object
     * @return Readable string
     */
    private String getErrorStatusMessage(HttpException exception){

        int code = exception.code();
        System.out.println(code);
        switch (code){
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                return "Authentication required,please login ";
            case HttpURLConnection.HTTP_NOT_FOUND:
                return "Resource not found, try again";
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return "Server error, we will fix it, check again later";

            case HttpURLConnection.HTTP_UNAVAILABLE:
                return "Server not available at the moment, check back later";

            default:
                return "Unknown error: We will look into it";
        }

    }

    public String getError(){

        return handleError();
    }
}
