package com.deeep.spaceglad.Worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;
import com.deeep.spaceglad.systems.TextSystem;

public class StartInfinityWorld {
    private Engine engine;
    private GameUI gameUI;
    private Core game;

    public StartInfinityWorld(Core game){
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
        engine.addSystem(new TextSystem());
//        engine.addSystem(new EnemySystem());
//        engine.addSystem(new PatronSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(0, -4000, 0, "start"));
        engine.addEntity(EntityFactory.createPlayer(0, -3700, 0));
//        engine.addEntity(EntityFactory.createButton(-300, -3945, -300, 0));
        engine.addEntity(EntityFactory.createButton(0, -3945, -300, -1, true));
        engine.addEntity(EntityFactory.createText(0, -3845, -300, "start.png"));
//        engine.addEntity(EntityFactory.createButton(300, -3945, -300, 2));

//        engine.addEntity(EntityFactory.loadScene(-100, -1000, -100, "mountains"));
//        engine.addEntity(EntityFactory.createPlayer(0, 500, 0));
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
    }
}
