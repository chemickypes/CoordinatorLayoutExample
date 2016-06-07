package com.angelomoroni.coordinatorlayoutexample;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

/**
 * Created by debug on 07/06/16.
 */
public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 90;
    private final SwipeOpenItemTouchHelper helper;
    private ArrayList<String> countries;

    public DataAdapter(SwipeOpenItemTouchHelper helper, OnItemClickListener listener) {
        this.countries = new ArrayList<>();
        this.helper = helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        if(i == HEADER){
            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_list, viewGroup, false));
        }

        if(i ==0) {
            return new ViewHolder(view);
        }else {
            return new NotSwipeViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if(viewHolder instanceof ViewHolder){
            ViewHolder v = (ViewHolder) viewHolder;
            v.tv_country.setText(countries.get(getPosition(i)));
        }else if(viewHolder instanceof NotSwipeViewHolder) {
            NotSwipeViewHolder v = (NotSwipeViewHolder) viewHolder;
            v.tv_country.setText(countries.get(getPosition(i)));
        }else {
            //nothing
        }
       // viewHolder.tv_country.setText(countries.get(i));
    }

    private int getPosition(int i) {
        return i-1;
    }

    @Override
    public int getItemCount() {
        return countries.size()+1;
    }

    public void addItem(String country) {
        countries.add(country);
        notifyItemInserted(countries.size());
    }

    public void removeItem(int position) {
        countries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, countries.size());
    }

    public void add(String country) {
        addItem(country);
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(position == 0) return HEADER;
        return (position %2 );
    }

    public class NotSwipeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_country;
        public NotSwipeViewHolder(View view) {
            super(view);

            tv_country = (TextView)view.findViewById(R.id.tv_country);
            tv_country.setTextColor(Color.parseColor("#987564"));

            tv_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.closeAllOpenPositions();
                }
            });
        }

    }



    public class ViewHolder extends BaseSwipeOpenViewHolder {
        TextView tv_country;
        public ViewHolder(View view) {
            super(view);


            tv_country = (TextView)view.findViewById(R.id.tv_country);

            tv_country.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.closeAllOpenPositions();
                }
            });
        }

        @NonNull
        @Override
        public View getSwipeView() {
            return tv_country;
        }

        @Override
        public float getEndHiddenViewSize() {
            return 200;
        }

        @Override
        public float getStartHiddenViewSize() {
            return 0;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(String s);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }
}
