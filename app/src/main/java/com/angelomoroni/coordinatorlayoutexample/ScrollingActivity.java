package com.angelomoroni.coordinatorlayoutexample;

import android.content.res.Resources;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;


public class ScrollingActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener, AliasAdapter.OnRowClickListener {



    private ImageView mProfileImage;
    private View picContainer;
    private int mMaxScrollSize;
    private LinearLayout mTitleContainer;

    int lastPercentageScroll = 0;
    private TextView mTitlePage;
    private RecyclerView recyclerView;
    private AliasAdapter aliasAdapter;
    private SwipeOpenItemTouchHelper helper;
    private View header,footer;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
        picContainer = findViewById(R.id.pic_container);
        mTitleContainer = (LinearLayout) findViewById(R.id.materialup_title_container);
        mTitlePage = (TextView) findViewById(R.id.title_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        setList();


    }

    private void setList() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
        helper.setPreventZeroSizeViewSwipes(true);

        header = LayoutInflater.from(this).inflate(R.layout.header_list, recyclerView, false);
        footer = LayoutInflater.from(this).inflate(R.layout.footer_list, recyclerView, false);
        aliasAdapter = new AliasAdapter(header,footer,"angelo@bemind.me","3395566777",helper,this);
        recyclerView.setAdapter(aliasAdapter);

        helper.attachToRecyclerView(recyclerView);
        helper.setCloseOnAction(true);
    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        helper.closeAllOpenPositions();
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;


        if(lastPercentageScroll != percentage) {
            lastPercentageScroll = percentage;


            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
            int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, r.getDisplayMetrics());

            int imgPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mTitleContainer.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, (int) (px - (px * (percentage /100.0))));
            mTitleContainer.setLayoutParams(params);

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mProfileImage.getLayoutParams();
            params1.width = (int) (imgPx - (imgPx * (percentage*1.5f /100.0)));
            params1.height = (int) (imgPx - (imgPx * (percentage*1.5f /100.0)));

            mProfileImage.setLayoutParams(params1);
            mProfileImage.setAlpha(1- (percentage*2.0F/100.0F));

//            mProfileImage.setAlpha(alpha);


            float div = (percentage * (percentage <90?1:1.5F)/100.0F);
            float alpha = 1 - div;
//            Log.d("ScrollingActivity","Alpha: "+ alpha);
            mTitlePage.setAlpha(alpha <0 ? 0:alpha);

            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) picContainer.getLayoutParams();
            params2.topMargin = (int) ((px2 * percentage*4.5)/100.0);
//            Log.d("ScrollingActivity","TopMargin Pic: "+ params2.topMargin);
//            Log.d("ScrollingActivity","TPercentage: "+ percentage);
            picContainer.setLayoutParams(params2);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            addRandomAlias();
        }
    }

    private void addRandomAlias() {
        Random random = new Random();
        if(random.nextInt()%2 == 0){
            aliasAdapter.addEmail("secondary@email.it");
        } else {
            aliasAdapter.addPhonNumber("+393334422112");
        }
    }

    @Override
    public void onVerifyClick(String item) {
        Toast.makeText(this,item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(String item) {
        Toast.makeText(this,item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAliasClick(String item) {

    }
}
