package com.demo.reqresdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {

    EditText ename, ejob;
    String name, job;
    private String url = "https://reqres.in/api/users";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ename = (EditText) findViewById(R.id.ename);
        ejob = (EditText) findViewById(R.id.ejob);
    }

    public void onSubmit(View v) {

        name = ename.getText().toString();
        job = ejob.getText().toString();
        new JSONAsyncTask().execute();
    }

    class JSONAsyncTask extends AsyncTask<String, String, String> {
        JSONObject response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewUserActivity.this);
            pDialog.setMessage("Loading, please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Map user = new Hashtable();
            user.put("name", name);
            user.put("job", job);

            JsonParser jsonParser = new JsonParser();
            response = jsonParser.makeHttpRequest(url, "POST", user);
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            try {
                int id = response.getInt("id");
                Toast.makeText(NewUserActivity.this, "Submitted succesfully with id:" + id, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
