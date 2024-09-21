package com.example.testapp;

//import android.os.CountDownTimer;

public class Shaman extends MyUnit{

    int poison = 0;
    Enemy enemy;
    public Shaman(String name, int health, int damage, Enemy enemy) {
        super(name, health, damage);
        this.enemy = enemy;
    }

    CountDownTimerExt poisonDamageTimer = new CountDownTimerExt(Integer.MAX_VALUE, 1000, true) {
        @Override
        public void onTick(long millisUntilFinished) {

            //if (!Unit.GamePause) {
                poisonDamage();
            //}
        }

        @Override
        public void onFinish() {

        }
    }.create();
    public void PoisonArrow() {
        if (mana < 60) return;
        mana -= 60;
        enemy.health -= damage;
        poison += 2;
    }

    public void poisonDamage(){
        enemy.health -= poison;
    }
}
