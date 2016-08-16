package com.example.bs148.coordinatoexampledemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void simpleCoorDinatorLayout(View view){
        Intent intent=new Intent(this,SimpleCoordinatorActivity.class);
        startActivity(intent);
    }

    public void googleIoDetailExample(View view){
        Intent intent=new Intent(this,IoExampleActivity.class);
        startActivity(intent);
    }

    public void materialUpProfileExample(View view){
        Intent intent=new Intent(this,MaterialUpConceptActivity.class);
        startActivity(intent);
    }
}
