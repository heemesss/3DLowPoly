package com.deeep.spaceglad.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.managers.ControllerWidget;
import com.deeep.spaceglad.managers.Helpers;
import com.deeep.spaceglad.managers.Stats;
import com.deeep.spaceglad.screens.MainMenuScreen;
import com.deeep.spaceglad.screens.StartInfinityScreen;
import com.deeep.spaceglad.screens.StartLevelsScreen;

public class GameUI {
    private Core game;
    public Stage stage;

    private ScoreWidget scoreWidget;
    private HealthWidget healthWidget;
    private CrosshairWidget crosshairWidget;
    private StatusWidget statusWidget;
    private ControllerWidget controllerWidget;

    private Image fire;

    public GameUI(Core game){
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.game = game;
        setWidgets();
        configureWidgets();
    }

    private void setWidgets(){
        scoreWidget = new ScoreWidget();
        crosshairWidget = new CrosshairWidget();
        healthWidget = new HealthWidget();
        statusWidget = new StatusWidget();
        controllerWidget = new ControllerWidget();
        fire = new Image(new Texture("data/fire.png"));

        stage.addActor(scoreWidget);
        stage.addActor(crosshairWidget);
        stage.addActor(healthWidget);
        stage.addActor(statusWidget);
        stage.addActor(fire);

        controllerWidget.addToStage(stage);
    }

    private void configureWidgets(){
        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, Gdx.graphics.getHeight() - scoreWidget.getHeight());

        healthWidget.setSize(140, 25);
        healthWidget.setPosition(0, Gdx.graphics.getHeight() - healthWidget.getHeight() * 3);

        crosshairWidget.setPosition(Gdx.graphics.getWidth() / 2f - 16, Gdx.graphics.getHeight() / 2f - 16);
        crosshairWidget.setSize(32, 32);

        statusWidget.setSize(Gdx.graphics.getWidth() / 3f, 32);
        statusWidget.setPosition(Gdx.graphics.getWidth() / 2f - statusWidget.getWidth() / 2f, Gdx.graphics.getHeight() - statusWidget.getHeight());

        fire.setSize(Gdx.graphics.getWidth() / 8f, Gdx.graphics.getWidth() / 8f);
        fire.setPosition(Gdx.graphics.getWidth() / 4f * 3, Gdx.graphics.getHeight() / 3f);
        fire.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Helpers.fire = true;
            }
        });
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void render() {
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void dispose() {
        stage.dispose();
    }
}
