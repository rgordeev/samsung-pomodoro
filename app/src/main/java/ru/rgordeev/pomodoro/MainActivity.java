package ru.rgordeev.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isActive = false;
    private CountDownTimer countDownTimer;
    private SeekBar seekBar;
    private TextView timer;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(1800);
        seekBar.setMin(0);
        seekBar.setOnSeekBarChangeListener(new MySeekBarListner());
        start = findViewById(R.id.start);
        start.setOnClickListener(new MyButtonOnClickListener());
    }

    private class MyButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final int position = seekBar.getProgress();
            if (!isActive) {
                isActive = true;
                start.setText("Stop");
                countDownTimer = new CountDownTimer(Long.valueOf(position) * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int sec = Long.valueOf(millisUntilFinished / 1000).intValue();
                        int mm = sec / 60;
                        int ss = sec % 60;
                        String text = String.format("%02d:%02d", mm, ss);
                        timer.setText(text);
                        seekBar.setProgress(sec);
                    }

                    @Override
                    public void onFinish() {
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ring);
                        mp.start();
                    }
                };
                countDownTimer.start();
            } else {
                start.setText("Start");
                isActive = false;
                countDownTimer.cancel();
            }
        }
    }

    private class MySeekBarListner implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int mm = progress / 60;
            int ss = progress % 60;
            String text = String.format("%02d:%02d", mm, ss);
            timer.setText(text);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
