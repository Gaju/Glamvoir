package glamvoir.appzstack.glamvoir.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.LoadableListAdapter;

/**
 * Created by anupam on 4/8/15.
 */
public abstract class BaseActivity<T extends Parcelable> extends AppCompatActivity {

    protected Toolbar toolbar;
    protected RecyclerView recyclerView;
    protected ArrayList<T> data = new ArrayList<T>();
    protected LoadableListAdapter<T> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_following);

        //initialize all views
        initViews();

        initListener();

        init();

        getToolbar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    /**
     * customize the toolbar
     *
     * @param toolbar : pass the toolbar reference
     */
    private void getToolbar(Toolbar toolbar) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getAppBarTitle());
    }

    protected abstract String getAppBarTitle();
    protected abstract void loadData();
    protected abstract LoadableListAdapter<T> createAdapter(ArrayList<T> data);


    /**
     * initialize all views listeners
     */
    private void initListener() {

    }

    private void init() {

        adapter = createAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    /**
     * initialize all views
     */
    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    public void dataLoaded(Collection<T> responseData) {

        if (responseData != null) {
            if (this.data != null)
                this.data.addAll(responseData);
            }

//            this.totalRows = totalRows;
//
//            if (totalRows > this.data.size()) {
//                adapter.setLoadingFooterType(LoadableListAdapter1.LoadingFooterType.Loading);
//            } else {
//                adapter.setLoadingFooterType(LoadableListAdapter1.LoadingFooterType.None);
//            }

            adapter.notifyDataSetChanged();


    }
}
