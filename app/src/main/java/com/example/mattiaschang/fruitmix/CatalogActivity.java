package com.example.mattiaschang.fruitmix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CatalogActivity extends AppCompatActivity {

    private Button mOrderButton;
    private Button mBrowseButton;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mOrderButton = findViewById(R.id.order_button);
        mBrowseButton = findViewById(R.id.browse_button);
        mSearchButton = findViewById(R.id.search_button1);
    }

    public void pressedSearch(View view) {
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }


    public void pressedOrder(View view) {
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    public void pressedBrowse(View view) {
        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, BrowseActivity.class);
                startActivity(intent);
            }
        });
    }
}
