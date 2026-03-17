package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.Worlds.StartInfinityWorld;
import com.deeep.spaceglad.Worlds.StartLevelsWorld;

public class StartInfinityScreen implements Screen {
    private StartInfinityWorld gameWorld;

    public StartInfinityScreen(Core game) {
        gameWorld = new StartInfinityWorld(game);
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render(float delta) {
        gameWorld.render(delta);
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
    }
}
