//==========================================
//	Title:  FlatLanders
//	Author: Fabian Joßberger | Luke Behrsing
//	Date:   15 Jan 2020
//==========================================

package com.pandaly.drivegame;

import android.os.Bundle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;
		initialize(new StartScreen(), config);
	}
}
