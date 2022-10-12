package com.depauw.buckettracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.depauw.buckettracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    public static final int DEFAULT_NUM_MINS = 20;
    public static final int MILLIS_PER_SECOND = 1000;
    public static final int MILLIS_PER_MIN = 60000;
    public static final int SECS_PER_MIN = 60;
    private static final int COUNTDOWN = 0;
    private CountDownTimer myTimer;
    private long pauseTime = DEFAULT_NUM_MINS * MILLIS_PER_MIN;

    private ActivityMainBinding binding;


    private View.OnClickListener toggle_is_guest_clickListener = new View.OnClickListener() {

        public void onClick(View v) {

            if (binding.toggleIsGuest.isChecked()) {
                binding.labelGuest.setTextColor(getResources().getColor(R.color.red, getTheme()));
                binding.labelHome.setTextColor(getResources().getColor(R.color.black, getTheme()));
                binding.textviewGuestScore.setTextColor(getResources().getColor(R.color.red, getTheme()));
                binding.textviewHomeScore.setTextColor(getResources().getColor(R.color.black, getTheme()));
            }

            else {
                binding.labelHome.setTextColor(getResources().getColor(R.color.red, getTheme()));
                binding.labelGuest.setTextColor(getResources().getColor(R.color.black, getTheme()));
                binding.textviewHomeScore.setTextColor(getResources().getColor(R.color.red, getTheme()));
                binding.textviewGuestScore.setTextColor(getResources().getColor(R.color.black, getTheme()));
            }
        }
    };


    private View.OnLongClickListener button_add_score_onLongClick = new View.OnLongClickListener() {


        public boolean onLongClick(View v) {


            int add = 0;
            if (binding.checkboxAddOne.isChecked()) {
                add += 1;
            }
            if (binding.checkboxAddTwo.isChecked()) {
                add += 2;
            }
            if (binding.checkboxAddThree.isChecked()) {
                add += 3;
            }


            if (binding.toggleIsGuest.isChecked()) {
                int score = Integer.valueOf(binding.textviewGuestScore.getText().toString());
                int scoreNew = score + add;
                binding.textviewGuestScore.setText(String.valueOf(scoreNew));
            }
            else {
                int score = Integer.valueOf(binding.textviewHomeScore.getText().toString());
                int scoreNew = score + add;
                binding.textviewHomeScore.setText(String.valueOf(scoreNew));
            }

            binding.checkboxAddThree.setChecked(false);
            binding.checkboxAddTwo.setChecked(false);
            binding.checkboxAddOne.setChecked(false);

            binding.toggleIsGuest.performClick();

            return true;
        }

    };


    public CountDownTimer getNewTimer(long totalLengthOfTimer, long tickLengthOfTimer) {
        return new CountDownTimer(totalLengthOfTimer,tickLengthOfTimer) {
            @Override
            public void onTick(long l)
            {
                long minValue = (l/MILLIS_PER_SECOND) / SECS_PER_MIN;
                long secValue = (l/MILLIS_PER_SECOND) % SECS_PER_MIN;
                binding.textviewTimeRemaining.setText(String.valueOf(minValue) + ":" + String.valueOf(secValue));
            }

            @Override
            public void onFinish()
            {
                binding.switchGameClock.setChecked(false);
            }
        };
    }

    private View.OnClickListener switch_game_clockClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (binding.switchGameClock.isChecked()) {
                myTimer = getNewTimer(pauseTime, MILLIS_PER_SECOND);
                myTimer.start();
            } else {
                String[] temp = binding.textviewTimeRemaining.getText().toString().split(":");
                long minsLeft = Integer.valueOf(temp[0]) * MILLIS_PER_MIN;
                long secsLeft = Integer.valueOf(temp[1]) * MILLIS_PER_SECOND;
                pauseTime = minsLeft + secsLeft;
                myTimer.cancel();
            }
            binding.toggleIsGuest.performClick();
        }
    };

    private View.OnClickListener button_set_time_ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if ((!(binding.edittextNumMins.getText().toString()).isEmpty()) && (!(binding.edittextNumSecs.getText().toString()).isEmpty()))
                {
                    String newTextMins = binding.edittextNumMins.getText().toString();
                    String newTextSecs = binding.edittextNumSecs.getText().toString();
                    int mins = Integer.valueOf(newTextMins);
                    int secs = Integer.valueOf(newTextSecs);

                if((mins >= COUNTDOWN && mins< DEFAULT_NUM_MINS) && (secs >= COUNTDOWN && secs < SECS_PER_MIN))
                {
                    binding.switchGameClock.setChecked(false);
                    if(myTimer != null)
                    {
                        myTimer.cancel();
                    }
                    pauseTime = (mins * MILLIS_PER_MIN) + (secs * MILLIS_PER_SECOND);
                    long minValue = (pauseTime/ MILLIS_PER_SECOND) / SECS_PER_MIN;
                    long secValue = (pauseTime/ MILLIS_PER_SECOND) % SECS_PER_MIN;
                    binding.textviewTimeRemaining.setText(minValue + ":" + secValue);
                }
            }
        }
    };

            @Override
            protected void onCreate (Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                binding.toggleIsGuest.setOnClickListener(toggle_is_guest_clickListener);
                binding.buttonAddScore.setOnLongClickListener(button_add_score_onLongClick);
                binding.switchGameClock.setOnClickListener(switch_game_clockClickListener);
                binding.buttonSetTime.setOnClickListener(button_set_time_ClickListener);

            }


}
