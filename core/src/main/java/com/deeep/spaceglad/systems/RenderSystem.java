package com.deeep.spaceglad.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.components.ModelComponent;
import com.deeep.spaceglad.managers.Helpers;

public class RenderSystem extends EntitySystem {
    private static final float FOV = 67F;
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private final Environment environment;
    private final DirectionalShadowLight shadowLight;
    public PerspectiveCamera camera;
    private final Vector3 position;

    public RenderSystem(){
        camera = new PerspectiveCamera(FOV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Helpers.camera = camera;
        camera.far = 100000;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 0.8f));
        shadowLight = new DirectionalShadowLight(1024 * 5, 1024 * 5, 200f, 200f, 1f, 300f);
        shadowLight.set(1f, 1f, 1f, 0, -0.1f, 0.1f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;

        batch = new ModelBatch();

        position = new Vector3();
    }

    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    public void update(float delta) {
        camera.update();
        drawModels(delta);
        if (Gdx.input.isCursorCatched()){
            camera.rotate(camera.up, -Gdx.input.getDeltaX() * 0.5f);
            camera.direction.rotate(new Vector3().set(camera.direction).crs(camera.up).nor(), -Gdx.input.getDeltaY() * 0.5f);
        }
        drawShadows();
    }

    private boolean isVisible(Camera cam, final ModelInstance instance) {
        return cam.frustum.pointInFrustum(instance.transform.getTranslation(position));
    }

    private void drawShadows() {
        shadowLight.begin(Vector3.Zero, camera.direction);
        batch.begin(shadowLight.getCamera());
        for (int x = 0; x < entities.size(); x++) {
            ModelComponent mod = entities.get(x).getComponent(ModelComponent.class);
            if (isVisible(camera, mod.instance)) batch.render(mod.instance);

        }
        batch.end();
        shadowLight.end();
    }

    private void drawModels(float delta) {
        batch.begin(camera);
        for (int i = 0; i < entities.size(); i++) {
            ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
            batch.render(mod.instance, environment);
        }
        batch.end();
    }

    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    public void dispose() {
        batch.dispose();
        batch = null;
    }
}
