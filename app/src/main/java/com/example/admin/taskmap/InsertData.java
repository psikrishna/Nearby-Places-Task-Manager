package com.example.admin.taskmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;

public class InsertData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button save;
    EditText title,details;
    String t,d,datafromeditor;
    Spinner spinner;
    DBHelper dbHelper;
    Boolean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        save = (Button)findViewById(R.id.buttonsave);
        title = (EditText)findViewById(R.id.editTextTitle);
        details = (EditText)findViewById(R.id.editTextDetails);
        spinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this,R.array.title_names,android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        try {
            dbHelper = new DBHelper(getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datafromeditor = title.getText().toString().trim();

                if(!datafromeditor.isEmpty())
                {
                    d = details.getText().toString().trim();
                    try {
                        if(!t.isEmpty() && !d.isEmpty()) {
                            b = dbHelper.onInsert(datafromeditor,d);
                            if (b) {
                                Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Insert Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {

                            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else
                {
                    d = details.getText().toString().trim();
                    try {
                        if(!t.isEmpty() && !d.isEmpty()) {
                            b = dbHelper.onInsert(t,d);
                            if (b) {
                                Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                          /*  etId.setText("");
                            etName.setText("");
                            etSalary.setText("");*/
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Insert Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {

                            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                        }
                        finish();

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        t = textView.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
