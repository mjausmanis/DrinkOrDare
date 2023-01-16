package com.example.drinkordare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.drinkordare.databinding.PlayerInputFragBinding;

public class PlayerInputFragment extends Fragment {
    int tagValue = 1;
    String[] tagArray = new String[24];

    private PlayerInputFragBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PlayerInputFragBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Button that adds an input field
        binding.addPlayerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(tagValue <= 24) {
                    //Get fields
                    LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.inputLinearLayout);
                    ScrollView scrollView = (ScrollView) getView().findViewById(R.id.scoreBoardScrollView);
                    Button button = (Button) getView().findViewById(R.id.addPlayerButton);

                    //Create a new text input field when button is pressed
                    EditText editText = new EditText(getActivity());
                    editText.setLayoutParams(new
                            LinearLayout.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.addView(editText);

                    //Tag the field for identification, add it to an array.
                    String tag = "playerName" + tagValue;
                    editText.setTag(tag);
                    tagArray[tagValue-1] = tag;
                    tagValue++;

                } else {
                    Toast.makeText(getActivity(), "Can't have more than 24 players", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button the goes to the next screen
        binding.goToGameSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Go through all the input fields, create player objects and to shared list
                for (int i = 0; i < tagArray.length; i++) {
                    if (tagArray[i] != null) {

                        EditText field = getView().findViewWithTag(tagArray[i]);
                        String name = field.getText().toString().trim();

                        if (!name.equals("")) {
                            Player player = new Player(name);
                            ((MainActivity)getActivity()).players.add(player);

                        }
                    }
                }
                if (((MainActivity)getActivity()).players.size() > 0) {
                    NavHostFragment.findNavController(PlayerInputFragment.this)
                        .navigate(R.id.action_PlayerInputFragment_to_GameSetupFragment);
                } else {
                    Toast.makeText(getActivity(), "Need at least 1 player", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}