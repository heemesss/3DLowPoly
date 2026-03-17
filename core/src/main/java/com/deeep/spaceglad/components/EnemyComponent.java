package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
    public float speed;
    public boolean isALife = true;
    public boolean isABoss = false;
    public int health = 100;

    public EnemyComponent(float speed) {
        this.speed = speed;
    }
}
