package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.Worlds.GameInfinityWorld;
import com.deeep.spaceglad.Worlds.GameLevel3World;

public class GameLevel3Screen implements Screen {
    private GameLevel3World gameWorld;

    public GameLevel3Screen(Core game) {
        gameWorld = new GameLevel3World(game);
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
