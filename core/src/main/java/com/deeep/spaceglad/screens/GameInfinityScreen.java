package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.Worlds.GameInfinityWorld;

public class GameInfinityScreen implements Screen {
    private GameInfinityWorld gameInfinityWorld;

    public GameInfinityScreen(Core game) {
        gameInfinityWorld = new GameInfinityWorld(game);
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render(float delta) {
        gameInfinityWorld.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameInfinityWorld.resize(width, height);
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
        gameInfinityWorld.dispose();
    }
}
