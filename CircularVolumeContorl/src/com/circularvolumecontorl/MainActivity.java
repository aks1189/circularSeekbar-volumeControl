package com.circularvolumecontorl;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.circularvolumecontrol.utilities.CircularSeekBar;
import com.circularvolumecontrol.utilities.CircularSeekBar.OnCircularSeekBarChangeListener;

public class MainActivity extends Activity {

	private CircularSeekBar circularSeekBar;
	private AudioManager audio;
	private int currentVolume;
	private int maxVolume;
	private int progressDegree;
	private int currentProgress;
	// Media Player
	private MediaPlayer mpPlaySound;

	// Sound Id's
	private int sadPiano = R.raw.sad_piano;

	private boolean hardKeyPressed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		circularSeekBar = (CircularSeekBar) findViewById(R.id.circularSeekBar1);
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		// Log.d("MAX VOL", "" + maxVolume);
		// Log.d("CURRENT VOL", "" + currentVolume);

		progressDegree = (360 / maxVolume);
		// Log.d("PROGRESS DEGREE", "" + progressDegree);

		currentProgress = circularSeekBar.getProgress();
		circularSeekBar.setMax(360);
		circularSeekBar.setProgress(progressDegree * currentVolume);
		mpPlaySound = MediaPlayer.create(this, sadPiano);
		mpPlaySound.setLooping(true);
		mpPlaySound.start();

		circularSeekBar
				.setOnSeekBarChangeListener(new OnCircularSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(CircularSeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStartTrackingTouch(CircularSeekBar seekBar) {

					}

					@Override
					public void onProgressChanged(
							CircularSeekBar circularSeekBar, int progress,
							boolean fromUser) {
						if (!hardKeyPressed) {
							currentProgress = circularSeekBar.getProgress();
							currentVolume = audio
									.getStreamVolume(AudioManager.STREAM_MUSIC);
							// audio.setStreamVolume(AudioManager.STREAM_RING,
							// progress, AudioManager.FLAG_SHOW_UI +
							// AudioManager.FLAG_PLAY_SOUND);
							int setVolume;
							if (progress > 0) {

								setVolume = round(progress) / maxVolume;
							} else {
								setVolume = 0;
							}
							if (currentVolume * progressDegree == 360) {
								circularSeekBar.setEnabled(false);
							} else {
								circularSeekBar.setEnabled(false);
							}
							// Log.d("Progress", "" + round(progress));
							audio.setStreamVolume(AudioManager.STREAM_MUSIC,
									setVolume, 0);

							Log.d("CURRENT VOL", "" + currentVolume);
							Log.d("SET VOL", "" + setVolume);
						}
						hardKeyPressed = false;
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, 0);
			// Log.d("VOLUME UP", "KEY PRESSED UP");
			currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
			if (currentVolume * progressDegree == 360) {
				circularSeekBar.setEnabled(false);
			}
			circularSeekBar.setProgress(currentVolume * progressDegree);
			Log.d("CURRENT VOL", "" + currentVolume);
			hardKeyPressed = true;
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, 0);
			// circularSeekBar.setProgress(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
			currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
			if (currentVolume * progressDegree == 0) {
				circularSeekBar.setEnabled(false);
			}
			circularSeekBar.setProgress(currentVolume * progressDegree);

			// Log.d("VOLUME DOWN", "KEY PRESSED DOWN");
			Log.d("CURRENT VOL", "" + currentVolume);
			hardKeyPressed = true;
			return true;
		default:
			return false;
		}
	}

	public int round(int val) {
		int x = val;
		for (int i = 0; i < (progressDegree - 1); i++) {
			if (x % progressDegree == 0)
				break;
			else
				x++;
		}
		Log.d("ROUND", "" + x);
		return x;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("ON PAUSE", "On Pause");
		if (mpPlaySound.isPlaying()) {
			mpPlaySound.stop();
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mpPlaySound.stop();
		Log.d("ON STOP", "On Stop");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d("ON RESTART", "On Restart");
		mpPlaySound.setLooping(true);
		mpPlaySound.start();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("ON RESUME", "On Resume");
		mpPlaySound.setLooping(true);
		mpPlaySound.start();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("ON START", "On Start");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("ON DESTROY", "On Destroy");

	}
}
