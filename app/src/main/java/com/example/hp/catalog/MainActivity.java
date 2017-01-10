package com.example.hp.catalog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton scroll = (ImageButton) findViewById(R.id.imageButton);
        scroll.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intentS = new Intent(view.getContext(),PropertyActivity.class);
                startActivityForResult(intentS,0);
            }
        });

        ImageButton map = (ImageButton) findViewById(R.id.imageButton2);
        map.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intentM = new Intent(view.getContext(),MapsActivity.class);
                startActivityForResult(intentM,0);
            }
        });

    }
}
