package com.deeep.spaceglad.Worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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

public class StartLevelsWorld {
    private Engine engine;
    private Core game;

    public StartLevelsWorld(Core game){
        engine = new Engine();
        this.game = game;
        Bullet.init();
        Stats.health = 1;
        addSystems();
        addEntities();
        Stats.status = "choice level";
        Stats.score = 0;
        Stats.isLevels = true;
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
        Preferences preferences = Gdx.app.getPreferences("levels");

        engine.addEntity(EntityFactory.createButton(-300, -3945, -300, 0, preferences.getBoolean("level1", true)));
        engine.addEntity(EntityFactory.createButton(-100, -3945, -300, 1, preferences.getBoolean("level2", false)));
        engine.addEntity(EntityFactory.createButton(100, -3945, -300, 2, preferences.getBoolean("level3", false)));
        engine.addEntity(EntityFactory.createButton(300, -3945, -300, 3, preferences.getBoolean("final", false)));
        engine.addEntity(EntityFactory.createButton(-300, -3945, 100, 3, true));

        engine.addEntity(EntityFactory.createText(-300, -3845, -300, "level1.png"));
        engine.addEntity(EntityFactory.createText(-100, -3845, -300, "level2.png"));
        engine.addEntity(EntityFactory.createText(100, -3845, -300, "level3.png"));
        engine.addEntity(EntityFactory.createText(300, -3845, -300, "final.png"));
        engine.addEntity(EntityFactory.createText(-300, -3845, 100, "exit.png"));
    }

    public void render(float delta) {
        engine.update(delta);
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
        engine.removeAllSystems();
        engine.removeAllEntities();
        engine = null;
    }
}
