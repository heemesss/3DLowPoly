package com.deeep.spaceglad;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;

public class GameWorld{
    private Engine engine;

    public GameWorld(){
        Bullet.init();
        addSystems();
        addEntities();
    }

    private void addSystems() {
        engine = new Engine();
        engine.addSystem(new RenderSystem());
        engine.addSystem(new BulletSystem());
        engine.addSystem(new PlayerSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(-100, -1000, -100, "Untitled"));
        engine.addEntity(EntityFactory.loadObject(0, 100, 0, "desk"));
        engine.addEntity(EntityFactory.createPlayer(0, 100, 0));
        engine.addEntity(EntityFactory.createEnemy(0, 0, 0));
    }

    public void render(float delta) {
        engine.update(delta);
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
    }
}
