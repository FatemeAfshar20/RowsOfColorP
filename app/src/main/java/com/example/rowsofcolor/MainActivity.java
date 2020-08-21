package com.example.rowsofcolor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    public static final String BUNDLE_VIEW_VISIBILITY = "View Visibility";
    private int numTextView = 12;
    private TextView[] mColorViews = new TextView[numTextView];
    private int[] mColorName = {R.string.color_beauty_yellow, R.string.color_turquoise_blue, R.string.color_beauty_green, R.string.color_beauty_pink
            , R.string.color_beauty_purple_light, R.string.color_brown,R.string.color_beauty_pink_light, R.string.color_yasi, R.string.color_beauty_blue
            , R.string.color_beauty_red, R.string.color_orange,R.string.color_blue_dark};
    int[] resourceId = createId(mColorViews, "box_");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findElem();
        setListener();
        saveInstance(savedInstanceState);
    }

    private <T extends View> int[] createId(T[] views, String commonPartOfId) {
        int[] IDs = new int[views.length];
        for (int i = 0; i < views.length; i++) {
            IDs[i] =  getId(commonPartOfId + i, R.id.class);
        }
        return IDs;
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

    public void changeText(TextView textViewListener) {
        TextView[] textViews = mColorViews;
        String text = (String) textViewListener.getText();
        for (int i = 0; i < mColorViews.length; i++) {
            textViews[i].setText(text);
        }
    }
// back Definition text
    public void backDefText() {
        for (int i = 0; i < mColorViews.length; i++) {
            mColorViews[i].setText(mColorName[i]);
        }
    }

    public void saveInstance(Bundle bundle) {
        if (bundle != null) {
            int[] setViewVisibility=bundle.getIntArray(BUNDLE_VIEW_VISIBILITY);
            for (int i = 0; i < setViewVisibility.length; i++) {
                mColorViews[i].setVisibility(setViewVisibility[i]);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int[] txtViewVisibility = new int[numTextView];

        for (int i = 0; i < txtViewVisibility.length; i++) {
            txtViewVisibility[i] = mColorViews[i].getVisibility();
        }

        outState.putIntArray(BUNDLE_VIEW_VISIBILITY, txtViewVisibility);
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