package id.pens.eventorganizer;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;

import android.widget.EditText;

/**
 * Created by MONKEY on 25/01/2018.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface typefaceLato = Typeface.createFromAsset(getContext().getAssets(),"font/Lato-Light.ttf");
            setTypeface(typefaceLato);
        }
    }

}