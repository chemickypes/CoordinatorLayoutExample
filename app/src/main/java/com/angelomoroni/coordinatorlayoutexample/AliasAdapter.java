package com.angelomoroni.coordinatorlayoutexample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;

/**
 * Created by debug on 07/06/16.
 */
public class AliasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 1;
    private static final int TITLE_SECTION = 2;
    private static final int MAIN_FIELD = 3;
    private static final int FOOTER = 4;
    private static final int SECONDARY_FIELD = 5;

    public View header;
    public View footer;
    public String mainEmail,mainPhoneNumber;
    public ArrayList<String> emails,phones;
    public int sections[] = new int[2];

    public ArrayList<Integer> viewTypeList = new ArrayList<>();

    public AliasAdapter(View header, View footer, String mainEmail, String mainPhoneNumber) {
        this.header = header;
        this.footer = footer;
        this.mainEmail = mainEmail;
        this.mainPhoneNumber = mainPhoneNumber;
        emails = new ArrayList<>();
        phones = new ArrayList<>();
        sections[0] = 1;
        sections[1] = 2+emails.size();

        viewTypeList.add(HEADER);
        viewTypeList.add(TITLE_SECTION);
        viewTypeList.add(MAIN_FIELD);
        viewTypeList.add(TITLE_SECTION);
        viewTypeList.add(MAIN_FIELD);
        viewTypeList.add(FOOTER);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6+emails.size()+phones.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return viewTypeList.get(position);
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
                    //helper.closeAllOpenPositions();
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
//                    helper.closeAllOpenPositions();
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

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}