package io.particle.particleauthtest;

import android.app.Application;

import io.particle.android.sdk.cloud.ParticleCloudSDK;

public class AuthTestApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParticleCloudSDK.init(this);
    }
}
