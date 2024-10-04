package com.example.testapp;

//import android.os.CountDownTimer;

public class Shaman extends MyUnit{

    int poison = 0;
    Enemy enemy;
    public Shaman(String name, int health, int damage, Enemy enemy) {
        super(name, health, damage);
        this.enemy = enemy;
        this.Cooldown1 = 8000;
        this.Cooldown2 = 12000;
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
        if (!first) return;
        if (mana < 60) return;
        mana -= 60;
        enemy.health -= damage;
        poison += 2;
        super.BlockFirst();
    }

    public void VenomExtract() {
        if (!second) return;
        if (mana < 30) return;
        mana -= 40;
        poison *= 2;
        super.BlockSecond();
    }

    public void poisonDamage(){
        enemy.health -= poison;
    }
}
