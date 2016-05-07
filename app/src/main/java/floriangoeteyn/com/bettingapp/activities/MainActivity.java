package floriangoeteyn.com.bettingapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.wunderlist.slidinglayer.SlidingLayer;
import com.wunderlist.slidinglayer.transformer.AlphaTransformer;

import java.util.Arrays;

import butterknife.ButterKnife;
import floriangoeteyn.com.bettingapp.adapters.BetAdapter;
import floriangoeteyn.com.bettingapp.R;
import floriangoeteyn.com.bettingapp.adapters.CategoryAdapter;
import floriangoeteyn.com.bettingapp.model.Bet;
import floriangoeteyn.com.bettingapp.model.BetCategory;
import floriangoeteyn.com.bettingapp.repo.LoadedListener;
import floriangoeteyn.com.bettingapp.repo.Repository;

public class MainActivity extends AppCompatActivity implements LoadedListener{


    private SlidingLayer slidingLayer;
    private RecyclerView rv;
    private BetAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Repository.registerListener(this);
        Repository.loadBets();

        setContentView(R.layout.activity_main);

        rv = (RecyclerView) findViewById(R.id.betRecyclerView);
        slidingLayer = (SlidingLayer) findViewById(R.id.slider);

        createActionbar();
        createItemView();
        createSlider();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void createItemView(){

        //opbouw van de adapter voor de recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new BetAdapter(Repository.getBets(), this);
        rv.setAdapter(adapter);
    }


    private void createActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View mActionBarView = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.LEFT);
        actionBar.setCustomView(mActionBarView, lp);
        Toolbar parent =(Toolbar) actionBar.getCustomView().getParent();
        parent.setContentInsetsAbsolute(0, 0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        createOptions(mActionBarView);
    }

    private void createOptions(View mActionBarView){

        ImageButton info = ButterKnife.findById(mActionBarView, R.id.action_info);
        ImageButton filter = ButterKnife.findById(mActionBarView, R.id.action_filter);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getApplicationContext())
                        .setMessage(R.string.info_dialog_text)
                        .setTitle(R.string.info_dialog_title)
                        .setCancelable(true)
                        .setNeutralButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .create()
                        .show();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (slidingLayer.isClosed()) {
                    slidingLayer.openLayer(true);
                } else {
                    slidingLayer.closeLayer(true);
                }
            }
        });

        mActionBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }



    private void createSlider() {
        slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        slidingLayer.setShadowSizeRes(R.dimen.shadow_size);
        slidingLayer.setChangeStateOnTap(false);
        slidingLayer.setStickTo(SlidingLayer.STICK_TO_LEFT);
        slidingLayer.setSlidingEnabled(false);
        slidingLayer.closeLayer(true);
        slidingLayer.setLayerTransformer(new AlphaTransformer());

        RecyclerView categoryRv = (RecyclerView) findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        categoryRv.setLayoutManager(llm);

        BetCategory[] categories = BetCategory.values();

        CategoryAdapter categoryAdapter = new CategoryAdapter(Arrays.asList(categories));
        categoryRv.setAdapter(categoryAdapter);
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
    public void onBetsLoaded() {
        adapter.setBets(Repository.getBets());
    }

    @Override
    public void onLoadFailed() {
    }

    @Override
    public void onBetAdded(Bet bet, int pos) {
        adapter.notifyItemInserted(pos);
    }

    @Override
    public void onBetRemoved(Bet bet, int pos) {
        adapter.notifyItemRemoved(pos);
    }
}
