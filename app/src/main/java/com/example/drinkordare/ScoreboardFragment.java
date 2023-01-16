package com.example.drinkordare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.drinkordare.databinding.FragmentScoreboardBinding;
import java.util.ArrayList;

public class ScoreboardFragment extends Fragment {


    private FragmentScoreboardBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentScoreboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.scoreContent);
        ArrayList<Player> playerList = ((MainActivity)getActivity()).players;
        for (int i = 0; i < playerList.size(); i++) {
            LinearLayout row = new LinearLayout(getActivity());
            row.setLayoutParams(new
                    LinearLayout.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.addView(row);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(75, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,0,5, 0);
            String name = playerList.get(i).getName();
            TextView nameField = new TextView(getActivity());
            nameField.setTextAppearance(getActivity(), R.style.scoreBoardLine);

            row.addView(nameField);
            nameField.setText(name);

            String drinks = "Drinks:" + playerList.get(i).getDrinks();
            TextView drinkCount = new TextView(getActivity());
            drinkCount.setTextAppearance(getActivity(), R.style.scoreBoardLine);
            row.addView(drinkCount);
            drinkCount.setText(drinks);


            String dares = "Dares:" + playerList.get(i).getDareCount();
            TextView dareCount = new TextView(getActivity());
            dareCount.setTextAppearance(getActivity(), R.style.scoreBoardLine);
            row.addView(dareCount);
            dareCount.setText(dares);
        }


        binding.nextTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).nextPlayer();
                NavHostFragment.findNavController(ScoreboardFragment.this)
                        .navigate(R.id.action_ScoreBoardFragment_to_GameScreenFragment);
            }
        });
        binding.endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).players.clear();
                ((MainActivity)getActivity()).chosenQuestions.clear();
                NavHostFragment.findNavController(ScoreboardFragment.this)
                        .navigate(R.id.action_ScoreBoardFragment_to_MainMenuFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}