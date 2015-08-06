package glamvoir.appzstack.glamvoir.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anupam on 4/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(getLayoutResourceId());
        }

        protected abstract int getLayoutResourceId();


}
