package com.deeep.spaceglad;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.EnemySystem;
import com.deeep.spaceglad.systems.PatronSystem;
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
        engine.addSystem(new EnemySystem());
        engine.addSystem(new PatronSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(-100, -1000, -100, "mountains"));
        engine.addEntity(EntityFactory.loadObject(0, 100, 0, "desk"));
        engine.addEntity(EntityFactory.createPlayer(0, 500, 0));
        engine.addEntity(EntityFactory.createEnemy(100, 100, 0));
    }

    public void render(float delta) {
        engine.update(delta);
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
    }
}
