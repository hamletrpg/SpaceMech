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
import com.spacemech.geeksontech.components.*;
import com.spacemech.geeksontech.controller.KeyboardController;
import com.spacemech.geeksontech.systems.CollisionSystem;
import com.spacemech.geeksontech.systems.PhysicsDebugSystem;
import com.spacemech.geeksontech.systems.PhysicsSystem;
import com.spacemech.geeksontech.systems.PlayerControlSystem;


public class MainGame implements Screen {
    private KeyboardController controller;
    private World world;
    private SpriteBatch spriteBatch;
    private PooledEngine engine;

    private OrthographicCamera camera;
    @Override
    public void show(){
        controller = new KeyboardController();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new B2dContactListener());

        float PixelsPerMeter = 32.0f;
        float WorldWidth = Gdx.graphics.getWidth()/PixelsPerMeter;
        float WorldHeight = Gdx.graphics.getHeight()/PixelsPerMeter;

        camera = new OrthographicCamera(WorldWidth, WorldHeight);
        camera.position.set(WorldWidth / 2f, WorldHeight / 2f, 0);
        camera.update();
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        engine = new PooledEngine();
        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new PlayerControlSystem(controller));

        createPlayer();

        Gdx.input.setInputProcessor(controller);
    }

    public void createPlayer() {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);

        b2dBodyComponent.body = createOval(10, 1, 1, true);

        position.position.set(10, 10, 0);
        type.type = TypeComponent.PLAYER;
        stateComponent.set(StateComponent.STATE_NORMAL);
        b2dBodyComponent.body.setUserData(entity);

        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(player);
        entity.add(collisionComponent);
        entity.add(stateComponent);
        entity.add(type);

        engine.addEntity(entity);

    }

    private Body createOval(float x, float y, float w, boolean dynamic){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        if(dynamic){
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        }else{
            boxBodyDef.type = BodyDef.BodyType.StaticBody;
        }

        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = true;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(w /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
        circleShape.dispose();

        return boxBody;

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
