package com.example.cout;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class WebsiteActivity extends AppCompatActivity {

    ListView webList;
    String wList[] = {"https://www.tutorialspoint.com/index.htm","https://www.javatpoint.com/","https://www.geeksforgeeks.org/","https://www.w3schools.com/","https://www.learncpp.com/","https://www.sololearn.com/","https://stackoverflow.com/","https://www.youtube.com/watch?v=vLnPwxZdW4Y"};
    ArrayList<String>list = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        webList = (ListView)findViewById(R.id.webList);

        //pushing all websites into arraylist
        list.add("TutorialsPoint");
        list.add("javatpoint");
        list.add("GeeksforGeeks");
        list.add("W3 Schools");
        list.add("Learn Cpp");
        list.add("Solo Learn");
        list.add("StackOverflow");
        list.add("Free Code Camp");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        webList.setAdapter(arrayAdapter);

        webList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri uri = Uri.parse(wList[i]);
                System.out.println("uri: "+uri);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                System.out.println("postion of clicked: "+i);
            }
        });

    }
}