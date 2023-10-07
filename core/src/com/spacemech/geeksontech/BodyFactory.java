package com.spacemech.geeksontech;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.spacemech.geeksontech.components.*;

import java.util.ArrayList;
import java.util.List;


public class BodyFactory {
      private static BodyFactory thisInstance;
      private World world;
      public PooledEngine engine;
      public Entity player;
       public List<Entity> enemies = new ArrayList<>();

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

    public void createSpawner(float x, float y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        SpawnerComponent spawner = engine.createComponent(SpawnerComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        b2dBodyComponent.body = createOval(x, y, 1, false);

        position.position.set(10, 10, 0);
        b2dBodyComponent.body.setUserData(entity);

        entity.add(spawner);
        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(health);
        engine.addEntity(entity);

//        return entity;
    }

    public Entity createEnemy(float x, float y, float xVelocity, float yVelocity) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        b2dBodyComponent.body = createOval(x, y, 1, true);

        position.position.set(10, 10, 0);
        type.type = TypeComponent.ENEMY;
        stateComponent.set(StateComponent.STATE_NORMAL);
        b2dBodyComponent.body.setUserData(entity);
        enemy.xVel = xVelocity;
        enemy.yVel = yVelocity;

        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(enemy);
        entity.add(collisionComponent);
        entity.add(stateComponent);
        entity.add(type);
        entity.add(health);
        engine.addEntity(entity);
        enemies.add(entity);
        return entity;
    }

    public Entity createPlayer() {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);



        position.position.set(15, 1, 0);
        type.type = TypeComponent.PLAYER;
        stateComponent.set(StateComponent.STATE_NORMAL);

        b2dBodyComponent.body = createOval(position.position.x, position.position.y, 1, true);
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

        return entity;
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
        System.out.println("there goes another bullet");
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        BulletComponent bullet = engine.createComponent(BulletComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);


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
        entity.add(collisionComponent);
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
