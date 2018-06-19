package com.example.siddhesh.sports_attendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SecondActivity extends AppCompatActivity {
    ListView listView;
    EditText eUID;
    EditText eName;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    JSONObject postDataParams;
    BufferedWriter writer;
    OutputStream os;
    HttpURLConnection conn;
    URL url;

    String sportsname;
    String date;
    String stime;
    String etime;
    int i=0;
    int q=0;

     String uid;
     String name;
    //String add1;

    AutoCompleteTextView textView;
    AutoCompleteTextView textView1;

     static final ArrayList<String> uids = new ArrayList<String>();
     ArrayList<String> uidss = new ArrayList<String>();



    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> namess = new ArrayList<String>();

    //String[] COUNTRIES = new String[] {
            //"155001"
    //};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = (ListView) findViewById(R.id.lstNames);
       // eUID = (EditText)findViewById(R.id.uid);
       // eName = (EditText)findViewById(R.id.name);
        listItems = new ArrayList<String>();
        //listItems.add("First Item - added on Activity Create");
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(MshowforItem);


        //Log.e("uid",uids.toString());


        Log.e("uid",uidss.toString());
        namess.add(names.toString());

        // In the onCreate method
         textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, namess);
        textView.setAdapter(adapter2);



        // In the onCreate method
        textView1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_dropdown_item_1line, uidss);
        textView1.setAdapter(adapter1);

        Intent intent = getIntent();

        sportsname = intent.getStringExtra("sportsname");
        date = intent.getStringExtra("date");
        stime = intent.getStringExtra("stime");
        etime = intent.getStringExtra("etime");



    }

    public void edit(View view) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                 uid = textView1.getText().toString(); // eUID.getText().toString();
                 name = textView.getText().toString(); //eName.getText().toString();
                String add1 = ""+uid+" : "+name;

                listItems.set(position,add1);

                adapter.notifyDataSetChanged();


            }
        });
    }


    public void add(View view) {
         uid = textView1.getText().toString(); //eUID.getText().toString();
         name = textView.getText().toString(); //eName.getText().toString();
        String add1 = ""+uid+" : "+name;

        uids.add(uid);
        //Log.e("uid",uids.toString());
        names.add(name);

        uidss.add(uids.toString());

        listItems.add(add1);

        adapter.notifyDataSetChanged();

        textView.setText("");
        textView1.setText("");
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void submit(View view) {
        for(i=0;i<adapter.getCount();i++) {

            new SendRequest().execute();

        }
    }


    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {



                try {
                        url = new URL("https://script.google.com/macros/s/AKfycbwEHl1NydHVUfHdEMiCd7Qq6yhQqjahaIEFB1Lyp77c6ekGHxM/exec");

                        postDataParams = new JSONObject();

                        String id = "1iAuAhnTLoESqHPFhvQDQSzKvIm0Sa_8q32vzyM1QOW4";

                        postDataParams.put("sportsname", sportsname);
                        postDataParams.put("date", date);
                        postDataParams.put("stime", stime);
                        postDataParams.put("etime", etime);
                        postDataParams.put("uids_names", adapter.getItem(q));
                        postDataParams.put("id", id);
                    q=q+1;


                        Log.e("params", postDataParams.toString());

                        conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(15000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);

                        os = conn.getOutputStream();
                        writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();


                        writer.close();
                        os.close();


                        int responseCode = conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            StringBuffer sb = new StringBuffer("");
                            String line = "";

                            while ((line = in.readLine()) != null) {

                                sb.append(line);
                                break;
                            }

                            in.close();
                            return sb.toString();


                        } else {
                            return new String("false : " + responseCode);
                        }

                    }




                catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }



        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }



}
