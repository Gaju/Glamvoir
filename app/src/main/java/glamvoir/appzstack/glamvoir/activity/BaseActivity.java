package glamvoir.appzstack.glamvoir.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by anupam on 4/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public enum Car {
        AUDI {
            @Override
            public int getPrice() {
                return 25000;
            }
        };

        public abstract int getPrice();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();


}
