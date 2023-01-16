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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.drinkordare.databinding.FragmentCustomQuestionsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

//Scrollable būtu jānoņem, nav īsti jēga
//Varbūt pie tā pop-up ar custom q
// Savādak nevajag
public class CustomQuestionsFragment extends Fragment {
    private FragmentCustomQuestionsBinding binding;

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

        // Read the contents of "custom.txt"
        final ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(getContext().getFilesDir(), "custom.txt");
            InputStream inputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Find the LinearLayout to hold the dares
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.custQuestLayout);

        for (final String dare : lines) {
        // Create a CheckBox to select the dare
            CheckBox dareView = new CheckBox(getContext());
            dareView.setText(dare);
            dareView.setTextSize(20);
            layout.addView(dareView);
        }

        // Find the button to delete selected dares
        Button deleteButton = (Button) getView().findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // Delete the selected dares from "custom.txt"
                try {
                    File file = new File(getContext().getFilesDir(), "custom.txt");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View child = layout.getChildAt(i);
                        if (child instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox) child;
                            if (!checkBox.isChecked()) {
                                bw.write(lines.get(i));
                                bw.newLine();
                            }
                        }
                    }
                    Toast.makeText(getContext(), "Checked dares deleted", Toast.LENGTH_SHORT).show();
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                NavHostFragment.findNavController(CustomQuestionsFragment.this)
                        .navigate(R.id.action_refreshCustomQuestions);
            }
        });

        FloatingActionButton addDares = view.findViewById(R.id.addAQuestion);
        addDares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // Create an AlertDialog to display the dares
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                EditText textInput = new EditText(getContext());
                builder.setView(textInput);
                builder.setTitle("Add a dare")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String userText = textInput.getText().toString();
                                try {
                                    File file = new File(getContext().getFilesDir(), "custom.txt");
                                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                                    fileOutputStream.write((userText + "\n").getBytes());
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    textInput.setText("");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                NavHostFragment.findNavController(CustomQuestionsFragment.this)
                                        .navigate(R.id.action_refreshCustomQuestions);
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}