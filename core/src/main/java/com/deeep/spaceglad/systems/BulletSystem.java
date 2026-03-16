package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.HullResult;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.components.BulletComponent;
import com.deeep.spaceglad.components.ButtonComponent;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.EnemyComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.PatronComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.managers.Helpers;
import com.deeep.spaceglad.managers.Stats;
import com.deeep.spaceglad.screens.GameScreen;
import com.deeep.spaceglad.screens.StartScreen;

public class BulletSystem extends EntitySystem implements EntityListener {
    private btDynamicsWorld collisionWorld;
    private btDefaultCollisionConfiguration collisionConfiguration;
    private btCollisionDispatcher dispatcher;
    private btDbvtBroadphase broadphase;
    private btSequentialImpulseConstraintSolver constraintSolver;

    private ImmutableArray<Entity> entities;

    private DebugDrawer debugDrawer;
    private final boolean debug = false;

    private Core game;


    public class MyContactListener extends ContactListener {
        @Override
        public void onContactStarted(btCollisionObject colObj0, btCollisionObject colObj1) {
            if (colObj0.userData instanceof Entity && colObj1.userData instanceof Entity) {
                Entity entity0 = (Entity) colObj0.userData;
                Entity entity1 = (Entity) colObj1.userData;
                if (entity0.getComponent(EnemyComponent.class) != null && entity1.getComponent(PlayerComponent.class) != null){
                    EnemyComponent enemyComponent = entity0.getComponent(EnemyComponent.class);
                    CharacterComponent characterComponent = entity1.getComponent(CharacterComponent.class);
                    if (enemyComponent.isALife)
                        Stats.health -= 1;
                    if (Stats.health == 0) {
                        game.setScreen(new StartScreen(game));
                    }
                    enemyComponent.isALife = false;
                }
                else if (entity1.getComponent(EnemyComponent.class) != null && entity0.getComponent(PlayerComponent.class) != null) {
                    EnemyComponent enemyComponent = entity1.getComponent(EnemyComponent.class);
                    CharacterComponent characterComponent = entity0.getComponent(CharacterComponent.class);
                    if (enemyComponent.isALife)
                        Stats.health -= 1;
                    if (Stats.health == 0) {
                        game.setScreen(new StartScreen(game));
                    }
                    enemyComponent.isALife = false;
                }


                else if (entity0.getComponent(EnemyComponent.class) != null && entity1.getComponent(PatronComponent.class) != null) {
                    PatronComponent patronComponent = entity1.getComponent(PatronComponent.class);
                    EnemyComponent enemyComponent = entity0.getComponent(EnemyComponent.class);

                    if (patronComponent.timeOfLive > 0)
                        Stats.score += 100;
                    patronComponent.timeOfLive = -1;
                    enemyComponent.isALife = false;
                }
                else if (entity1.getComponent(EnemyComponent.class) != null && entity0.getComponent(PatronComponent.class) != null) {
                    PatronComponent patronComponent = entity0.getComponent(PatronComponent.class);
                    EnemyComponent enemyComponent = entity1.getComponent(EnemyComponent.class);

                    if (patronComponent.timeOfLive > 0)
                        Stats.score += 100;
                    patronComponent.timeOfLive = -1;
                    enemyComponent.isALife = false;
                }

                else if (entity1.getComponent(ButtonComponent.class) != null) {
                    ButtonComponent buttonComponent = entity1.getComponent(ButtonComponent.class);
                    CharacterComponent characterComponent = entity0.getComponent(CharacterComponent.class);
                    if (buttonComponent.type == 0){
                        game.setScreen(new GameScreen(game));
                        Stats.health = 1;
                    }
                }
            }
        }
    }

    public BulletSystem(Core game){
        MyContactListener myContactListener = new MyContactListener();
        myContactListener.enable();

        this.game = game;

        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        btSequentialImpulseConstraintSolver solver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfiguration);
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

        System.out.println(entities.size());
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
        CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
        if (comp != null)
            collisionWorld.removeCollisionObject(comp.body);
        if (characterComponent != null) {
            collisionWorld.removeCollisionObject(characterComponent.ghostObject);
            collisionWorld.removeAction(characterComponent.characterController);
        }
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        dispose();
    }

    public void dispose() {

//        collisionWorld.dispose();

//        if (broadphase != null) broadphase.dispose();
//        if (dispatcher != null) dispatcher.dispose();
//        if (collisionConfiguration != null) collisionConfiguration.dispose();
    }

    @Override
    public void entityRemoved(Entity entity) {
        removeBody(entity);
        entity.removeAll();
    }
}
