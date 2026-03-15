package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.PatronComponent;

public class PatronSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PatronComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
            PatronComponent patronComponent = entity.getComponent(PatronComponent.class);

            Vector3 position = new Vector3();
            modelComponent.instance.transform.getTranslation(position);
            position.add(patronComponent.direction.cpy().scl(5000 * deltaTime));
            modelComponent.instance.transform.setTranslation(position);

            // hitbox move
            characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);

            patronComponent.timeOfLive -= deltaTime;
            if (patronComponent.timeOfLive < 0) {
                getEngine().removeEntity(entity);
            }
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        entities = null;
    }
}
