package com.example.testapp;

public class Enemy extends Unit {

    public Enemy(String name, int health, int damage) {
        super(name, health, damage);
    }
    boolean targetWarrior;
    boolean targetWitch;
    boolean targetShaman;
    boolean skipFirstTick = false;
    CountDownTimerExt enemyAttack = new CountDownTimerExt(Integer.MAX_VALUE, 10000, true) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (skipFirstTick) {
                attackChooser();
            }
            skipFirstTick = true;
        }

        @Override
        public void onFinish() {

        }
    }.create();

    public void attackChooser(){
        //if (tester != null) {
            //if ((tester.warrior != null) && (tester.shaman != null)) {
                if ((tester.warrior.health > tester.shaman.health)
                        && (tester.warrior.health > tester.witch.health)) {
                    tester.warrior.health -= this.damage;
                } else if ((tester.witch.health > tester.shaman.health)
                        && (tester.witch.health > tester.warrior.health)) {
                    tester.witch.health -= this.damage;
                } else if ((tester.shaman.health > tester.warrior.health)
                        && (tester.shaman.health > tester.witch.health)) {
                    tester.shaman.health -= this.damage;
                }
        //}
        //}
    }
}
