package com.teamnexters.ehhhh;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by HyeonSi on 2015-07-14.
 */
public class Ehhhh extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "jFEQcJnT5I2tVviV3faZOaeiye1SNXJtF51KtRgI", "QXn50z6hkZKwiCuKnOEnPMNXlVI4CukNL6GmfT8S");
    }
}
