package com.example.siddhesh.sports_attendance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FirstActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText eSportsName;
    EditText eDate;
    EditText edittext;
    EditText edittext1;
    String sportsname;
    String date1;
    String Stime;
    String Etime;
    String[] arr_time = {"8:00", "8:50","9:40","10:30","11:20","12:10","1:00","1:50","2:40","3:30","4:20","5:10","6:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        eSportsName = (EditText)findViewById(R.id.sports_name);
        eDate = (EditText)findViewById(R.id.date);

        edittext = (EditText) findViewById(R.id.date);
        edittext1 = (EditText)findViewById(R.id.time);

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FirstActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Spinner j_spin = (Spinner) findViewById(R.id.start_time);
        j_spin.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arr_time);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        j_spin.setAdapter(aa);

        Spinner j_spin1 = (Spinner) findViewById(R.id.end_time);
        j_spin1.setOnItemSelectedListener(this);
        ArrayAdapter aa1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arr_time);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        j_spin1.setAdapter(aa1);
        j_spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                Etime = arg0.getItemAtPosition(pos).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Stime = adapterView.getItemAtPosition(i).toString();
    }

    public void onNothingSelected(AdapterView<?>arg0){}


    Calendar myCalendar = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };


    public void register(View view){

        sportsname = eSportsName.getText().toString();

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String d=sdf.format(myCalendar.getTime());

        date1 = d;

        Intent in = new Intent(FirstActivity.this, SecondActivity.class);
        in.putExtra("sportsname",sportsname);
        in.putExtra("date",date1);
        in.putExtra("stime",Stime);
        in.putExtra("etime",Etime);
        startActivity(in);

        sportsname="";
        date1="";


    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        edittext.setText(sdf.format(myCalendar.getTime()));
    }


    public void back(View view) {
        Intent in = new Intent(FirstActivity.this, MainActivity.class);
        startActivity(in);
    }
}
