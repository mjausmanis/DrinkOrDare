package com.example.drinkordare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


import com.example.drinkordare.databinding.FragmentGameScreenBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        String textFromFile = null;
        textFromFile = showQuestion(getContext());
        TextView questionDisplay = getView().findViewById(R.id.questionDisplay);
        questionDisplay.setText(textFromFile);

        String playerName = ((MainActivity)getActivity()).players.get(((MainActivity)getActivity()).currentPlayer).name;
        String displayText = playerName + "'s turn";
        TextView playerDisplay = getView().findViewById(R.id.playerDisplay);
        playerDisplay.setText(displayText);

        binding.reroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFromFile = null;
                textFromFile = showQuestion(getContext());
                questionDisplay.setText(textFromFile);
            }
        });

        binding.endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("What happened?")
                        .setPositiveButton("Did the dare", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                ((MainActivity)getActivity()).players.get(((MainActivity)getActivity()).currentPlayer).addDareDone();
                                NavHostFragment.findNavController(GameScreenFragment.this)
                                        .navigate(R.id.action_GameScreenFragment_to_ScoreboardFragment);
                            }
                        })
                        .setNeutralButton("Took a drink", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                builder2.setTitle("What's the drink?")
                                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();

                                                //Have to add the drink selection


                                                ((MainActivity)getActivity()).players.get(((MainActivity)getActivity()).currentPlayer).addDrinks(1);
                                                NavHostFragment.findNavController(GameScreenFragment.this)
                                                        .navigate(R.id.action_GameScreenFragment_to_ScoreboardFragment);
                                            }
                                        })
                                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                AlertDialog dialog2 = builder2.create();
                                dialog2.show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    String showQuestion(Context context) {
        ArrayList<String[]> questions = ((MainActivity)getActivity()).chosenQuestions;

        Random random = new Random();
        int index = random.nextInt(questions.size());
        return questions.get(index)[0];
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}