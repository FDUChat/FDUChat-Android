package com.fdu.fduchat.smart_compoent;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by HurricaneTong on 15/10/12.
 */
public class SmartTextView extends TextView implements SmartComponent {

    public SmartTextView(Context context) {
        super(context);
    }

    public void setText(Object text) {
        super.setText((String) text);
    }

    public String getText() {
        return (String)super.getText();
    }

}
