package com.angelomoroni.coordinatorlayoutexample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import atownsend.swipeopenhelper.BaseSwipeOpenViewHolder;
import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

/**
 * Created by debug on 07/06/16.
 */
public class AliasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 1;
    private static final int TITLE_SECTION = 2;
    private static final int MAIN_FIELD = 3;
    private static final int FOOTER = 4;
    private static final int SECONDARY_FIELD = 5;
    private final SwipeOpenItemTouchHelper helper;
    private OnRowClickListener listener;

    public View header;
    public View footer;
    public String mainEmail,mainPhoneNumber;
    public ArrayList<String> emails,phones;
    public int sections[] = new int[2];

    public ArrayList<Integer> viewTypeList = new ArrayList<>();

    public AliasAdapter(View header, View footer, String mainEmail, String mainPhoneNumber,SwipeOpenItemTouchHelper helper,OnRowClickListener listener) {
        this.header = header;
        this.footer = footer;
        this.mainEmail = mainEmail;
        this.mainPhoneNumber = mainPhoneNumber;
        emails = new ArrayList<>();
        phones = new ArrayList<>();
        sections[0] = 1;
        sections[1] = 3+emails.size();

        viewTypeList.add(0,HEADER);
        viewTypeList.add(1,TITLE_SECTION);
        viewTypeList.add(2,MAIN_FIELD);
        viewTypeList.add(3,TITLE_SECTION);
        viewTypeList.add(4,MAIN_FIELD);
        viewTypeList.add(5,FOOTER);

        this.helper = helper;

        this.listener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case HEADER:
                viewHolder = new HeaderViewHolder(header);
                break;
            case TITLE_SECTION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_section_layout, parent, false);
                viewHolder = new TitleSectionViewHolder(view);
                break;
            case MAIN_FIELD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
                viewHolder = new NotSwipeViewHolder(view);
                break;
            case SECONDARY_FIELD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            case FOOTER:
                viewHolder = new FooterViewHolder(footer);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ViewHolder){
            ViewHolder v = (ViewHolder) viewHolder;

            if(position<sections[1]){
                v.tv_country.setText(emails.get(position-(sections[0]+2)));
            }else {
                v.tv_country.setText(phones.get(position-(sections[1]+2)));
            }
            //v.tv_country.setText(countries.get(getPosition(i)));


        }else if(viewHolder instanceof NotSwipeViewHolder) {
            NotSwipeViewHolder v = (NotSwipeViewHolder) viewHolder;
            if(position == 2){
                v.tv_country.setText(mainEmail);
            }else {
                v.tv_country.setText(mainPhoneNumber);
            }

        }else if( viewHolder instanceof TitleSectionViewHolder){
            TitleSectionViewHolder t = (TitleSectionViewHolder) viewHolder;
            t.tv_country.setText(position == sections[0]?"INDIRIZZI EMAIL":"NUMERI DI TELEFONO");
        }else {
            //nothing
        }
    }

    @Override
    public int getItemCount() {
        return viewTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return viewTypeList.get(position);
    }

    public void addEmail(String em) {
        if(emails.size() <2) {

            int indToAdd = sections[1];
            viewTypeList.add(indToAdd, SECONDARY_FIELD);
            emails.add(em);
            sections[1] += 1 ;

            notifyItemInserted(indToAdd);

        }

    }

    public void addPhonNumber(String pn) {
        if(phones.size()<2){
            int indToAdd = getItemCount()-1;
            viewTypeList.add(indToAdd,SECONDARY_FIELD);
            phones.add(pn);

            notifyItemInserted(indToAdd);
        }
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
        ImageView deleteB,verifyB;
        public ViewHolder(View view) {
            super(view);

            deleteB = (ImageView) view.findViewById(R.id.delete_button);
            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick("delete");
                }
            });
            verifyB = (ImageView) view.findViewById(R.id.verify_button);
            verifyB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick("verify");
                }
            });

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
            return deleteB.getMeasuredWidth() * 2;
        }

        @Override
        public float getStartHiddenViewSize() {
            return 0;
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.closeAllOpenPositions();
                }
            });
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.closeAllOpenPositions();
                }
            });

        }
    }

    private class TitleSectionViewHolder extends RecyclerView.ViewHolder {
        TextView tv_country;
        public TitleSectionViewHolder(View view) {
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.closeAllOpenPositions();
                }
            });

            tv_country = (TextView)view.findViewById(R.id.tv_country);
        }
    }

    public interface OnRowClickListener {
        void onVerifyClick(String item);
        void onDeleteClick(String item);
        void onAliasClick(String item);
    }
}
