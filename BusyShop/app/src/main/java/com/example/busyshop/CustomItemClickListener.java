package com.example.busyshop;

import android.view.View;
import com.example.busyshop.adapter.ContactsListingAdapter;

public interface CustomItemClickListener {
     void onItemClick(View v, int position);
     void onItemClick(View v, int position, ContactsListingAdapter.ActionItem actionItem);
}
