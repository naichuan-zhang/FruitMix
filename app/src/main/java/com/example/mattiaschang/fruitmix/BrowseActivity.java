package com.example.mattiaschang.fruitmix;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class BrowseActivity extends AppCompatActivity {

    public Context mContext;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        mContext = this;

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final FruitAdapter fruitAdapter = new FruitAdapter(this);
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra("fruitName");
        fruitAdapter.setData(fruitName);
        recyclerView.setAdapter(fruitAdapter);

        fruitAdapter.addName();
        if(fruitName == null) {
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
            fruitAdapter.addName();
        }


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Log.i("dayang", "execute when open menu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.i("dayang", "OnCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.browse_action_setting:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
