package com.example.drinkordare;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.drinkordare.databinding.ActivityMainBinding;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    int currentPlayer = 0;
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<String[]> questions = new ArrayList<String[]>();
    ArrayList<String[]> chosenQuestions = new ArrayList<String[]>();

    public void nextPlayer() {
        this.currentPlayer ++;
        if (this.currentPlayer >= this.players.size()) {
            this.currentPlayer = 0;
        }
    }
    static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("cards.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] question = new String[2];
                question = line.split(";");
                this.questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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


    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isMusicPlaying", OptionsFragment.isMusicPlaying);
    }
}