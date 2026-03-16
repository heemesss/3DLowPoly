package com.deeep.spaceglad.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deeep.spaceglad.Core;

public class GameUI {
    private Stage stage;

    public ScoreWidget scoreWidget;
    public HealthWidget healthWidget;
    private CrosshairWidget crosshairWidget;

    public GameUI(){
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
        setWidgets();
        configureWidgets();
    }

    private void setWidgets(){
        scoreWidget = new ScoreWidget();
        crosshairWidget = new CrosshairWidget();
        healthWidget = new HealthWidget();

        stage.addActor(scoreWidget);
        stage.addActor(crosshairWidget);
        stage.addActor(healthWidget);
    }

    private void configureWidgets(){
        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition(0, Gdx.graphics.getHeight() - scoreWidget.getHeight());

        healthWidget.setSize(140, 25);
        healthWidget.setPosition(0, Gdx.graphics.getHeight() - healthWidget.getHeight() * 3);

        crosshairWidget.setPosition(Gdx.graphics.getWidth() / 2f - 16, Gdx.graphics.getHeight() / 2f - 16);
        crosshairWidget.setSize(32, 32);
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
