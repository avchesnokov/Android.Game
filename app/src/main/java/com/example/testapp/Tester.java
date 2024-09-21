package com.example.testapp;

public class Tester {
    Enemy enemy;
    Warrior warrior;
    Witch witch;
    Shaman shaman;


    public Tester(Enemy enemy, Warrior warrior, Witch witch, Shaman shaman) {
        this.enemy = enemy;
        this.warrior = warrior;
        this.witch = witch;
        this.shaman = shaman;
    }
    public void fight(){

    }

    public void AttackSwitch(String func) {
        switch (func){
            case "SwordSlash":
                warrior.SwordSlash(enemy);
                break;
            case "Shield":
                warrior.Shield();
                break;
            case "ManaSurge":
                witch.ManaSurge();
                break;
        }
    }
}
