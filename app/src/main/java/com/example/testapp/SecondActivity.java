package com.example.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.Visibility;
import android.os.Bundle;
//import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    boolean menu = false;
    Tester tester = null;
    boolean warriorAttacks = false;
    boolean warrior_check = true;
    boolean archer_check = true;
    boolean witch_check = true;
    boolean enemy_check = true;



    Button menu_main ;
    Button settings ;
    ImageView character2 ;
    Button character3 ;
    ImageView SwordSlash ;
    TextView SwordSlashText ;
    ImageView shield ;
    TextView shieldText ;
    Button attack3 ;
    Button attack4 ;
    Button attack5 ;
    Button attack6 ;
    ImageView Warrior ;
    ProgressBar manaBar ;
    ProgressBar warriorHp ;
    ProgressBar witchHp;
    ProgressBar shamanHp ;
    ProgressBar enemyHp ;
    TextView manaCounter ;
    CountDownTimerExt gameTimer;
    ImageView backgroundMenu ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Enemy enemy = new Enemy("Death Ward",900,60);
        tester = new Tester(enemy,
                new Warrior("Warrior", 210, 35),
                new Witch("Witch", 175, 40, enemy),
                new Shaman("Shaman", 185, 55, enemy));
        enemy.setTester(tester);
        gameTimer = new CountDownTimerExt(Integer.MAX_VALUE, 100, true) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                    manaBar.setProgress(MyUnit.mana);
                    manaCounter.setText(MyUnit.mana + " mana");

                    shamanHp.setProgress(tester.shaman.health);
                    warriorHp.setProgress(tester.warrior.health);
                    witchHp.setProgress(tester.witch.health);
                    enemyHp.setProgress(tester.enemy.health);

                    warrior_check = warriorAlive();
                    archer_check = archerAlive();
                    witch_check = witchAlive();
                    enemy_check = enemyAlive();

                    if (!enemy_check || !(warrior_check || archer_check || witch_check)) {
                        gameOver();
                        settings.setEnabled(false);
                    }
            }

            @Override
            public void onFinish() {

            }
        }.create();

        manaCounter = findViewById(R.id.manaCounter);
        menu_main = findViewById(R.id.btnOpenMain);
        settings = findViewById(R.id.settings);
        character2 = findViewById(R.id.character2);
        character3 = findViewById(R.id.character3);
        SwordSlash = findViewById(R.id.SwordSlash);
        SwordSlashText = findViewById(R.id.SwordSlashText);
        shield = findViewById(R.id.shield);
        shieldText = findViewById(R.id.shieldText);
        attack3 = findViewById(R.id.attack3);
        attack4 = findViewById(R.id.attack4);
        attack5 = findViewById(R.id.attack5);
        attack6 = findViewById(R.id.attack6);
        Warrior = findViewById(R.id.Warrior);
        manaBar = findViewById(R.id.manaBar);
        manaBar.setMax(MyUnit.MAX_MANA);
        warriorHp = findViewById(R.id.warriorHp);
        warriorHp.setMax(210);
        witchHp = findViewById(R.id.witchHp);
        witchHp.setMax(175);
        shamanHp = findViewById(R.id.shamanHp);
        shamanHp.setMax(185);
        enemyHp = findViewById(R.id.enemyHp);
        enemyHp.setMax(900);
        backgroundMenu = findViewById(R.id.backgroundMenu);
        backgroundMenu.setAlpha(0.3f);




        settings.setOnClickListener(v -> {
            menu = !menu;
            if (menu) {
                gameOver();
                menu_main.setVisibility(View.VISIBLE);
            } else {
                gameResumed();
                menu_main.setVisibility(View.GONE);
            }
            Warrior.setClickable(!menu);
        });



        Warrior.setOnClickListener(v -> {
           warriorAttacks = !warriorAttacks;
           if (warriorAttacks) {
               SwordSlashText.setVisibility(View.VISIBLE);
               SwordSlash.setVisibility(View.VISIBLE);
               shield.setVisibility(View.VISIBLE);
               shieldText.setVisibility(View.VISIBLE);
           }
           else {
               SwordSlash.setVisibility(View.GONE);
               shield.setVisibility(View.GONE);
               SwordSlashText.setVisibility(View.GONE);
               SwordSlash.setVisibility(View.GONE);
               shieldText.setVisibility(View.GONE);
           }
        });
        menu_main.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });

        if (warrior_check) {
            SwordSlash.setOnClickListener(v -> tester.AttackSwitch("SwordSlash"));
            shield.setOnClickListener(v -> tester.AttackSwitch("Shield"));
        }

        if (witch_check) {
            attack3.setOnClickListener(v -> tester.AttackSwitch("ManaSurge"));
        }


    }
    public boolean warriorAlive(){
        return tester.warrior.health > 0;
    }
    public boolean archerAlive(){
        return tester.shaman.health > 0;
    }
    public boolean witchAlive(){
        return tester.witch.health > 0;
    }
    public boolean enemyAlive(){
        return tester.enemy.health > 0;
    }
    public void gameOver(){
        Warrior.setEnabled(false);
        SwordSlash.setEnabled(false);
        shield.setEnabled(false);
        character2.setEnabled(false);
        character3.setEnabled(false);
        backgroundMenu.setVisibility(View.VISIBLE);
        tester.witch.manaSurgeTimer.pause();
        tester.witch.debuffTimer.pause();
        tester.warrior.shieldTimer.pause();
        tester.shaman.poisonDamageTimer.pause();
        MyUnit.manaTimer.pause();
    }
    public void gameResumed(){
        Warrior.setEnabled(true);
        SwordSlash.setEnabled(true);
        shield.setEnabled(true);
        character2.setEnabled(true);
        character3.setEnabled(true);
        backgroundMenu.setVisibility(View.GONE);
        tester.witch.manaSurgeTimer.resume();
        tester.witch.debuffTimer.resume();
        tester.warrior.shieldTimer.resume();
        tester.shaman.poisonDamageTimer.resume();
        MyUnit.manaTimer.resume();
    }
}