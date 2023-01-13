package com.example.drinkordare;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class PlayerInputFragment extends Fragment {
    int tagValue = 1;
    String[] tagArray = new String[24];
    ArrayList<Player> players = new ArrayList<Player>();

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

        binding.addPlayerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(tagValue <= 24) {

                    LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.inputLinearLayout);
                    Button button = (Button) getView().findViewById(R.id.addPlayerButton);
                    ScrollView scrollView = (ScrollView) getView().findViewById(R.id.inputScrollView);

                    EditText editText = new EditText(getActivity());
                    editText.setLayoutParams(new
                            LinearLayout.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.addView(editText);

                    String tag = "playerName" + tagValue;
                    editText.setTag(tag);
                    tagArray[tagValue-1] = tag;
                    tagValue++;

                    scrollView.scrollToDescendant(button);
                } else {
                    Toast.makeText(getActivity(), "Can't have more than 24 players", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.goToGameSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < tagArray.length; i++) {
                    if (tagArray[i] != null) {

                        EditText field = getView().findViewWithTag(tagArray[i]);
                        String name = field.getText().toString();

                        Player player = new Player(name);
                        players.add(player);

                    }
                }
                NavHostFragment.findNavController(PlayerInputFragment.this)
                        .navigate(R.id.action_PlayerInputFragment_to_GameSetupFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}