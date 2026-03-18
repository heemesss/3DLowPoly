package com.deeep.spaceglad.Worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.deeep.spaceglad.Core;
import com.deeep.spaceglad.UI.GameUI;
import com.deeep.spaceglad.WiFi.MyClient;
import com.deeep.spaceglad.WiFi.MyRequest;
import com.deeep.spaceglad.WiFi.MyResponse;
import com.deeep.spaceglad.WiFi.MyServer;
import com.deeep.spaceglad.components.CharacterComponent;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.components.PlayerComponent;
import com.deeep.spaceglad.managers.EntityFactory;
import com.deeep.spaceglad.managers.Helpers;
import com.deeep.spaceglad.managers.Stats;
import com.deeep.spaceglad.systems.BulletSystem;
import com.deeep.spaceglad.systems.OnlineSystem;
import com.deeep.spaceglad.systems.PatronSystem;
import com.deeep.spaceglad.systems.PlayerSystem;
import com.deeep.spaceglad.systems.RenderSystem;

import java.util.Objects;

public class GameWorldOnline {
    private MyServer server;
    private MyClient client;
    private MyRequest request;
    private MyResponse response;

    private Core game;
    private Engine engine;

    public GameWorldOnline(Core game, MyServer server, MyClient client, MyRequest request,
                           MyResponse response){
        this.server = server;
        this.client = client;
        this.request = request;
        this.response = response;
        engine = new Engine();
        addSystems();
//        gameUI.scoreWidget.player = 0;
//        gameUI.scoreWidget.enemy = 0;
        addEntities();
    }

    private void addSystems() {
        engine.addSystem(new RenderSystem());
        engine.addSystem(new BulletSystem(game));
        engine.addSystem(new PlayerSystem());
        engine.addSystem(new PatronSystem());
        engine.addSystem(new OnlineSystem());
    }

    private void addEntities(){
        engine.addEntity(EntityFactory.loadScene(0, 0
            , 0, "mountains"));
        engine.addEntity(EntityFactory.createPlayer(MathUtils.random(-2000, 2000), 800, MathUtils.random(-2000, 2000)));
        engine.addEntity(EntityFactory.createOnlineEnemy(server != null, server, client));
    }

    private void setPositionCharacter(Entity entity){
        entity.getComponent(ModelComponent.class).instance.transform.set(
            MathUtils.random(1) == 0 ? -44 : 44, 0,
            MathUtils.random(1) == 0 ? -44 : 44, 0, 0, 0, 0);
        entity.getComponent(CharacterComponent.class).ghostObject.setWorldTransform(entity.getComponent(ModelComponent.class).instance.transform);
    }

    public void render(float delta) {
        engine.update(delta);
        Stats.status = "online";
        if (client != null) {
            setRequest();
            client.send();
            System.out.println(response.x);
        } else if (server != null) {
            setResponse();
            System.out.println(request.text);
        }
    }

    public void setRequest(){
        Vector3 position = Helpers.camera.position.cpy();
        request.x = position.x;
        request.y = position.y;
        request.z = position.z;
        request.text = "client";
    }

    public void setResponse(){
        Vector3 position = Helpers.camera.position.cpy();
        response.x = position.x;
        response.y = position.y;
        response.z = position.z;
        response.text = "server";
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
        if (server != null){
            response.text = "EXIT";
        }

        if (client != null){
            request.text = "EXIT";
            client.send();
        }
        engine.removeAllSystems();
        engine.removeAllEntities();
        engine = null;
        Gdx.app.exit();
    }
}
