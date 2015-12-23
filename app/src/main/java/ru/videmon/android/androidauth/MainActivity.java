package ru.videmon.android.androidauth;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
    EditText un, pw, server_path;
    TextView error;
    Button ok;
    private String resp;
    private String errorMsg;

    private static final String SERVER_PATH = "http://93.88.130.37:8080/ru.videmon.server/login.do";
    private String path;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un = (EditText) findViewById(R.id.et_un);
        pw = (EditText) findViewById(R.id.et_pw);
        ok = (Button) findViewById(R.id.btn_login);
        error = (TextView) findViewById(R.id.tv_error);
        server_path = (EditText) findViewById(R.id.et_sv);
        server_path.setText(SERVER_PATH);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /** According with the new StrictGuard policy,  running long tasks on the Main UI thread is not possible
                 So creating new thread to create and execute http operations */
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                        postParameters.add(new BasicNameValuePair("username", un.getText().toString()));
                        postParameters.add(new BasicNameValuePair("password", pw.getText().toString()));

                        if (server_path.getText().toString() == null) {
                            path = SERVER_PATH;
                            server_path.setText(SERVER_PATH);
                        } else {
                            path = server_path.getText().toString();
                        }
                        String response = null;
                        try {
                            response = SimpleHttpClient.executeHttpPost(path, postParameters);
                            String res = response.toString();
                            resp = res.replaceAll("\\s+", "");

                        } catch (Exception e) {
                            e.printStackTrace();
                            errorMsg = e.getMessage();
                        }
                    }

                }).start();
                try {
                    /** wait a second to get response from server */
                    Thread.sleep(1000);
                    /** Inside the new thread we cannot update the main thread
                     So updating the main thread outside the new thread */

                    error.setText(resp);

                    if (null != errorMsg && !errorMsg.isEmpty()) {
                        error.setText(errorMsg);
                    }
                } catch (Exception e) {
                    error.setText(e.getMessage());
                }
            }
        });
    }
}