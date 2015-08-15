package com.teamnexters.ehhhh;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by HyeonSi on 2015-07-14.
 */
public class Ehhhh extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "GlRu30kOz11iiOVolSoLFNTq1eteictDJZQlMT0l", "9Fp73GbZ7NSWe7mD9W3oW8gcwN913j03UXAIJxBk");
    }
}