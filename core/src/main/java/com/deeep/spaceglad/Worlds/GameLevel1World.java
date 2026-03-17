package com.deeep.spaceglad.Worlds;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.EnemySystem;
import com.deeep.spaceglad.systems.PatronSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;

public class GameLevel1World {
    private Engine engine;
    private GameUI gameUI;
    private Core game;

    public GameLevel1World(Core game){
        new Assets();
        engine = new Engine();
        gameUI = new GameUI();
        this.game = game;
        Bullet.init();
        addSystems();
        addEntities();
    }

    private void addSystems() {
        engine.addSystem(new RenderSystem());
        engine.addSystem(new BulletSystem(game));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new EnemySystem(-1000, 200, -1000, 1000, -850));
        engine.addSystem(new PatronSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(-100, -1000, -100, "level1"));
        engine.addEntity(EntityFactory.createPlayer(1000, -850, 0));
//        engine.addEntity(EntityFactory.createEnemy(100, 100, 0));
    }

    public void render(float delta) {
        engine.update(delta);
        gameUI.update(delta);
        gameUI.render();
    }

    public void resize(int width, int height) {
        gameUI.resize(width, height);
    }

    public void dispose() {
        engine.removeAllSystems();
        engine.removeAllEntities();
        gameUI.dispose();
        engine = null;
    }
}
