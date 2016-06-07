package com.angelomoroni.coordinatorlayoutexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by debug on 07/06/16.
 */
public class AliasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public View header;
    public View footer;
    public String mainEmail,mainPhoneNumber;
    public ArrayList<String> emails,phones;
    public int sections[] = new int[2];

    public AliasAdapter(View header, View footer, String mainEmail, String mainPhoneNumber) {
        this.header = header;
        this.footer = footer;
        this.mainEmail = mainEmail;
        this.mainPhoneNumber = mainPhoneNumber;
        emails = new ArrayList<>();
        phones = new ArrayList<>();
        sections[0] = 1;
        sections[1] = 2+emails.size();
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
}
