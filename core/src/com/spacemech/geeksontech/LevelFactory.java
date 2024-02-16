package com.spacemech.geeksontech;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.spacemech.geeksontech.components.*;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory {
    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    public List<Entity> enemies = new ArrayList<>();
    public Entity player;

    public LevelFactory(PooledEngine en){
        engine = en;
        world = new World(new Vector2(0,-10f), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);
    }

//    public void createSpawner(float x, float y) {
//        Entity entity = engine.createEntity();
//        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
//        TransformComponent position = engine.createComponent(TransformComponent.class);
//        SpawnerComponent spawner = engine.createComponent(SpawnerComponent.class);
//        HealthComponent health = engine.createComponent(HealthComponent.class);
//
//        b2dBodyComponent.body = bodyFactory.createOval(x, y, 1, false);
//
//        position.position.set(10, 10, 0);
//        b2dBodyComponent.body.setUserData(entity);
//
//        entity.add(spawner);
//        entity.add(b2dBodyComponent);
//        entity.add(position);
//        entity.add(health);
//        engine.addEntity(entity);
//
////        return entity;
//    }


    public Entity createEnemy(float x, float y, float xVelocity, float yVelocity) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        EnemyComponent enemy = engine.createComponent(EnemyComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        b2dBodyComponent.body = bodyFactory.createOval(x, y, 1, true);

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
        b2dBodyComponent.body = bodyFactory.createOval(x, y, 1, true);

        b2dBodyComponent.body.setBullet(true);
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

    public Entity createPlayer() {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateComponent = engine.createComponent(StateComponent.class);
        HealthComponent health = engine.createComponent(HealthComponent.class);

        type.type = TypeComponent.PLAYER;
        stateComponent.set(StateComponent.STATE_NORMAL);
        position.position.set(5, 5, 0);
        b2dBodyComponent.body = bodyFactory.createOval(position.position.x, position.position.y, 1, true);


        b2dBodyComponent.body.setUserData(entity);

        entity.add(b2dBodyComponent);
        entity.add(position);
        entity.add(player);
        entity.add(collisionComponent);
        entity.add(stateComponent);
        entity.add(type);
        entity.add(health);
        engine.addEntity(entity);
        this.player = entity;
        System.out.println("fuck render me plz");
        return entity;
    }

    public void removeEntity(Entity ent){
        engine.removeEntity(ent);
    }
}
