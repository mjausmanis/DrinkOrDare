package com.example.drinkordare;

import static com.example.drinkordare.MainActivity.mediaPlayer;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.drinkordare.databinding.FragmentOptionsBinding;


public class OptionsFragment extends Fragment {

    private FragmentOptionsBinding binding;
    static boolean isMusicPlaying = true;
    boolean darkModeOn  = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Switch switch1 = getView().findViewById(R.id.darkModeSwitch);
        int nightModeFlags =
                getContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                switch1.setChecked(true);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                switch1.setChecked(false);
                break;
        }
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    darkModeOn = true;
                    getActivity().recreate();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    darkModeOn = false;
                    getActivity().recreate();
                }
            }
        });



        Switch switch2 = getView().findViewById(R.id.musicSwitch);
        if (isMusicPlaying) {
            switch2.setChecked(true);
        } else {
            switch2.setChecked(false);
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // start music
                    if(!mediaPlayer.isPlaying())
                        mediaPlayer.start();
                } else {
                    // stop music
                    if(mediaPlayer.isPlaying())
                        mediaPlayer.pause();
                }
            }
        });









        binding.goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OptionsFragment.this)
                        .navigate(R.id.action_OptionsFragment_to_MainMenuFragment);
            }
        });
    }
}