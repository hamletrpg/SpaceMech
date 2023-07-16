package com.spacemech.geeksontech;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.*;
import com.spacemech.geeksontech.components.*;


public class BodyFactory {
      private static BodyFactory thisInstance;
      private World world;
      public Body b2body;
      public PooledEngine engine;
      public Entity player;

//      private final float DEGTORAD = 0.0174533f;

      private BodyFactory(World world, PooledEngine engine) {

          this.world = world;
          this.engine = engine;
      }

      public static BodyFactory getInstance(World world, PooledEngine engine) {
          if (thisInstance == null) {
              thisInstance = new BodyFactory(world, engine);
          } else {
              thisInstance.world = world;
              thisInstance.engine = engine;
          }
          return  thisInstance;
      }

//      public Body createFigure() {
//          BodyDef bdef = new BodyDef();
//          bdef.position.set(32 / SpaceMech.PPM, 32 / SpaceMech.PPM);
//          bdef.type = BodyDef.BodyType.DynamicBody;
//          b2body = world.createBody(bdef);
//          FixtureDef fdef = new FixtureDef();
//
//          CircleShape shape = new CircleShape();
//          shape.setRadius(6 / SpaceMech.PPM);
//
//          fdef.shape = shape;
//          b2body.createFixture(fdef).setUserData(this);
//
//          b2body.createFixture(fdef);
//          return b2body;
//      }

    public void createEnemy(int x, int y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        b2dBodyComponent.body = createOval(10, 10, 1, false);

        position.position.set(10, 10, 0);
        type.type = TypeComponent.ENEMY;
        stateComponent.set(StateComponent.STATE_NORMAL);
        b2dBodyComponent.body.setUserData(entity);

        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(enemy);
        entity.add(collisionComponent);
        entity.add(stateComponent);
        entity.add(type);
        entity.add(health);

        engine.addEntity(entity);
    }

    public void createPlayer() {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        b2dBodyComponent.body = createOval(15, 1, 1, true);

        position.position.set(15, 1, 0);
        type.type = TypeComponent.PLAYER;
        stateComponent.set(StateComponent.STATE_NORMAL);
        b2dBodyComponent.body.setUserData(entity);

        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(player);
        entity.add(collisionComponent);
        entity.add(stateComponent);
        entity.add(type);
        entity.add(health);
        this.player = entity;
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

    public Entity createBullet(float x, float y, float xVelocity, float yVelocity, BulletComponent.Owner owner) {
        System.out.println("there goes another butter");
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        BulletComponent bullet = engine.createComponent(BulletComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);

        bullet.owner = owner;

        b2dBodyComponent.body = makeCirclePolyBody(x, y, 0.5f, BodyDef.BodyType.DynamicBody, true);

        b2dBodyComponent.body.setBullet(true);
        makeAllFixtureSensors(b2dBodyComponent.body);
        position.position.set(x, y, 0);

        type.type = TypeComponent.BULLET;
        b2dBodyComponent.body.setUserData(entity);
        bullet.xVel = xVelocity;
        bullet.yVel = yVelocity;

        entity.add(bullet);
        entity.add(position);
        entity.add(stateComponent);
        entity.add(b2dBodyComponent);
        entity.add(type);
        engine.addEntity(entity);

        return entity;
    }

    public Body makeCirclePolyBody(float positionX, float positionY, float radius, BodyDef.BodyType bodyType, boolean fixedRotation) {
          BodyDef boxBodyDef = new BodyDef();
          boxBodyDef.type = bodyType;
          boxBodyDef.position.x = positionX;
          boxBodyDef.position.y = positionY;
          boxBodyDef.fixedRotation = fixedRotation;

          Body boxBody = world.createBody(boxBodyDef);
          CircleShape circleShape = new CircleShape();
          circleShape.setRadius(radius/2);
            FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
          circleShape.dispose();
          return boxBody;
    }

    public void makeAllFixtureSensors(Body body) {
          for (Fixture fixture: body.getFixtureList()){
              fixture.setSensor(true);
          }
    }
}
