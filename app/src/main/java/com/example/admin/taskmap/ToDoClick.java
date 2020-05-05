package com.example.admin.taskmap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ToDoClick extends AppCompatActivity  {

    DBHelper dbHelper;
    Button button,update;
    EditText editText,setdetailfor;
    String data,find,newdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_click);



        update = (Button)findViewById(R.id.buttonupdate);
        button = (Button) findViewById(R.id.buttonmap);
        editText = (EditText)findViewById(R.id.editTextmap);
        setdetailfor = (EditText)findViewById(R.id.editTextdetailfor);



        try {
            dbHelper = new DBHelper(getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        data = getIntent().getStringExtra("Details");
        find = getIntent().getStringExtra("Search");


        editText.setText(data);

        setdetailfor.setText(find);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?&daddr="+find));
                startActivity(intent);
            }
        });


        //-----------------------------------
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b;

                newdata = editText.getText().toString().trim();
                b = dbHelper.onUpdate(find,newdata);

                if (b)
                {
                    Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Not updated!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
