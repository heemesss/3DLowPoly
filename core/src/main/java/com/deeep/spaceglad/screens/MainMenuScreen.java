package com.deeep.spaceglad.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.spaceglad.Assets;
import com.deeep.spaceglad.Core;

public class MainMenuScreen implements Screen {
    Core game;
    Stage stage;
    Image backgroundImage, titleImage;
    ImageTextButton playButton, quitButton, onlineGameButton;

    public MainMenuScreen(Core game) {
        new Assets();
        this.game = game;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        setWidgets();
        configureWidgets();
        setListeners();

        Gdx.input.setInputProcessor(stage);
    }

    private void setWidgets() {
        backgroundImage = new Image(new Texture(Gdx.files.internal("data/backgroundMN.png")));
        titleImage = new Image(new Texture(Gdx.files.internal("data/title.png")));
        playButton = new ImageTextButton("Play", Assets.style);
        onlineGameButton = new ImageTextButton("Online", Assets.style);
        quitButton = new ImageTextButton("Quit", Assets.style);
    }

    private void configureWidgets() {
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        titleImage.setSize(Gdx.graphics.getWidth() / 3f, 200);
        titleImage.setPosition(Gdx.graphics.getWidth() / 6f * 2 - titleImage.getWidth() / 2, Gdx.graphics.getHeight() / 2f - titleImage.getHeight() / 2);

        playButton.setSize(300, 128);
        quitButton.setSize(300, 128);
        onlineGameButton.setSize(300, 128);

        playButton.setPosition(Gdx.graphics.getWidth() / 4f * 3 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f + playButton.getHeight() * 1.75f);
        onlineGameButton.setPosition(Gdx.graphics.getWidth() / 4f * 3 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f + onlineGameButton.getHeight() * 0.25f);
        quitButton.setPosition(Gdx.graphics.getWidth() / 4f * 3 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2f - quitButton.getHeight() * 2.75f);

        stage.addActor(backgroundImage);
        stage.addActor(titleImage);
        stage.addActor(playButton);
        stage.addActor(onlineGameButton);
        stage.addActor(quitButton);
    }

    private void setListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StartInfinityScreen(game));
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        onlineGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StartLevelsScreen(game));
            }
        });

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
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
}
