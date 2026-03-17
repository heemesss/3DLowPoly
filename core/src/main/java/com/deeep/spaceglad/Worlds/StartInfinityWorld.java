package com.deeep.spaceglad.Worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.managers.Stats;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.PatronSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;
import com.deeep.spaceglad.systems.TextSystem;

public class StartInfinityWorld {
    private Engine engine;
    private Core game;

    public StartInfinityWorld(Core game){
        new Assets();
        engine = new Engine();
        this.game = game;
        Bullet.init();
        addSystems();
        addEntities();
        Stats.score = 0;
        Stats.isLevels = false;
    }

    private void addSystems() {
        engine.addSystem(new RenderSystem());
        engine.addSystem(new BulletSystem(game));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new TextSystem());
        engine.addSystem(new PatronSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(0, -4000, 0, "start"));
        engine.addEntity(EntityFactory.createPlayer(0, -3700, 0));
        engine.addEntity(EntityFactory.createButton(0, -3945, -300, -1, true));
        engine.addEntity(EntityFactory.createText(0, -3845, -300, "start.png"));
    }

    public void render(float delta) {
        engine.update(delta);
    }

    public void resize(int width, int height) {
    }

    public void dispose() {
        engine.removeAllSystems();
        engine.removeAllEntities();
    }
}
