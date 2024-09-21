package com.example.testapp;

//import android.os.CountDownTimer;

public class Warrior extends MyUnit{

    int m_TempHealth = 0;

    CountDownTimerExt shieldTimer = new CountDownTimerExt(5000, 5000, false) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //if (!Unit.GamePause) {
                update5sec();
            //}
        }
    }.create();

    protected void update5sec() {
        int delta = m_TempHealth - this.health;
        if ((50 - delta) > 0) {
            this.health = this.health - (50 - delta);
        }
        super.BlockSecond();
    }

    public Warrior(String name, int health, int damage) {
        super(name, health, damage);
        this.Cooldown1 = 5000;
        this.Cooldown2 = 12000;
    }

    public void SwordSlash(Enemy enemy){
        if (!first) return;
        if (mana < 10) return;
        mana -= 10;
        enemy.health -= this.damage;
        super.BlockFirst();
    }

    public void Shield(){
        if (!second) return;
        if (mana < 25) return;
        mana -= 25;
        this.health += 50;
        m_TempHealth = this.health;
        shieldTimer.resumeForce();
        super.BlockSecond();
        //ДОЛЖНО ПРОИЗОЙТИ ЧЕРЕЗ 5 СЕКУНД
//        this.health = this.health - (50 - (m_TempHealth - this.health));
//        super.BlockSecond();
        }
    }

