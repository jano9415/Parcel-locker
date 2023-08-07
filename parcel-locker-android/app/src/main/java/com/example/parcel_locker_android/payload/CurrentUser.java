package com.example.parcel_locker_android.payload;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.parcel_locker_android.payload.response.LoginResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentUser {

    public static LoginResponse getCurrentUser(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String currentUserString = sharedPreferences.getString("currentUser", null);
        if(currentUserString != null) {
            try {
                JSONObject jsonToObject = new JSONObject(currentUserString);
                String token = jsonToObject.getString("token");
                String tokenType = jsonToObject.getString("tokenType");
                String userId = jsonToObject.getString("userId");
                String emailAddress = jsonToObject.getString("emailAddress");

                //JSONArray jsonArray = jsonToObject.getJSONArray("roles");
                List<String> roles = new ArrayList<>();
                /*for(int i = 0; i < jsonArray.length(); i++){
                    roles.add(jsonArray.getString(i));
                }*/
                return new LoginResponse("null", token, tokenType, userId, emailAddress, roles);
            }catch(JSONException e) {
                Log.d("hiba", e.toString());
            }
        }
        return null;

    }
}




