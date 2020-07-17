package com.fatihb.sur;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	MediaPlayer py;
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		py=MediaPlayer.create(this,R.raw.song);
		initialize(new SurvivorBird(), config);
	}

	@Override
	protected void onResume() {
		super.onResume();

		py.start();
		py.setLooping(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
		py.pause();
	}
}

