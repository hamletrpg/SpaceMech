package com.spacemech.geeksontech.views;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.spacemech.geeksontech.B2dContactListener;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.LevelFactory;
import com.spacemech.geeksontech.SpaceMech;
import com.spacemech.geeksontech.controller.KeyboardController;
import com.spacemech.geeksontech.systems.*;


public class MainGame implements Screen {
    private KeyboardController controller;
    private SpriteBatch spriteBatch;
    private PooledEngine engine;
    private LevelFactory levelFactory;
    private OrthographicCamera camera;
    private SpaceMech game;
    private Entity player;
    private Entity enemy;

    public MainGame(SpaceMech spaceMech) {
        game = spaceMech;

        controller = new KeyboardController();
        engine = new PooledEngine();
        levelFactory = new LevelFactory(engine);
        float PixelsPerMeter = 32.0f;
        float WorldWidth = Gdx.graphics.getWidth()/PixelsPerMeter;
        float WorldHeight = Gdx.graphics.getHeight()/PixelsPerMeter;

        camera = new OrthographicCamera(WorldWidth, WorldHeight);
        camera.position.set(WorldWidth / 2f, WorldHeight / 2f, 0);
        camera.update();
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        player = levelFactory.createPlayer(camera);
//        enemy = levelFactory.createEnemy(
//                10,
//                10,
//                0, -1
//        );

        engine.addSystem(new PhysicsSystem(levelFactory.world));
        engine.addSystem(new PhysicsDebugSystem(levelFactory.world, camera));
        engine.addSystem(new CollisionSystem());

        engine.addSystem(new EnemySystem(levelFactory));
        engine.addSystem(new BulletSystem(levelFactory, controller));
        engine.addSystem(new PlayerControlSystem(controller, levelFactory));
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(controller);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
