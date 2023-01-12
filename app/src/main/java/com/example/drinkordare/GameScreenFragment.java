package com.example.drinkordare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD:app/src/main/java/com/example/drinkordare/FirstFragment.java
public class FirstFragment extends Fragment {
<<<<<<< Updated upstream
//comment just for my dummy ass to test github :D
=======
=======
import com.example.drinkordare.databinding.FragmentGameScreenBinding;
>>>>>>> c5e7fa60db6e01adf8b8d17b0f73627cd7f9a2d0:app/src/main/java/com/example/drinkordare/GameScreenFragment.java

public class GameScreenFragment extends Fragment {

    private FragmentGameScreenBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentGameScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameScreenFragment.this)
                        .navigate(R.id.action_GameScreenFragment_to_ScoreboardFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}