package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.managers.Helpers;

public class EnemySystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private float timeToSpawn = 5f;
    private float timeOfSpawn = 0f;

    @Override
    public void update(float deltaTime) {
        Vector3 player_pos = Helpers.camera.position.cpy();

        for (Entity entity : entities) {
            EnemyComponent enemyComponent = entity.getComponent(EnemyComponent.class);
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);

            enemyComponent.speed += 1;
            if (enemyComponent.speed > 150)
                enemyComponent.speed = 150;

            Vector3 position = new Vector3();
            modelComponent.instance.transform.getTranslation(position);

            float dX = player_pos.x - position.x;
            float dZ = player_pos.z - position.z;

            float theta = (float) (Math.atan2(dX, dZ));

            Quaternion rot = new Quaternion().setFromAxis(0, 1, 0, (float) Math.toDegrees(theta) + 90);
            modelComponent.instance.transform.set(position, rot);

            // move
            Vector3 direction = new Vector3(-1, 0, 0).rot(modelComponent.instance.transform);
            Vector3 walk_direction = new Vector3(0, 0, 0).add(direction).scl(deltaTime * enemyComponent.speed);
            characterComponent.characterController.setWalkDirection(walk_direction);

            // hitbox move
            characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);

            if (position.y < -1000 || !enemyComponent.isALife) {
                getEngine().removeEntity(entity);
                getEngine().addEntity(EntityFactory.spawnEnemy());
            }

        }

        timeOfSpawn += deltaTime;
        if (timeOfSpawn > timeToSpawn && entities.size() < 20) {
            getEngine().addEntity(EntityFactory.spawnEnemy());
            timeOfSpawn = 0;
            timeToSpawn -= 0.5f;
            if (timeToSpawn < 1) {
                timeToSpawn = 1;
            }
        }
    }

    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(EnemyComponent.class).get());
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        entities = null;
    }
}
