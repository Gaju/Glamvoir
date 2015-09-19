package glamvoir.appzstack.glamvoir.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import glamvoir.appzstack.glamvoir.R;
import glamvoir.appzstack.glamvoir.adapter.Custome_All_ListAdapter;


public class ALL extends Fragment {

    //ImagePagerAdapter adapter;
    private ListView mlistView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.all,container,false);
        //feed_or_felia_market_shell

        mlistView= (ListView) v.findViewById(R.id.lv_all);
       // mlistView.setAdapter(new Custome_All_ListAdapter(getActivity()));
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return v;
    }

}