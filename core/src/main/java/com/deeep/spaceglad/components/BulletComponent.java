package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class BulletComponent implements Component{
    public btCollisionObject body;

    public BulletComponent(btCollisionObject body){
        this.body = body;
    }
}
