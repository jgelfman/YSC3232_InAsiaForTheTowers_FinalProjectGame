package com.example.inasiaforthetowers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    int musicCounter = 1;
    Intent bgm;
    Button singleStart;
    Button doubleStart;
    Button checkScore;
    ImageButton goToSetting;
    ImageButton musicSetting;
    private String userID = null;
    private AlertDialog.Builder dialogueBuilder;
    private AlertDialog dialog;

    //This part is for the setting pop up
    private EditText newID;
    private Button settingPopUpCancel, settingPopUpSave;
    private TextView showID;
    private Menu menu;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Start background music
        startService(new Intent(this, MusicService.class));


        //Main menu button choices
        singleStart = findViewById(R.id.startGame1);
        singleStart.setOnClickListener(this);

        doubleStart = findViewById(R.id.startGame2);
        doubleStart.setOnClickListener(this);

        checkScore = findViewById(R.id.checkScore);
        checkScore.setOnClickListener(this);

        //Setting icon button for setting ID
        goToSetting = findViewById(R.id.gameSetting);
        goToSetting.setOnClickListener(this);

        musicSetting = findViewById(R.id.musicSetting);
        musicSetting.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startGame1) {
            Intent intent = new Intent(this, PlaySingle.class);
            startActivity(intent);
        } else if (view.getId() == R.id.checkScore) {
            Intent intent = new Intent(this, HighScore.class);
            startActivity(intent);
        } else if (view.getId() == R.id.startGame2) {
            Intent intent = new Intent(this, PlayDouble.class);
            startActivity(intent);
        } else if (view.getId() == R.id.gameSetting) {
            createNewIDDialogue(this);
        } else if (view.getId() == R.id.musicSetting) {
            if (musicCounter == 1) {
                stopService(new Intent(this, MusicService.class));
                musicSetting.setBackgroundResource(R.drawable.setting_music2);
                musicCounter = 0;
            } else if (musicCounter == 0) {
                startService(new Intent(this, MusicService.class));
                musicSetting.setBackgroundResource(R.drawable.setting_music);
                musicCounter = 1;
            }
        }

    }

    @SuppressLint("SetTextI18n")
    public void createNewIDDialogue(Menu menu) {
        dialogueBuilder = new AlertDialog.Builder(this.menu);
        final View IDPopUpView = this.menu.getLayoutInflater().inflate(R.layout.popup, null);
        newID = IDPopUpView.findViewById(R.id.enterNewID);
        settingPopUpCancel = IDPopUpView.findViewById(R.id.cancelSetting);
        settingPopUpSave = IDPopUpView.findViewById(R.id.saveSetting);
        showID = IDPopUpView.findViewById(R.id.showCurrentID);
        showID.setText("ID: " + userID);

        dialogueBuilder.setView(IDPopUpView);
        dialog = dialogueBuilder.create();
        dialog.show();

        settingPopUpSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userID = newID.getText().toString();
                dialog.dismiss();
            }
        });

        settingPopUpCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    // End of setting pop up

    // Pause the music when exiting app
    @Override
    protected void onStop() {
        super.onStop();
        if (musicCounter == 1) {
            stopService(new Intent(this, MusicService.class));
        }
    }

    protected void onResume() {
        if (musicCounter == 1) {
            startService(new Intent(this, MusicService.class));
        }
        super.onResume();
    }


}