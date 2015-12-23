package ru.videmon.android.androidauth;

import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


@SuppressLint("NewApi")
public class LoginLayout extends Activity {
    EditText un, pw, sv;
    TextView finalResult;
    Button ok;
    private AsyncTask<String, String, String> asyncTask;
    private String response;
    private static Context context;
    private static final String SERVER_PATH = "https://93.88.130.37:8443/ru.videmon.server/login.do";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginLayout.context = getApplicationContext();
        setContentView(R.layout.activity_main);
        un = (EditText) findViewById(R.id.et_un);
        pw = (EditText) findViewById(R.id.et_pw);
        sv = (EditText) findViewById(R.id.et_sv);
        sv.setText(SERVER_PATH);
        ok = (Button) findViewById(R.id.btn_login);
        finalResult = (TextView) findViewById(R.id.tv_error);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AsyncTaskRunner runner=new AsyncTaskRunner();
                String userName=un.getText().toString();
                String password=pw.getText().toString();
                asyncTask=runner.execute(userName,password);
                try {
                    String asyncResultText=asyncTask.get();
                    response = asyncResultText.trim();
                } catch (InterruptedException e1) {
                    response = e1.getMessage();
                } catch (ExecutionException e1) {
                    response = e1.getMessage();
                } catch (Exception e1) {
                    response = e1.getMessage();
                }
                finalResult.setText(response);
            }
        });
    }

    public static Context getAppContext() {
        return LoginLayout.context;
    }
}