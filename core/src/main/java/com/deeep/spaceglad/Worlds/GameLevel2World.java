package com.deeep.spaceglad.Worlds;


import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.managers.Stats;
import com.deeep.spaceglad.screens.StartLevelsScreen;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.EnemySystem;
import com.deeep.spaceglad.systems.PatronSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;

public class GameLevel2World {
    private Engine engine;
    private GameUI gameUI;
    private Core game;

    public GameLevel2World(Core game){
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
        engine.addSystem(new EnemySystem(-1000, 1000, -1000, 1000, 200));
        engine.addSystem(new PatronSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(0, 0, 0, "level2"));
        engine.addEntity(EntityFactory.createPlayer(0, 500, 0));
    }

    public void render(float delta) {
        Stats.time -= delta;
        Stats.status = "live " + (int) Stats.time + " seconds";
        if (Stats.time < 0) {
            Gdx.app.getPreferences("levels").putBoolean("level3", true);
            Gdx.app.getPreferences("levels").flush();
            game.setScreen(new StartLevelsScreen(game));
            return;
        }
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
