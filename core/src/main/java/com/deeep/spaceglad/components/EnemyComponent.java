package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
    public float speed;

    public EnemyComponent(float speed) {
        this.speed = speed;
    }
}
