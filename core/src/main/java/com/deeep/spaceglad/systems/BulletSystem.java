package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.components.BulletComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.managers.Helpers;

public class BulletSystem extends EntitySystem implements EntityListener {
    private final btCollisionConfiguration collisionConfiguration;
    private final btCollisionDispatcher dispatcher;
    private final btBroadphaseInterface broadphase;
    public final btDiscreteDynamicsWorld collisionWorld;

    private ImmutableArray<Entity> entities;

    private DebugDrawer debugDrawer;
    private final boolean debug = false;

    public class MyContactListener extends ContactListener {
        @Override
        public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
//            System.out.println("SDFASDFASDFA");
//            if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {
//                Entity entity0 = (Entity) colObj0.userData;
//                Entity entity1 = (Entity) colObj1.userData;
//            }
        }
    }

    public BulletSystem(){
        MyContactListener myContactListener = new MyContactListener();
        myContactListener.enable();
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btAxisSweep3(new Vector3(-1000, -1000, -1000), new Vector3(1000, 1000, 1000));
        btSequentialImpulseConstraintSolver solver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        btGhostPairCallback ghostPairCallback = new btGhostPairCallback();
        broadphase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);
        collisionWorld.setGravity(new Vector3(0, -10, 0));

        if (debug) {
            debugDrawer = new DebugDrawer();
            debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
            collisionWorld.setDebugDrawer(debugDrawer);
        }
    }

    @Override
    public void update(float deltaTime) {
        collisionWorld.stepSimulation(deltaTime);
        for (Entity entity : entities) {
            ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
            CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);

            Vector3 translation = new Vector3();
            characterComponent.ghostObject.getWorldTransform().getTranslation(translation);
            modelComponent.instance.transform.set(translation.x, translation.y, translation.z, 0,0 ,0 ,0);
        }

        if (debug) {
            debugDrawer.begin(Helpers.camera);
            collisionWorld.debugDrawWorld();
            debugDrawer.end();
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        BulletComponent bulletComponent = entity.getComponent(BulletComponent.class);
        CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
        if (bulletComponent != null) {
            collisionWorld.addRigidBody((btRigidBody) bulletComponent.body);
        }
        if (characterComponent != null) {
            collisionWorld.addCollisionObject(characterComponent.ghostObject,
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
            collisionWorld.addAction(characterComponent.characterController);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        engine.addEntityListener(Family.one(BulletComponent.class, CharacterComponent.class).get(), this);
        entities = engine.getEntitiesFor(Family.all(CharacterComponent.class).get());
    }

    public void removeBody(Entity entity) {
        BulletComponent comp = entity.getComponent(BulletComponent.class);
        if (comp != null)
            collisionWorld.removeCollisionObject(comp.body);
    }

    public void dispose() {
        collisionWorld.dispose();
        if (broadphase != null) broadphase.dispose();
        if (dispatcher != null) dispatcher.dispose();
        if (collisionConfiguration != null) collisionConfiguration.dispose();
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
