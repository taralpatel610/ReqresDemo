package com.demo.reqresdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // key names matching json response object
    private static final String KEY_DATA = "data";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_FIRST_NAME = "first_name";
    private static final String KEY_USER_LAST_NAME = "last_name";
    private static final String KEY_USER_AVATAR = "avatar";
    private String url = "https://reqres.in/api/users";
    private ProgressDialog pDialog;
    ArrayList<User> userList;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // user list to store the user object
        userList = new ArrayList<User>();
        // Async task to get the data
        new JSONAsyncTask().execute();
        // list view
        ListView listview = (ListView) findViewById(R.id.list);
        // adapter to bind the userlist data to list view along with avatar images
        adapter = new UserAdapter(getApplicationContext(), R.layout.list_item, userList);
        listview.setAdapter(adapter);

    }

    class JSONAsyncTask extends AsyncTask<String, String, String> {
        JSONObject response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading, please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //requesting url to get data
            JsonParser jsonParser = new JsonParser();
            response = jsonParser.makeHttpRequest(url, "GET", null);

            return null;
        }

        protected void onPostExecute(String result) {
            runOnUiThread(new Runnable() {
                public void run() {

                    try {
                        JSONArray userArray = response.getJSONArray(KEY_DATA);

                        //Populate the user list from response
                        for (int i = 0; i < userArray.length(); i++) {
                            User u = new User();
                            JSONObject userObj = userArray.getJSONObject(i);
                            u.setId(userObj.getInt(KEY_USER_ID));
                            u.setFirstName(userObj.getString(KEY_USER_FIRST_NAME));
                            u.setLastName(userObj.getString(KEY_USER_LAST_NAME));
                            u.setAvatar(userObj.getString(KEY_USER_AVATAR));
                            userList.add(u);
                        }
                        //Create an adapter with the user List and set it to the LstView
                        adapter.notifyDataSetChanged();
                        pDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
