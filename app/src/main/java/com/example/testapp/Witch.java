package com.example.testapp;

//import android.os.CountDownTimer;

public class Witch extends MyUnit{

    double DamageDebuff = 0;
    Enemy enemy;
    int tempEnemyHealth1 = 0;
    int manaSurgeBuff = 0;

    public Witch(String name, int health, int damage, Enemy enemy) {
        super(name, health, damage);
        this.enemy = enemy;
        this.Cooldown1 = 15000;
        this.Cooldown2 = 12000;
    }

    CountDownTimerExt manaSurgeTimer = new CountDownTimerExt(5000, 50, false) {
        @Override
        public void onTick(long millisUntilFinished) {
            //if (!Unit.GamePause) {
            if (tempEnemyHealth1 > enemy.health) {
                if (mana < MyUnit.MAX_MANA) {
                    manaSurgeBuff = tempEnemyHealth1 - enemy.health;
                    if (mana + manaSurgeBuff >= MyUnit.MAX_MANA){
                        mana = MyUnit.MAX_MANA;
                    }
                    else {
                        setMana();
                    }
                    getEnemyHealth();
                    manaSurgeBuff = 0;
                }
            }
            //}
        }
        @Override
        public void onFinish() {

        }
    };

    public void ManaSurge(){
        if (!first) return;
        if (mana < 50) return;
        mana -= 50;
        enemy.health -= damage;
        getEnemyHealth();
        manaSurgeTimer.create();
        manaSurgeTimer.resumeForce();
        super.BlockFirst();

    }


    public void WeaknessPotion(){
        if (!second) return;
        if (mana < 35) return;
        mana -= 35;
        DamageDebuff = enemy.damage / 2;
        enemy.damage -= DamageDebuff;
        debuffTimer.create();
        debuffTimer.resumeForce();
        super.BlockSecond();
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
    };

    public void weaknessWoreOff(){
        enemy.damage += DamageDebuff;
    }




    public void getEnemyHealth(){
        tempEnemyHealth1 = enemy.health;
    }
    public void setMana(){mana += manaSurgeBuff; }
}
