package com.example.testapp;

class Unit {
    int damage;
    String name;
    int health;
    Tester tester = null;

    static boolean GamePause = false;

    public Unit(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public void printInfo(){
        System.out.println("Name:" + name);
        System.out.println("Health:" + health);
    }

    public void setTester(Tester _tester) {
        this.tester = _tester;
    }


}