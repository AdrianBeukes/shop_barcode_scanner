package com.example.busyshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busyshop.CustomItemClickListener;
import com.example.busyshop.R;
import com.example.busyshop.model.ItemDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsListingAdapter extends CustomRecyclerViewAdapter {

    String TAG = "ContactsListingAdapter";

    Context context;
    ArrayList<ItemDetail> itemDetailArrayList;
    CustomItemClickListener customItemClickListener;


    public ContactsListingAdapter(Activity activity, ArrayList<ItemDetail> contactsData,
                                  CustomItemClickListener customItemClickListener) {
        super(activity, contactsData, customItemClickListener);
        this.context = activity;
        this.itemDetailArrayList = contactsData;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(v -> {
            if (customItemClickListener != null)
                customItemClickListener.onItemClick(v, mViewHolder.getAdapterPosition());
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder type_item = (ViewHolder) holder;
        final ItemDetail itemDetail = getListing().get(position);

        //set text here for each Item - Description, image, Price, quantity
        //would also have to set for each classof the items
        setText(type_item.txtName, itemDetail.getAPL883().getDescription());
        setText(type_item.txtPrice, String.valueOf(itemDetail.getAPL883().getPrice()));
        //quantity added here with a count of how many

        //set item image
        setDrawable(type_item.ivItemImage, itemDetail.getAPL883().getImage(),position);

        // Redirects the user click action to the respective callbacks
        //add more quantity to cart
        type_item.ivSubtract.setOnClickListener(view -> {
            if (customItemClickListener != null)
                customItemClickListener.onItemClick(view, type_item.getAdapterPosition(), ActionItem.SUBTRACT);
        });

        //removes quantity from cart
        type_item.ivAdd.setOnClickListener(view -> {
            if (customItemClickListener != null)
                customItemClickListener.onItemClick(view, type_item.getAdapterPosition(), ActionItem.PLUS);
        });
    }

    public void setText(TextView tvSelectedControl, String textValue)
    {
        if(TextUtils.isEmpty(textValue))
            tvSelectedControl.setVisibility(View.GONE);
        else
        {
            tvSelectedControl.setVisibility(View.VISIBLE);
            tvSelectedControl.setText(textValue);
            Log.d(TAG, "setText: "+textValue);
        }
    }

    private void setDrawable(ImageView imageView, String value, int index)
    {
        //if quantity of items is equall or less thatn 0, set inactive
        String activeColor[]={"","#65AC58","#FF8B00","#0492FF","#EA0404"};
        String inActiveColor[]={"","#D1D1D1","#D1D1D1","#D1D1D1","#EA0404"};

        if(TextUtils.isEmpty(value))
        {
            imageView.setClickable(false);
            imageView.setColorFilter(Color.parseColor(inActiveColor[index]));

        }
        else
        {
            imageView.setClickable(true);
            imageView.setColorFilter(Color.parseColor(activeColor[index]));

        }
    }

    public enum ActionItem
    {
        PLUS, SUBTRACT
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public ArrayList<ItemDetail> getListing() {
        return super.getListing();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivItemImage)
        ImageView ivItemImage;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;
        @BindView(R.id.txtQuantity)
        TextView txtQuantity;
        @BindView(R.id.ivSubtract)
        ImageView ivSubtract;
        @BindView(R.id.ivAdd)
        ImageView ivAdd;
        @BindView(R.id.cvDetails)
        CardView cvDetails;
        @BindView(R.id.cvItem)
        CardView cvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
