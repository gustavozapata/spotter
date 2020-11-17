package me.gustavozapata.spotter.utils;

import android.graphics.Color;
import android.widget.TextView;

public class SpotCheckUtils {
    public static void colourResult(String result, TextView textView) {
        if (result != null) {
            switch (result) {
                case "Produced documents":
                    textView.setTextColor(Color.rgb(245, 156, 22));
                    break;
                case "Advice given":
                    textView.setTextColor(Color.rgb(18, 141, 210));
                    break;
                case "Driver detained":
                    textView.setTextColor(Color.rgb(225, 23, 47));
                    break;
            }
        }
    }
}
