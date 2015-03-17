package com.example.stepseek;

import com.example.stepseek.view.StepSeekBar;
import com.example.stepseek.view.StepSeekBar.OnChangedListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	private StepSeekBar stepSeekBar;
	private SharedPreferences preferences;
	private Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = getPreferences(MODE_PRIVATE);
		editor = preferences.edit();

		setContentView(R.layout.activity_main);
		stepSeekBar = (StepSeekBar) findViewById(R.id.bar);
		stepSeekBar.moveTo(preferences.getInt("index", 0));
		stepSeekBar.setOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(StepSeekBar stepSeekBar, int index) {
				Toast.makeText(MainActivity.this,
						stepSeekBar.getSplitText(index), Toast.LENGTH_SHORT)
						.show();
				editor.putInt("index", index);
				editor.commit();
			}
		});
	}

}
