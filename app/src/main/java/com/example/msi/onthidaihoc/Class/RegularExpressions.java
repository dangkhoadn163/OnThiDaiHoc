package com.example.msi.onthidaihoc.Class;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by MSI on 1/12/2018.
 */

public class RegularExpressions {



    public RegularExpressions() {
    }

    public boolean check(final String REGEX, final String INPUT ) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);
        Log.d("DK", "Current REGEX is: " + REGEX);
        Log.d("DK", "Current INPUT is: " + INPUT);
        Log.d("DK", "lookingAt(): " + matcher.lookingAt());
        Log.d("DK", "matches(): " + matcher.matches());
        return matcher.matches();
    }
}
