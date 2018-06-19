package com.example.siddhesh.sports_attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SSecondActivity extends AppCompatActivity {
     ListView listView;
     ArrayList<String> list2;
     ArrayAdapter<String> list1;


    String sportsname;
    String date;
    String stime;
    String etime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssecond);

        //list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        list2 = new ArrayList<String>();
        /**
         * Binding that List to Adapter
         */
        //adapter = new MyArrayAdapter(this, list);
        list1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list2);

        /**
         * Getting List and Setting List Adapter
         */

        listView.setAdapter(list1);

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
             Toast.makeText(getApplicationContext(),"internet connection is not available",Toast.LENGTH_SHORT).show();
        }


        Intent intent = getIntent();

        sportsname = intent.getStringExtra("sportsname");
        date = intent.getStringExtra("date");
        stime = intent.getStringExtra("stime");
        etime = intent.getStringExtra("etime");


        //new GetDataTask().execute();
    }


    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
        }


        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            Log.d("jsobjectprint", String.valueOf(jsonObject));

            try {

                /**
                 * Check Whether Its NULL???
                 */
                //if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {


                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray("records");
                        //Log.d("sfsdgsdg","hello world");

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                       // if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString("uid_names");
                                String sname = innerObject.getString("sports_name");
                                String d = innerObject.getString("date");
                                String st = innerObject.getString("start_time");

                                if(sname.equals(sportsname)) {

                                    if(d.equals(date)) {
                                        if(st.equals(stime)) {

                                            //Log.d("sportsname", sname);
                                            list2.add(name);

                                        }

                                    }

                                }

                            }
                        }
                    //}
                //} else {

                //}
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "+++++++++" + je.getLocalizedMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //dialog.dismiss();

            list1.notifyDataSetChanged();

            /**
             * Checking if List size if more than zero then
             * Update ListView
             */

        }
    }
}
