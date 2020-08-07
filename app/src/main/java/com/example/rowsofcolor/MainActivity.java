package com.example.rowsofcolor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private int numTextView = 12;
    private TextView[] mColorViews =new  TextView[numTextView];
    private String[] mColorNameId = {"زردخردلی", "آبی فیروزه ای", "سبز روشن", "صورتی کثیف", "بنفش روشن"
            , "بادمجانی", "صدفی", "یاسی", "آبی آسمانی", "قرمز"
            , "نارنجی", "آبی"};
    int[] resourceId =new int[numTextView];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resourceId=createId(mColorViews,"box_");
        findElem();
        setListener();
    }

    private <T extends View> int[] createId(T[] views, String commonPartOfId) {
        int[] IDs = new int[views.length];
        for (int i = 0; i < views.length; i++) {
            int tempt = getId(commonPartOfId + i, R.id.class);
            IDs[i] = tempt;
        }
        return IDs;
    }

    public void changeText(TextView textView) {
        TextView[] textViews = mColorViews;
        String text = (String) textView.getText();
        for (int i = 0; i < mColorViews.length; i++) {
            textViews[i].setText(text);
        }
    }

    public void backDefText() {
        for (int i = 0; i < mColorViews.length; i++) {
            mColorViews[i].setText(mColorNameId[i]);
        }
    }

    private void findElem() {
        for (int i = 0; i < mColorViews.length; i++) {
            mColorViews[i] = findViewById(resourceId[i]);
        }
    }

    public void setListener() {
        for (int i = 0; i < mColorViews.length; i++) {
            final int finalI = i;
            mColorViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mColorViews[finalI].setVisibility(View.GONE);
                    changeText(mColorViews[finalI]);
                    mColorViews[finalI].postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backDefText();
                        }
                    }, 5000);
                }
            });
        }
    }

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }
}