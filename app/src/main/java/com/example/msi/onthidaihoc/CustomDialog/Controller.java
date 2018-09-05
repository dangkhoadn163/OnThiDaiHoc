package com.example.msi.onthidaihoc.CustomDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;


import com.example.msi.onthidaihoc.Activity.ChooseActivity;
import com.example.msi.onthidaihoc.Class.BackgroundWoker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

/**
 * Created by DK on 9/23/2017.
 */

public class Controller {
    static String encodedImage;

    //encode image ra base64
    public static void getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("base64: ", "" + encodedImage);
    }

    public static void uploadAvatar(Context context, final String userID, Bitmap imageUri) {
        //class trên
        getStringImage(imageUri);
        //Nhung library java.util.calendar
        Calendar c = Calendar.getInstance();
        String s = c.getTimeInMillis() + "";
        // up hinh len database
        String type = "uploadavatar";
        BackgroundWoker backgroundWoker = new BackgroundWoker(context, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.d("thong bao", "" + output);
            }
        });
        backgroundWoker.execute(type, userID, encodedImage);
    }

    public static void downloadAvatar(Context context, final String userID) {
        //Nhung library java.util.calendar
        Calendar c = Calendar.getInstance();
        String s = c.getTimeInMillis() + "";
        // down hinh len database
        String type = "downloadavatar";
        BackgroundWoker backgroundWoker = new BackgroundWoker(context, new BackgroundWoker.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.d("encodeImage", "encodeImage: " + output);

                try {
                    JSONArray jsonArray = new JSONArray(output);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String encode = jsonObject.getString("Encode");

                    Log.d("RESPONSE",encode);

                    parseStringToBitMap(encode);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        backgroundWoker.execute(type, userID);
    }

    private static void parseStringToBitMap(String encode) {
        byte[] decodeResponse = Base64.decode(encode, Base64.DEFAULT | Base64.NO_WRAP);
        Bitmap deocdeBytes = BitmapFactory.decodeByteArray(decodeResponse, 0, decodeResponse.length);

        ChooseActivity.setImage(deocdeBytes);
    }
}
