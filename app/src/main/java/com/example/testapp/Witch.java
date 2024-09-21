package com.example.testapp;

//import android.os.CountDownTimer;

public class Witch extends MyUnit{

    double DamageDebuff = 0;
    Enemy enemy;
    int tempEnemyHealth = 0;
    int manaSurgeBuff = 0;

    public Witch(String name, int health, int damage, Enemy enemy) {
        super(name, health, damage);
        this.enemy = enemy;
    }
    CountDownTimerExt debuffTimer = new CountDownTimerExt(5000, 5000, false) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //if (!Unit.GamePause) {
                weaknessWoreOff();
            //}
        }
    }.create();
    public void weaknessPotion(){
        if (mana < 35) return;
        mana -= 35;
        DamageDebuff = enemy.damage / 2;
        enemy.damage -= DamageDebuff;
        debuffTimer.resumeForce();
    }
    public void weaknessWoreOff(){
        enemy.damage += DamageDebuff;
    }



    CountDownTimerExt manaSurgeTimer = new CountDownTimerExt(5000, 100, false) {
        @Override
        public void onTick(long millisUntilFinished) {
            //if (!Unit.GamePause) {
                if (tempEnemyHealth > enemy.health) {
                    if (mana < MyUnit.MAX_MANA) {
                        manaSurgeBuff = tempEnemyHealth - enemy.health;
                        setMana();
                        getEnemyHealth();
                        manaSurgeBuff = 0;
                    }
                }
            //}
        }

        @Override
        public void onFinish() {

        }
    }.create();
    public void ManaSurge(){
        if (mana < 50) return;
        mana -= 50;
        enemy.health -= damage;
        getEnemyHealth();
        manaSurgeTimer.resumeForce();

    }
    public void getEnemyHealth(){
        tempEnemyHealth = enemy.health;
    }
    public void setMana(){mana += manaSurgeBuff; }
}
