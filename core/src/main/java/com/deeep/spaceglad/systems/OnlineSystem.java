package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.WiFi.MyRequest;
import com.deeep.spaceglad.WiFi.MyResponse;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.OnlineComponent;
import com.deeep.spaceglad.managers.Helpers;

public class OnlineSystem extends EntitySystem {
    private ImmutableArray<Entity> enemy;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (Entity entity : enemy) {
            OnlineComponent onlineComponent = entity.getComponent(OnlineComponent.class);
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);

            Vector3 position = new Vector3();
            if (onlineComponent.isServer) {
                MyRequest request = onlineComponent.server.getRequest();
                position.x = request.x;
                position.y = request.y;
                position.z = request.z;
            } else {
                MyResponse response = onlineComponent.client.getResponse();
                position.x = response.x;
                position.y = response.y;
                position.z = response.z;
            }

            modelComponent.instance.transform.setTranslation(position);
//            modelComponent.instance.transform.setToRotation(1, 0, 0, Helpers.camera.direction.x);
            characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        enemy = engine.getEntitiesFor(Family.all(OnlineComponent.class).get());
    }
}
