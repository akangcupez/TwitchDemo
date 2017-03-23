package com.example.aji.twitchdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aji Subastian
 *
 * Untuk menggunakan Twitch TV Web Player di aplikasi lain, copy-paste file-file berikut:
 * In order to use Twitch TV Web Player in another application, copy-paste these files below:
 *
 * java > TwitchPlayerActivity.java
 * java > TwitchWebClient.java
 * java > TwitchWebInterface.java
 * res > layout > activity_twitch_player.xml
 *
 * Kemudian, set di manifest untuk TwitchPlayerActivity-nya (lihat contoh).
 * And then, set in the manifest for the TwitchPlayerActivity (see example)
 *
 * Pastikan theme pada values > styles.xml menggunakan theme fullscreen seperti pada contoh
 * Make sure that the theme on values > styles.xml uses fullscreen theme as in example
 *
 * TwitchPlayerActivity memerlukan data String dari intent extra, dengan key TwitchPlayerActivity.VIDEO_ID
 * TwitchPlayerActivity required String data type from intent extra, using TwitchPlayerActivity.VIDEO_ID as the key
 *
 * video ID adalah _id dengan format prefix "v" + angka (contoh: v106400740)
 * video ID is an _id with prefix "v" + number format (ex: v106400740)
 *
 */
public class DemoActivity extends AppCompatActivity implements DemoListAdapter.OnItemClickListener {

    private DemoListAdapter mAdapter;
    private List<DemoEntity> mDemoEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //prepare adapter
        mAdapter = new DemoListAdapter(this, mDemoEntityList);
        mAdapter.setOnItemClickListener(this);

        //recycler view
        RecyclerView rvTwitch = (RecyclerView) findViewById(R.id.rv_twitch);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        rvTwitch.setLayoutManager(mLayoutManager);
        rvTwitch.setItemAnimator(new DefaultItemAnimator());
        rvTwitch.setHasFixedSize(true);

        //set adapter to recycler view
        rvTwitch.setAdapter(mAdapter);

        //simulate populate new data
        populateData();
    }

    @Override
    public void onItemClick(View view, int position) {
        DemoEntity entity = mAdapter.getObject(position);
        if (!TextUtils.isEmpty(entity.videoId)) {
            //Open Twitch Player
            Intent intent = new Intent(DemoActivity.this, TwitchPlayerActivity.class);
            intent.putExtra(TwitchPlayerActivity.VIDEO_ID, entity.videoId);
            startActivity(intent);
        }
    }

    private void populateData() {
        //Dummy content
        mDemoEntityList.add(new DemoEntity(R.drawable.thumba, "v106400740"));
        mDemoEntityList.add(new DemoEntity(R.drawable.thumbb, "v91943175"));
        mDemoEntityList.add(new DemoEntity(R.drawable.thumbc, "v126872798"));
        mDemoEntityList.add(new DemoEntity(R.drawable.thumbd, "v127626643"));

        mAdapter.setEntityList(mDemoEntityList);
    }

}
