package com.deeep.spaceglad.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.JsonReader;
import com.deeep.spaceglad.WiFi.MyClient;
import com.deeep.spaceglad.WiFi.MyRequest;
import com.deeep.spaceglad.WiFi.MyResponse;
import com.deeep.spaceglad.WiFi.MyServer;
import com.deeep.spaceglad.bullet.MotionState;
import com.deeep.spaceglad.components.BulletComponent;
import com.deeep.spaceglad.components.ButtonComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.MoneyComponent;
import com.deeep.spaceglad.components.OnlineComponent;
import com.deeep.spaceglad.components.PatronComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.components.TextComponent;

public class EntityFactory {
    public static Entity loadScene(int x, int y, int z, String name) {
        Entity entity = new Entity();
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/" + name + ".g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);
        MotionState motionState = new MotionState(modelComponent.instance.transform);
        btRigidBody.btRigidBodyConstructionInfo bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, motionState, shape, Vector3.Zero);
        btRigidBody body = new btRigidBody(bodyInfo);
        body.userData = entity;
        BulletComponent bulletComponent = new BulletComponent(body);
        entity.add(bulletComponent);
        return entity;
    }

    public static Entity loadObject(int x, int y, int z, String name) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/" + name + ".g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(40, 40, 40));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(modelComponent.instance.transform);
        ghostObject.setCollisionShape(shape);
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setGravity(new Vector3(0, -1000, 0));
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        return entity;
    }

    public static Entity createPlayer(int x, int y, int z) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/hero.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(40, 100, 40));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(modelComponent.instance.transform);
        ghostObject.setCollisionShape(shape);
        ghostObject.userData = entity;
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setGravity(new Vector3(0, -1000, 0));
        characterController.setFallSpeed(10*50);
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        PlayerComponent playerComponent = new PlayerComponent();
        entity.add(playerComponent);

        return entity;
    }

    public static Entity createEnemy(int x, int y, int z){
        Entity entity = new Entity();
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/ogr.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(40, 150, 40));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(modelComponent.instance.transform);
        ghostObject.setCollisionShape(shape);
        ghostObject.userData = entity;
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setGravity(new Vector3(0, -1000, 0));
        characterController.setFallSpeed(10*50);
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        EnemyComponent enemyComponent = new EnemyComponent(MathUtils.random(100, 120));
        entity.add(enemyComponent);

        return entity;
    }

    public static Entity createPatron(Vector3 position, int damage, Vector3 direction) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/bullet.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, position.x, position.y, position.z);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(10, 10, 10));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(new Matrix4().setToTranslation(position));
        ghostObject.setCollisionShape(shape);
        ghostObject.userData = entity;
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setFallSpeed(0);
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        PatronComponent patronComponent = new PatronComponent(direction, damage);
        entity.add(patronComponent);

        return entity;
    }

    public static Entity spawnEnemy(int x1, int x2, int y1, int y2, int z){

        return createEnemy(MathUtils.random(x1, x2), z, MathUtils.random(y1, y2));
    }

    public static Entity createButton(int x, int y, int z, int type, boolean isActive){
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/button.g3dj"));
        modelData.materials.first().diffuse.set(isActive ? Color.GREEN : Color.RED);
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);
        MotionState motionState = new MotionState(modelComponent.instance.transform);
        btRigidBody.btRigidBodyConstructionInfo bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, motionState, shape, Vector3.Zero);
        btRigidBody body = new btRigidBody(bodyInfo);
        body.userData = entity;
        BulletComponent bulletComponent = new BulletComponent(body);
        entity.add(bulletComponent);

        ButtonComponent buttonComponent = new ButtonComponent(type, isActive);
        entity.add(buttonComponent);

        return entity;
    }

    public static Entity createText(int x, int y, int z, String name) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/level.g3dj"));
        modelData.materials.first().textures.first().fileName = "Models/" + name;
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        TextComponent textComponent = new TextComponent();
        entity.add(textComponent);

        return entity;
    }

    public static Entity createBossEnemy(int x, int y, int z){
        Entity entity = new Entity();
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/ogr.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        for (Node node : model.nodes) {
            node.scale.scl(10);
        }
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(400, 1500, 400));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(modelComponent.instance.transform);
        ghostObject.setCollisionShape(shape);
        ghostObject.userData = entity;
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setGravity(new Vector3(0, -1000, 0));
        characterController.setFallSpeed(10*50);
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        EnemyComponent enemyComponent = new EnemyComponent(MathUtils.random(100, 120));
        enemyComponent.isABoss = true;
        entity.add(enemyComponent);

        return entity;
    }

    public static Entity createMoney(float x, float y, float z) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/money.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);

        btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);
        MotionState motionState = new MotionState(modelComponent.instance.transform);
        btRigidBody.btRigidBodyConstructionInfo bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, motionState, shape, Vector3.Zero);
        btRigidBody body = new btRigidBody(bodyInfo);
        body.userData = entity;
        BulletComponent bulletComponent = new BulletComponent(body);
        entity.add(bulletComponent);

        MoneyComponent moneyComponent = new MoneyComponent();
        entity.add(moneyComponent);

        return entity;
    }

    public static Entity createOnlineEnemy(boolean isServer, MyServer server, MyClient client) {
        Entity entity = new Entity();

        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());
        ModelData modelData = modelLoader.loadModelData(Gdx.files.internal("Models/ogr.g3dj"));
        Model model = new Model(modelData, new TextureProvider.FileTextureProvider());
        ModelComponent modelComponent = new ModelComponent(model, 0, 0, 0);
        entity.add(modelComponent);

        btBoxShape shape = new btBoxShape(new Vector3(40, 150, 40));
        btPairCachingGhostObject ghostObject = new btPairCachingGhostObject();
        ghostObject.setWorldTransform(modelComponent.instance.transform);
        ghostObject.setCollisionShape(shape);
        ghostObject.userData = entity;
        ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        btKinematicCharacterController characterController = new btKinematicCharacterController(ghostObject, shape, 0.35f);
        characterController.setGravity(new Vector3(0, -1000, 0));
        characterController.setFallSpeed(10*50);
        CharacterComponent characterComponent = new CharacterComponent(ghostObject, shape, characterController);
        entity.add(characterComponent);

        OnlineComponent onlineComponent = new OnlineComponent(isServer, server, client);
        entity.add(onlineComponent);

        return entity;
    }
}
