package com.example.drinkordare;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


import com.example.drinkordare.databinding.FragmentGameScreenBinding;

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

        //Uzliek uz questionDisplay tekstu
        String textFromFile = readTextFromFile(getContext());
        TextView questionDisplay = getView().findViewById(R.id.questionDisplay);
        questionDisplay.setText(textFromFile);

        binding.reroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFromFile = readTextFromFile(getContext());
                questionDisplay.setText(textFromFile);
            }
        });

        binding.tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameScreenFragment.this)
                        .navigate(R.id.action_GameScreenFragment_to_ScoreboardFragment);
            }
        });
    }



    //Lasa txt failu, randomly izvelas vienu liniju
    //Neprasat man kā tas strāda, man jau tā smadzenes vārās no šī
    String readTextFromFile(Context context) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("cards.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines.size() > 0) {
            Random random = new Random();
            int index = random.nextInt(lines.size());
            return lines.get(index);
        } else {
            return "File is Empty";
        }
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}