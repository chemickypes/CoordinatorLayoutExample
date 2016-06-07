package com.angelomoroni.coordinatorlayoutexample;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;

public class ScrollingActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener{


    /*private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private static final String TAG = "ACTIVITY MATERIAL";
    private boolean mIsAvatarShown = true;*/

    private ImageView mProfileImage;
    private int mMaxScrollSize;
    private LinearLayout mTitleContainer;

    int lastPercentageScroll = 0;
    private TextView mTitlePage;
    private RecyclerView recyclerView;
    private ArrayList<String> countries = new ArrayList<>();
    private DataAdapter adapter;
    private SwipeOpenItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
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

        setList();


    }

    private void setList() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);
        adapter.add("Australia");
        adapter.add("India");
        adapter.add("United States of America");
        adapter.add("Germany");
        adapter.add("Russia");
        //adapter.notifyDataSetChanged();

        helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(
                SwipeOpenItemTouchHelper.START ));

        helper.attachToRecyclerView(recyclerView);
        helper.setCloseOnAction(true);
    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

       /* if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false;
            mProfileImage.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true;

            mProfileImage.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }*/

        //Log.d(TAG,"percentage:"+percentage);

        if(lastPercentageScroll != percentage) {
            lastPercentageScroll = percentage;


            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());

            int imgPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mTitleContainer.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, (int) (px - (px * (percentage /100.0))));
            mTitleContainer.setLayoutParams(params);

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mProfileImage.getLayoutParams();
            params1.width = (int) (imgPx - (imgPx * (percentage*2.5f /100.0)));
            params1.height = (int) (imgPx - (imgPx * (percentage*2.5f /100.0)));
            mProfileImage.setLayoutParams(params1);
            mProfileImage.setAlpha(1- (percentage*2.5F/100.0F));

            mTitlePage.setAlpha(1- (percentage/100.0F));
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
}
