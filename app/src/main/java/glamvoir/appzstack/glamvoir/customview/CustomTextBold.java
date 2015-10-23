package glamvoir.appzstack.glamvoir.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by jai on 10/4/2015.
 */
public class CustomTextBold extends TextView {

    private Context c;
    public CustomTextBold (Context c) {
        super(c);
        this.c = c;
        Typeface face=Typeface.createFromAsset(c.getAssets(), "nexa_bold-webfont.ttf");
        setTypeface(face);

    }
    public CustomTextBold (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = context;
        Typeface face=Typeface.createFromAsset(c.getAssets(), "nexa_bold-webfont.ttf");
        setTypeface(face);
    }

    public CustomTextBold (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        Typeface face=Typeface.createFromAsset(c.getAssets(), "nexa_bold-webfont.ttf");
        setTypeface(face);

    }



}