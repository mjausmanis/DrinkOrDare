package com.example.drinkordare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.drinkordare.databinding.FragmentGameSetupBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GameSetupFragment extends Fragment {

    private FragmentGameSetupBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentGameSetupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String[]> questions = ((MainActivity)getActivity()).questions;
                CheckBox physical = (CheckBox) getView().findViewById(R.id.physicalChallengeCheck);
                CheckBox trivia = (CheckBox) getView().findViewById(R.id.triviaQuestCheck);
                CheckBox truth = (CheckBox) getView().findViewById(R.id.truthCheck);
                CheckBox dares = (CheckBox) getView().findViewById(R.id.dareCheck);
                CheckBox spicy = (CheckBox) getView().findViewById(R.id.spicyCheck);
                CheckBox custom = (CheckBox) getView().findViewById(R.id.customQuestCheck);

                for (int i = 0; i < questions.size(); i++) {
                    if (questions.get(i)[1].equals("physical") && physical.isChecked()) {
                        ((MainActivity) getActivity()).chosenQuestions.add(questions.get(i));
                    } else if (questions.get(i)[1].equals("trivia") && trivia.isChecked()) {
                        ((MainActivity) getActivity()).chosenQuestions.add(questions.get(i));
                    } else if (questions.get(i)[1].equals("truth") && truth.isChecked()) {
                        ((MainActivity) getActivity()).chosenQuestions.add(questions.get(i));
                    } else if (questions.get(i)[1].equals("dare") && dares.isChecked()) {
                        ((MainActivity) getActivity()).chosenQuestions.add(questions.get(i));
                    } else if (questions.get(i)[1].equals("spice") && spicy.isChecked()) {
                        ((MainActivity) getActivity()).chosenQuestions.add(questions.get(i));
                    }
                }
                if (custom.isChecked()) {
                    try {
                        File customFile = new File(getActivity().getFilesDir(), "custom.txt");
                        InputStream customInputStream = new FileInputStream(customFile);
                        BufferedReader customBr = new BufferedReader(new InputStreamReader(customInputStream));
                        String line;
                        while ((line = customBr.readLine()) != null) {
                            String[] question = {line, "custom"};
                            ((MainActivity)getActivity()).chosenQuestions.add(question);
                        }
                        customBr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                NavHostFragment.findNavController(GameSetupFragment.this)
                        .navigate(R.id.action_GameSetupFragment_to_GameScreenFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}