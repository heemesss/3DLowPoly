package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.TextComponent;
import com.deeep.spaceglad.managers.Helpers;

public class TextSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Family.all(TextComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        Vector3 player_pos = Helpers.camera.position.cpy();

        for (Entity entity : entities) {
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);

            Vector3 position = new Vector3();
            modelComponent.instance.transform.getTranslation(position);

            float dX = player_pos.x - position.x;
            float dZ = player_pos.z - position.z;

            float theta = (float) (Math.atan2(dX, dZ));

            Quaternion rot = new Quaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta));
            modelComponent.instance.transform.set(position, rot);
        }
    }
}
