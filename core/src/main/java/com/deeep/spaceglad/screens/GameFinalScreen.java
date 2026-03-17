package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.Worlds.GameFinalWorld;

public class GameFinalScreen implements Screen {
    private GameFinalWorld gameWorld;
    private GameUI gameUI;

    public GameFinalScreen(Core game) {
        gameWorld = new GameFinalWorld(game);
        gameUI = new GameUI(game);
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);
        Gdx.input.setInputProcessor(gameUI.stage);
    }

    @Override
    public void render(float delta) {
        gameWorld.render(delta);
        gameUI.update(delta);
        gameUI.render();
    }

    @Override
    public void resize(int width, int height) {
        gameWorld.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameWorld.dispose();
        gameUI.dispose();
    }
}
