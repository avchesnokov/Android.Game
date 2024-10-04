package com.example.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
    boolean witchAttacks = false;
    boolean shamanAttacks = false;
    boolean warrior_check = true;
    boolean shaman_check = true;
    boolean witch_check = true;
    boolean enemy_check = true;


    ImageView ManaSurge;
    Button menu_main;
    Button settings;
    ImageView Witch;
    ImageView Shaman;
    ImageView SwordSlash;
    TextView SwordSlashText;
    ImageView shield;
    TextView shieldText;
    ImageView WeaknessPotion;
    TextView WeaknessPotionText;
    TextView ManaSurgeText;
    ImageView PoisonArrow;
    TextView PoisonArrowText;
    ImageView VenomExtract;
    TextView VenomExtractText;
    Button attack6;
    ImageView Warrior;
    ProgressBar manaBar;
    ProgressBar warriorHp;
    ProgressBar witchHp;
    ProgressBar shamanHp;
    ProgressBar enemyHp;
    TextView manaCounter;
    CountDownTimerExt gameTimer;
    ImageView backgroundMenu;
    ImageView SwordSlashCooldown;
    TextView SwordSlashCooldownText;
    ImageView YouWin;
    ImageView YouLose;
    ImageView Paused;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_field);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Enemy enemy = new Enemy("Death Ward",900,60);
        tester = new Tester(enemy,
                new Warrior("Warrior", 255, 35),
                new Witch("Witch", 175, 40, enemy),
                new Shaman("Shaman", 200, 55, enemy));
        enemy.setTester(tester);
        gameTimer = new CountDownTimerExt(Integer.MAX_VALUE, 50, true) {

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
                shaman_check = shamanAlive();
                witch_check = witchAlive();
                enemy_check = enemyAlive();

                    if (!warrior_check) {
                        Warrior.setEnabled(false);
                        SwordSlash.setVisibility(View.GONE);
                        SwordSlashText.setVisibility(View.GONE);
                        shield.setVisibility(View.GONE);
                        shieldText.setVisibility(View.GONE);
                    }

                    if (!witch_check) {
                        Witch.setEnabled(false);
                        ManaSurge.setVisibility(View.GONE);
                        ManaSurgeText.setVisibility(View.GONE);
                        WeaknessPotion.setVisibility(View.GONE);
                        WeaknessPotionText.setVisibility(View.GONE);
                    }

                    if (!shaman_check) {
                        Shaman.setEnabled(false);
                        PoisonArrow.setVisibility(View.GONE);
                        PoisonArrowText.setVisibility(View.GONE);
                        VenomExtract.setVisibility(View.GONE);
                        VenomExtractText.setVisibility(View.GONE);
                    }

                    if (!enemy_check) {
                        gameOver();
                        settings.setEnabled(false);
                        YouWin.setVisibility(View.VISIBLE);
                    }
                    if (!(warrior_check || shaman_check || witch_check)) {
                        gameOver();
                        settings.setEnabled(false);
                        YouLose.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onFinish() {

            }
        }.create();
        YouWin = findViewById(R.id.YouWin);
        YouLose = findViewById(R.id.YouLose);
        Paused = findViewById(R.id.Paused);
        manaCounter = findViewById(R.id.manaCounter);
        menu_main = findViewById(R.id.btnOpenMain);
        settings = findViewById(R.id.settings);
        Witch = findViewById(R.id.Witch);
        Shaman = findViewById(R.id.Shaman);
        SwordSlash = findViewById(R.id.SwordSlash);
        SwordSlashText = findViewById(R.id.SwordSlashText);
        shield = findViewById(R.id.shield);
        shieldText = findViewById(R.id.shieldText);
        ManaSurge = findViewById(R.id.ManaSurge);
        ManaSurgeText = findViewById(R.id.ManaSurgeText);
        WeaknessPotion = findViewById(R.id.WeaknessPotion);
        WeaknessPotionText = findViewById(R.id.WeaknessPotionText);
        PoisonArrow = findViewById(R.id.PoisonArrow);
        VenomExtract = findViewById(R.id.VenomExtract);
        PoisonArrowText = findViewById(R.id.PoisonArrowText);
        VenomExtract = findViewById(R.id.VenomExtract);
        VenomExtractText = findViewById(R.id.VenomExtractText);
        Warrior = findViewById(R.id.Warrior);
        manaBar = findViewById(R.id.manaBar);
        manaBar.setMax(MyUnit.MAX_MANA);
        warriorHp = findViewById(R.id.warriorHp);
        warriorHp.setMax(255);
        witchHp = findViewById(R.id.witchHp);
        witchHp.setMax(175);
        shamanHp = findViewById(R.id.shamanHp);
        shamanHp.setMax(200);
        enemyHp = findViewById(R.id.enemyHp);
        enemyHp.setMax(900);
        backgroundMenu = findViewById(R.id.backgroundMenu);
        SwordSlashCooldown = findViewById(R.id.SwordSlashCooldown);
        SwordSlashCooldownText = findViewById(R.id.SwordSlashCooldownText);
        backgroundMenu.setAlpha(0.4f);


        settings.setOnClickListener(v -> {
            menu = !menu;
            if (menu) {
                gameOver();
                Paused.setVisibility(View.VISIBLE);
            } else {
                gameResumed();
                Paused.setVisibility(View.GONE);
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
               shieldText.setVisibility(View.GONE);
           }
        });

        Witch.setOnClickListener(v -> {
            witchAttacks = !witchAttacks;
            if (witchAttacks) {
                ManaSurge.setVisibility(View.VISIBLE);
                ManaSurgeText.setVisibility(View.VISIBLE);
                WeaknessPotion.setVisibility(View.VISIBLE);
                WeaknessPotionText.setVisibility(View.VISIBLE);
            }
            else {
                ManaSurge.setVisibility(View.GONE);
                WeaknessPotion.setVisibility(View.GONE);
                ManaSurgeText.setVisibility(View.GONE);
                WeaknessPotionText.setVisibility(View.GONE);
            }
        });

        Shaman.setOnClickListener(v -> {
            shamanAttacks = !shamanAttacks;
            if (shamanAttacks) {
                PoisonArrow.setVisibility(View.VISIBLE);
                PoisonArrowText.setVisibility(View.VISIBLE);
                VenomExtract.setVisibility(View.VISIBLE);
                VenomExtractText.setVisibility(View.VISIBLE);
            }
            else {
                PoisonArrow.setVisibility(View.GONE);
                PoisonArrowText.setVisibility(View.GONE);
                VenomExtract.setVisibility(View.GONE);
                VenomExtractText.setVisibility(View.GONE);
            }
        });

        menu_main.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
        });

        SwordSlash.setOnClickListener(v -> tester.AttackSwitch("SwordSlash"));
        shield.setOnClickListener(v -> tester.AttackSwitch("Shield"));

        ManaSurge.setOnClickListener(v -> tester.AttackSwitch("ManaSurge"));
        WeaknessPotion.setOnClickListener(v -> tester.AttackSwitch("WeaknessPotion"));

        PoisonArrow.setOnClickListener(v -> tester.AttackSwitch("PoisonArrow"));
        VenomExtract.setOnClickListener(v -> tester.AttackSwitch("VenomExtract"));



    }
    public boolean warriorAlive(){
        return tester.warrior.health > 0;
    }
    public boolean shamanAlive(){
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
        Witch.setEnabled(false);
        ManaSurge.setEnabled(false);
        WeaknessPotion.setEnabled(false);
        Shaman.setEnabled(false);
        VenomExtract.setEnabled(false);
        PoisonArrow.setEnabled(false);
        backgroundMenu.setVisibility(View.VISIBLE);
        tester.witch.manaSurgeTimer.pause();
        tester.witch.debuffTimer.pause();
        tester.warrior.shieldTimer.pause();
        tester.shaman.poisonDamageTimer.pause();
        tester.enemy.skipFirstTick = false;
        tester.enemy.enemyAttack.pause();
        menu_main.setVisibility(View.VISIBLE);
        MyUnit.manaTimer.pause();
    }
    public void gameResumed(){
        Warrior.setEnabled(true);
        SwordSlash.setEnabled(true);
        shield.setEnabled(true);
        Witch.setEnabled(true);
        ManaSurge.setEnabled(true);
        WeaknessPotion.setEnabled(true);
        Shaman.setEnabled(true);
        VenomExtract.setEnabled(true);
        PoisonArrow.setEnabled(true);
        backgroundMenu.setVisibility(View.GONE);
        tester.witch.manaSurgeTimer.resume();
        tester.witch.debuffTimer.resume();
        tester.warrior.shieldTimer.resume();
        tester.shaman.poisonDamageTimer.resume();

        tester.enemy.enemyAttack.resume();

        menu_main.setVisibility(View.GONE);
        MyUnit.manaTimer.resume();
    }
}