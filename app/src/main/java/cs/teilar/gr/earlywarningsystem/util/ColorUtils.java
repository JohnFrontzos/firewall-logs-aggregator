package cs.teilar.gr.earlywarningsystem.util;


import android.graphics.Color;

import java.util.Random;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 22/11/2015
 */
public class ColorUtils {


    public static int randomColor() {
        Random rand = new Random();
        return Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }


}
