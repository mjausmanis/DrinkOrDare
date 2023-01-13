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


        EditText textInput = view.findViewById(R.id.textInput);
        Button saveTextButton = view.findViewById(R.id.saveTextButton);
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            }
        });

        Button viewSavedDaresButton = view.findViewById(R.id.viewSavedDaresButton);
        viewSavedDaresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
// Create a LinearLayout to hold the dares
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                for (final String dare : lines) {
// Create a CheckBox to select the dare
                    CheckBox dareView = new CheckBox(getContext());
                    dareView.setText(dare);
                    dareView.setTextSize(20);
                    layout.addView(dareView);
                }
// Create a button to delete selected dares
                Button deleteButton = new Button(getContext());
                deleteButton.setText("Delete");
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
                    }
                });
// Create an AlertDialog to display the dares
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Saved Dares")
                        .setView(layout)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteButton.callOnClick();
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