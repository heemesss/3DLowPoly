package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;

public class CharacterComponent implements Component {
    public btPairCachingGhostObject ghostObject;
    public btConvexShape ghostShape;
    public btKinematicCharacterController characterController;

    public CharacterComponent(btPairCachingGhostObject ghostObject,
                              btConvexShape ghostShape,
                              btKinematicCharacterController characterController){
        this.ghostObject = ghostObject;
        this.ghostShape = ghostShape;
        this.characterController = characterController;
    }
}
