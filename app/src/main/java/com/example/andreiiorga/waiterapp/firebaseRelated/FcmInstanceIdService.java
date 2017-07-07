package com.example.andreiiorga.waiterapp.firebaseRelated;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.andreiiorga.waiterapp.R;
import com.example.andreiiorga.waiterapp.staticUtils.StaticStrings;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.zzj;

/**
 * Created by andreiiorga on 27/06/2017.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        StaticStrings.FIREBASE_TOKEN = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", StaticStrings.FIREBASE_TOKEN);
    }
}
