package com.example.drinkordare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.drinkordare.databinding.MainMenuFragBinding;

public class MainMenuFragment extends Fragment {

    private MainMenuFragBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MainMenuFragBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_PlayerInputFragment);
            }
        });
        binding.goToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_OptionsFragment);
            }
        });
        binding.goToCustQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainMenuFragment.this)
                        .navigate(R.id.action_MainMenuFragment_to_CustomQuestionsFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}