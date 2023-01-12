package com.example.drinkordare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.drinkordare.databinding.FragmentCustomQuestionsBinding;

import java.util.ArrayList;

//Scrollable būtu jānoņem, nav īsti jēga
//Varbūt pie tā pop-up ar custom q
// Savādak nevajag
public class CustomQuestionsFragment extends Fragment {
    private FragmentCustomQuestionsBinding binding;
    //šis ir tas array kur glabājas custom questions - to būs jāizmanto klāt pie main game.
    private ArrayList<String> textList = new ArrayList<>();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCustomQuestionsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CustomQuestionsFragment.this)
                        .navigate(R.id.action_CustomQuestionsFragment_to_MainMenuFragment);
            }
        });


        EditText textInput = view.findViewById(R.id.textInput);
        Button saveTextButton = view.findViewById(R.id.saveTextButton);
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textList.add(textInput.getText().toString());
                textInput.setText("");
            }
        });

        Button showTextButton = view.findViewById(R.id.showTextButton);
        showTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText();
            }
        });
    }

    //Vēl jāpievieno question noņemšana, to vēlāk.
    private void showText() {
        StringBuilder textBuilder = new StringBuilder();
        for (String text : textList) {
            textBuilder.append(text).append("\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Custom questions you have saved")
                .setMessage(textBuilder.toString())
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}