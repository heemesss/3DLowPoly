package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;

public class PatronComponent implements Component {
    public Vector3 direction;
    public int damage;
    public float timeOfLive = 5f;

    public PatronComponent(Vector3 direction, int damage) {
        this.damage = damage;
        this.direction = direction;
    }
}
