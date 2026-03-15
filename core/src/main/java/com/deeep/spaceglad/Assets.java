package com.deeep.spaceglad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Assets {
    public static Skin skin;
    public static Sound soundGun, soundDeath;
    public static ImageTextButton.ImageTextButtonStyle style;

    public Assets() {
        skin = new Skin();
        FileHandle fileHandle = Gdx.files.internal("data/uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

        soundGun = Gdx.audio.newSound(Gdx.files.internal("data/soundGun.mp3"));
        soundDeath = Gdx.audio.newSound(Gdx.files.internal("data/soundDeath.mp3"));

         style = new ImageTextButton.ImageTextButtonStyle(
            new TextureRegionDrawable(new Texture("data/button_up.png")),
            new TextureRegionDrawable(new Texture("data/button_down.png")),
            new TextureRegionDrawable(new Texture("data/button_up.png")), Assets.skin.getFont("default-font"));
    }

    public static void dispose() {
        skin.dispose();
        soundGun.dispose();
        soundDeath.dispose();
    }
}
