package com.deeep.spaceglad.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.spaceglad.managers.Helpers;

public class FireWidget  extends Actor {
    private Image fireButton;
    private Texture texture;

    public FireWidget() {
        texture = new Texture("data/fire.png");
        fireButton = new Image(texture);
    }

    public void setListener(){
        fireButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Helpers.fire = true;
            }
        });
    }

    @Override
    public void act(float delta) {
        fireButton.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        fireButton.draw(batch, parentAlpha);
//        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        fireButton.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        fireButton.setSize(width, height);
    }

}
