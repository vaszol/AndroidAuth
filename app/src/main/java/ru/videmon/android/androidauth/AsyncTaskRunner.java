package ru.videmon.android.androidauth;

/**
 * Created by vaszol on 23.12.2015.
 */

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

/**
 * @author Prabu
 *
 */
public class AsyncTaskRunner extends  AsyncTask<String,String,String>{

    private String resp;
    @Override
    protected String doInBackground(String... params) {
        int count = params.length;
        if(count==2){
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("username",params[0]));
            postParameters.add(new BasicNameValuePair("password",params[1]));
            String response = null;
            try {
                response = SimpleHttpClient.executeHttpPost("https://93.88.130.37:8443/ru.videmon.server/login.do", postParameters);
                String res = response.toString();
                resp = res.replaceAll("\\s+", "");
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
        }else{
            resp="Invalid number of arguments-"+count;
        }
        return resp;
    }
}