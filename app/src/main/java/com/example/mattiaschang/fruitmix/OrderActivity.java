package com.example.mattiaschang.fruitmix;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class OrderActivity extends AppCompatActivity {


    private String name;
    private int quantity;

    private TextView mNameTextView;
    private TextView mQuantityTextView;
    private TextView mDateTextView;

    private Item mCurrentItem;
    private Item mClearedItem;

    private List<Item> mItems;

    private Button mBackButton;
    //private RadioGroup mRadioGroup;
    //private RadioButton b1;
    //private RadioButton b2;
    //private RadioButton b3;
    private static String rbFruitName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameTextView = findViewById(R.id.name_text_view);
        mQuantityTextView = findViewById(R.id.quantity_text_view);
        mDateTextView = findViewById(R.id.delivery_date_text_view);
        mBackButton = findViewById(R.id.back_button);

        mItems = new ArrayList<Item>();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditItem();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Log.i("dayang", "execute when open menu");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.i("dayang", "OnCreateOptionMenu");
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_setting:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;

            case R.id.action_reset:
                mClearedItem = mCurrentItem;
                mCurrentItem = Item.getEmptyItem();
                updateView();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Item cleared", Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentItem = mClearedItem;
                        mClearedItem = null;
                        updateView();
                        Snackbar.make(findViewById(R.id.coordinator_layout), "Restored", Snackbar.LENGTH_LONG);
                    }
                });
                snackbar.show();
                return true;

            case R.id.action_search:
                showSearchDialog();
                return true;

            case R.id.action_clear_all:
                showClearAllDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addEditItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_new_item);

        View view = getLayoutInflater().inflate(R.layout.item_dialog, null, false);
        builder.setView(view);

        //final EditText nameEditText = view.findViewById(R.id.name_edit_text);
        final RadioGroup radGroup = view.findViewById(R.id.radio_group);
        final RadioButton b1 = view.findViewById(R.id.apple_radio_button);
        final RadioButton b2 = view.findViewById(R.id.banana_radio_button);
        final RadioButton b3 = view.findViewById(R.id.hamimelon_radio_button);
        final EditText quantityEditText = view.findViewById(R.id.quantity_edit_text);
        final CalendarView deliveryDateView = view.findViewById(R.id.calendar_view);
        final GregorianCalendar calendar = new GregorianCalendar();

        //String rbFruitName = "";

        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.apple_radio_button:
                        rbFruitName = "apple";
                        break;
                    case R.id.banana_radio_button:
                        rbFruitName = "banana";
                        break;
                    case R.id.hamimelon_radio_button:
                        rbFruitName = "hamimelon";
                        break;
                }
            }
        });

        deliveryDateView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = rbFruitName;
                quantity = Integer.parseInt(quantityEditText.getText().toString());
                mCurrentItem = new Item(name, quantity, calendar);
                mItems.add(mCurrentItem);
                updateView();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void updateView() {
        mNameTextView.setText(getString(R.string.fruit_name_format, mCurrentItem.getName()));
        mQuantityTextView.setText(getString(R.string.quantity_format, mCurrentItem.getQuantity()));
        mDateTextView.setText(getString(R.string.delivery_date_format, mCurrentItem.getDeliveryDateString()));
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_an_item);
        builder.setItems(getNames(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentItem = mItems.get(which);
                updateView();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void showClearAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentItem = Item.getEmptyItem();
                mItems.clear();
                updateView();
            }
        });
        builder.create().show();
    }

    private String[] getNames() {
        String[] names = new String[mItems.size()];
        for (int i = 0; i < mItems.size(); i++) {
            names[i] = mItems.get(i).getName();
        }
        return names;
    }


    public void pressedBack(View view) {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, CatalogActivity.class);
                startActivity(intent);
            }
        });
    }
}
