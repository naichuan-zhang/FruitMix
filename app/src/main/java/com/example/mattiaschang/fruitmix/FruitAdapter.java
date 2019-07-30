package com.example.mattiaschang.fruitmix;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> implements NumberPicker.OnValueChangeListener, NumberPicker.OnScrollListener, NumberPicker.Formatter {

    private List<String> mNames;
    private List<String> mPrices;
    private List<String> mQuantities;
    private Context mContext;
    //private Random mRandom = new Random();
    private RecyclerView mRecyclerView;



    private static int count;

    FruitAdapter(Context context) {
        mNames = new ArrayList<>();
        mPrices = new ArrayList<>();
        mQuantities = new ArrayList<>();
        mContext = context;
        count = 0;
    }

    @NonNull
    @Override
    public FruitAdapter.FruitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_list_view, parent, false);
        return new FruitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.FruitViewHolder holder, int position) {
        String[] elements = mNames.get(position).split(",");
        String name = elements[0];
        int price = Integer.parseInt(elements[1]);
        int quantity = Integer.parseInt(elements[2]);
        holder.mNameTextView.setText(mContext.getResources().getString(R.string.name_format, name));
        holder.mPriceTextView.setText(mContext.getResources().getString(R.string.price_format, price));
        holder.mQuantityTextView.setText(mContext.getResources().getString(R.string.quantity_format, quantity));
        int resId = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        holder.mFruitImageView.setImageResource(resId);
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    class FruitViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mPriceTextView;
        private TextView mQuantityTextView;
        private ImageView mFruitImageView;

        FruitViewHolder(View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.fruit_name);
            mPriceTextView = itemView.findViewById(R.id.fruit_price);
            mQuantityTextView = itemView.findViewById(R.id.fruit_quantity);
            mFruitImageView = itemView.findViewById(R.id.fruit_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFruitPurchaseDialog(mContext, mNameTextView, mPriceTextView, mQuantityTextView);
                }
            });

            int p = getAdapterPosition();

        }
    }

    public void addName() {
        mNames.add(0, getNameAndPrice());
        notifyItemInserted(0);
        notifyItemRangeChanged(0, mNames.size());
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    String[] names;
    public void setData(String fruitName) {
        if (fruitName != null) {
            names = new String[] {
                    "watermelon,20,110", "strawberry,15,300", "pomegranate,15,150", "pitaya,10,170", "orange,25,190",
                    "mango,40,200", "kiwifruit,35,220", "hamimelon,25,100", "banana,30,230", "apple,20,320"
            };

            for (int i = 0; i < names.length; i++) {
                String[] elements = names[i].split(",");
                String name = elements[0];
                int price = Integer.parseInt(elements[1]);
                int quantity = Integer.parseInt(elements[2]);
                if (name.equals(fruitName)) {
                    names = new String[] {
                         name + "," + price + "," + quantity
                    };
                    break;
                }
            }
        }
        else {
            names = new String[] {
                    "watermelon,20,110", "strawberry,15,300", "pomegranate,15,150", "pitaya,10,170", "orange,25,190",
                    "mango,40,200", "kiwifruit,35,220", "hamimelon,25,100", "banana,30,230", "apple,20,320"
            };
        }
    }

    private String getNameAndPrice() {


        return names[count++];
    }

    private void showFruitPurchaseDialog(Context context, final TextView mNameTextView, final TextView mPriceTextView, final TextView mQuantityTextView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fruit_purchase_dialog, null, false);
        builder.setView(view);

        final TextView nameTextView = view.findViewById(R.id.fruit_purchase_dialog_name_text_view);
        final TextView priceTextView = view.findViewById(R.id.fruit_purchase_dialog_price_text_view);
        final NumberPicker quantityNumberPicker = view.findViewById(R.id.fruit_number_picker);
        init(quantityNumberPicker, mQuantityTextView);

        String name = mNameTextView.getText().toString();
        String priceAsString = mPriceTextView.getText().toString();

        //Toast.makeText(mContext,name,Toast.LENGTH_LONG).show();
        nameTextView.setText(name);
        priceTextView.setText(priceAsString);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int quantity = Integer.parseInt(mQuantityTextView.getText()
                         .subSequence(10, mQuantityTextView.getText().length()).toString());
                mQuantityTextView.setText(mContext.getResources().getString(R.string.quantity_format, (quantity - quantityNumberPicker.getValue())));
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void init(NumberPicker numberPicker, TextView mQuantityTextView) {
        numberPicker.setFormatter(this);
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setOnScrollListener(this);
        numberPicker.setValue(1);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(Integer.parseInt(mQuantityTextView.getText()
                .subSequence(10, mQuantityTextView.getText().length()).toString()));  //TODO: ....

        //TODO: .............

        //Toast.makeText(mContext, mQuantityTextView.getText().toString().indexOf(10),);
    }

    public String format(int value) {
        String tmpStr = String.valueOf(value);
        if (value < 10) {
            tmpStr = "0" + tmpStr;
        }
        return tmpStr;
    }

    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

    }

    public void onScrollStateChange(NumberPicker view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
        }
    }
}