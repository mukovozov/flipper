package com.redmadrobot.sample.configs

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.redmadrobot.flipper.Feature
import com.redmadrobot.flipper.config.FlipperConfig
import com.redmadrobot.sample.BuildConfig
import com.redmadrobot.sample.R


class RemoteConfig(context: Context) : FlipperConfig {
    private val remoteConfig by lazy { FirebaseRemoteConfig.getInstance() }

    private val configSettings by lazy {
        FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
    }

    init {
        with(remoteConfig) {
            setDefaults(R.xml.remote_config_defaults)
            setConfigSettings(configSettings)
            fetch(0).addOnCompleteListener {
                if (it.isSuccessful) remoteConfig.activateFetched()
            }
        }
    }

    override fun featureIsEnabled(feature: Feature): Boolean {
        return remoteConfig.getBoolean(feature.id)
    }
}
