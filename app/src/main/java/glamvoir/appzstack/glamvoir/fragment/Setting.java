package glamvoir.appzstack.glamvoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glamvoir.appzstack.glamvoir.R;


/**
 * Created by jaim on 7/25/2015.
 */
public class Setting extends android.support.v4.app.Fragment {


    public static final String TAG = Setting.class
            .getSimpleName();

    public static Setting newInstance() {

        return new Setting();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting,container,false);
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





    }


}
