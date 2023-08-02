package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.BulletComponent;
import com.spacemech.geeksontech.components.EnemyComponent;
import com.spacemech.geeksontech.components.SpawnerComponent;

import java.util.ArrayList;
import java.util.List;


public class SpawnerSystem extends IteratingSystem {
    private float timer = 0f;
    private float delay = 7f;
    public float shootDelay = 2f;
    public float timeSinceLastShot = 0f;
    private float bulletPadding = 0.5f;
    private ComponentMapper<SpawnerComponent> spawnerComponent;
    private ComponentMapper<EnemyComponent> enemyComponent;
    private ComponentMapper<B2dBodyComponent> b2dComponent;
    private BodyFactory bodyFactory;

    public SpawnerSystem(BodyFactory bodyFact) {
        super(Family.all(SpawnerComponent.class).get());
        spawnerComponent = ComponentMapper.getFor(SpawnerComponent.class);
        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
        b2dComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        bodyFactory = bodyFact;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2dComp = b2dComponent.get(entity);
        Entity playerEntity = bodyFactory.player;
        B2dBodyComponent playerBody = playerEntity.getComponent(B2dBodyComponent.class);
        float playerBodyX = playerBody.body.getPosition().x;
        float playerBodyY = playerBody.body.getPosition().y;
        List<Entity> enemies = bodyFactory.enemies;




        if (this.timer > 0) {
            this.timer -= deltaTime;
        }

        if (this.timer <= 0) {
           bodyFactory.createEnemy(
                    b2dComp.body.getPosition().x,
                    b2dComp.body.getPosition().y,
                    0, -1
            );
//            enemies.add(enemy);
//            System.out.println(enemies.size());
//            System.out.println("Enemies" + enemies);
            this.timer = this.delay;
//            System.out.println(this.timer);
//            System.out.println(this.delay);
        }

        if (this.timeSinceLastShot > 0) {
            this.timeSinceLastShot -= deltaTime;
        }

        if(this.timeSinceLastShot <= 0) {
            for (Entity enemy : enemies) {
                B2dBodyComponent  enemyBody = enemy.getComponent(B2dBodyComponent.class);

            bodyFactory.createBullet(
                    enemyBody.body.getPosition().x,
                    (enemyBody.body.getPosition().y-bulletPadding),
                        0, -3, BulletComponent.Owner.ENEMY
                );
                this.timeSinceLastShot = this.shootDelay;
            }
        }
    }
}
