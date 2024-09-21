package com.example.testapp;

import android.os.CountDownTimer;
//import java.util.concurrent.atomic.AtomicInteger;

public class MyUnit extends Unit{
    public static final int MAX_MANA = 200;
    public static int mana;
    //Thread thread;
    public Boolean first = true;
    public Boolean second = true;
    int Cooldown1 = 5000;
    int Cooldown2 = 5000;
    CountDownTimerExt firstTimer = null;
    CountDownTimerExt secondTimer = null;
    public void setFirst(boolean value1)
    {
        this.first = value1;
    }
    public void setSecond(boolean value2)
    {
        this.second = value2;
    }
    public static CountDownTimerExt manaTimer = new CountDownTimerExt(Integer.MAX_VALUE, 200, true) {
        @Override
        public void onTick(long millisUntilFinished) {
            //if (!Unit.GamePause) {
                increaseMana();
            //}
        }

        @Override
        public void onFinish() {

        }
    }.create();
    public void BlockFirst(){
//        new Thread(()->{
//           this.first = false;
//            try {
//                Thread.sleep(Cooldown1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            this.first = true;
//        }).start();
        setFirst(false);
        firstTimer = new CountDownTimerExt(Cooldown1, Cooldown1, true) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setFirst(true);
            }
        }.create();
    }
    public void BlockSecond(){
//        new Thread(()->{
//            this.second = false;
//            try {
//                Thread.sleep(Cooldown2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            this.second = true;
//        }).start();
        setSecond(false);
        secondTimer = new CountDownTimerExt(Cooldown2, Cooldown2, true) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                setSecond(true);
            }
        }.create();
    }
    public MyUnit(String name, int health, int damage) {
        super(name, health, damage);
        this.mana = MyUnit.MAX_MANA;
    }

    public static void increaseMana() {
        if (mana < MyUnit.MAX_MANA) {
            mana += 1;
        }
    }



    public void stopMana(){

    }
}
