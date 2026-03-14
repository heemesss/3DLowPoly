package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.managers.Helpers;

public class PlayerSystem extends EntitySystem implements EntityListener {
    private Entity player = null;

    @Override
    public void update(float deltaTime) {
        if (player == null)
            return;
        super.update(deltaTime);
        ModelComponent modelComponent = player.getComponent(ModelComponent.class);
        CharacterComponent characterComponent = player.getComponent(CharacterComponent.class);
        PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
        Vector3 position = new Vector3();
        Vector3 move = new Vector3();
        modelComponent.instance.transform.getTranslation(position);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            move.add(Helpers.camera.direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            move.add(Helpers.camera.direction.cpy().crs(Helpers.camera.up).scl(-1));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            move.sub(Helpers.camera.direction);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move.add(Helpers.camera.direction.cpy().crs(Helpers.camera.up));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && characterComponent.characterController.canJump()) {
            characterComponent.characterController.jump(new Vector3(0, 500, 0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) {
            getEngine().addEntity(EntityFactory.createPatron(position.cpy().add(Helpers.camera.direction.cpy().setLength(60)), 10, Helpers.camera.direction.cpy()));
        }

        move.y = 0;
        move.setLength(10);


        modelComponent.instance.transform.set(position.add(move), new Quaternion());
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        Helpers.camera.position.set(position);
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.one(PlayerComponent.class).get(), this);
    }

    @Override
    public void entityAdded(Entity entity) {
        if (player == null) {
            player = entity;
        }
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
