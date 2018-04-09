package com.example.ashkan.a531.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ashkan.a531.Adapters.SetFragmentPageRecyclerAdapter;

/**
 * Created by Ashkan on 3/29/2018.
 */

public class SetsFragmentBindingAdapter {
    @BindingAdapter({"positionOfPager","weightEntered"})
    public static void setSetsList(RecyclerView view, int positionOfPager, int adjustedOneRepMax){
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if(layoutManager == null){
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        SetFragmentPageRecyclerAdapter adapter = (SetFragmentPageRecyclerAdapter) view.getAdapter();
        if(adapter == null){
            adapter = new SetFragmentPageRecyclerAdapter(view.getContext(),positionOfPager,adjustedOneRepMax);
        }
    }
}
