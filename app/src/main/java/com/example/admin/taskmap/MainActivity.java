package com.example.admin.taskmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ListView listView;
    Button button;
    ArrayList keylist;
    ArrayList maplist;
    ArrayList<String> tasklist;
    ArrayAdapter<String> taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button);
        button.setText("Refresh");
        keylist = new ArrayList<String>();
        maplist = new ArrayList<String>();

        tasklist = new ArrayList<String>();
        taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, maplist);
        listView.setAdapter(taskAdapter);

        try {
            dbHelper = new DBHelper(getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }


        tasklist.clear();
        taskAdapter.clear();

        try {
            Cursor cursor = dbHelper.getAllData();
            cursor.moveToFirst();

            if (cursor.isAfterLast()){
                Toast.makeText(getApplicationContext(),"No records found",Toast.LENGTH_SHORT).show();
            }
            else {
                while (!cursor.isAfterLast()) {
                    keylist.add(cursor.getString(1));
                    maplist.add(cursor.getString(0));
                    tasklist.add(cursor.getString(0) + "     " + cursor.getString(1));
                    taskAdapter.notifyDataSetChanged();
                    cursor.moveToNext();
                }
            }
            cursor.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }


        //--------------------------------------------------






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //---------------------------------------------
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try
                {

                            Intent newActivity = new Intent(getApplicationContext(), ToDoClick.class);
                            newActivity.putExtra("Details",keylist.get(position).toString());
                            newActivity.putExtra("Search",maplist.get(position).toString());
                            startActivity(newActivity);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        //------------------------------------------------

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Boolean b = dbHelper.onRemove(maplist.get(position).toString());
                                maplist.remove(position);
                                tasklist.remove(position);
                                keylist.remove(position);
                                taskAdapter.notifyDataSetChanged();

                                if(b)
                                {
                                    Toast.makeText(getApplicationContext(),"Data deleted",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();

                return false;
            }
        });








        //------------------------------------------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(), InsertData.class);
                startActivity(intent);

            }
        });
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
            Intent intent = new Intent(getApplicationContext(),AboutUs.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
/*
Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
startActivity(intent);
 */