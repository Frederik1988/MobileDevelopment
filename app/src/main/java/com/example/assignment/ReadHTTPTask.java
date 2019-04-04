package com.example.assignment;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class ReadHTTPTask  extends AsyncTask<String, Void, CharSequence> {
    @Override
    protected CharSequence doInBackground(String... urls) {
        String urlString = urls[0];
        try {
            CharSequence result = HTTPHelper.GetHttpResponse(urlString);
            return result;
        } catch (IOException ex) {
            cancel(true);
            String errorMessage = ex.getMessage() + "\n" + urlString;
            Log.e("WRISTBAND", errorMessage);
            return errorMessage;
        }
    }
}
