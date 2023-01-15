package com.example.drinkordare;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.drinkordare.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    int currentPlayer = 0;
    ArrayList<Player> players = new ArrayList<Player>();

    public void nextPlayer() {
        this.currentPlayer ++;
        if (this.currentPlayer >= this.players.size()) {
            this.currentPlayer = 0;
        }
    }
    private static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Pārkopēt cards no assets uz internal storage startup brīdī
        copyAssetToInternalStorage(this, "cards.txt", "preset.txt");
        //Music

        mediaPlayer = MediaPlayer.create(this, R.raw.maukusencis);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1f, 1f);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }



    //Loads preset dares on app startup.
    private void copyAssetToInternalStorage(Context context, String assetName, String fileName) {
        try {
            InputStream is = context.getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}