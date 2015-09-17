package glamvoir.appzstack.glamvoir.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glamvoir.appzstack.glamvoir.R;

/**
 * Created by jai on 9/16/2015.
 */
public class Interest extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interest,container,false);
        return v;
    }
}