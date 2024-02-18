package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.spacemech.geeksontech.LevelFactory;
import com.spacemech.geeksontech.components.BulletComponent;
import com.spacemech.geeksontech.components.EnemyComponent.Type;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.EnemyComponent;
import com.spacemech.geeksontech.components.HealthComponent;

public class EnemySystem extends IteratingSystem {
    private ComponentMapper<EnemyComponent> enemyComponent;
    private ComponentMapper<B2dBodyComponent> b2dComponent;
    private ComponentMapper<HealthComponent> enemyHealth;
    private LevelFactory levelFactory;
    private float timeSinceLastShot;
    private float shootDelay = 3f;

    @SuppressWarnings("unchecked")
    public EnemySystem(LevelFactory levelFactory) {
        super(Family.all(EnemyComponent.class).get());
        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
        b2dComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        enemyHealth = ComponentMapper.getFor(HealthComponent.class);
        this.levelFactory = levelFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComp = enemyComponent.get(entity);
        B2dBodyComponent b2dComp = b2dComponent.get(entity);
        HealthComponent enemyHealthComp = enemyHealth.get(entity);

        b2dComp.body.setLinearVelocity(enemyComp.xVel, enemyComp.yVel);

        if(enemyComp.enemyType == Type.ENEMYSHIP) {
            // Variable to switch direction of the enemy
            Math.abs(enemyComp.xPostCenter - b2dComp.body.getPosition().x);
        }

        if (this.timeSinceLastShot > 0) {
            this.timeSinceLastShot -= deltaTime;
        }

//        if(this.timeSinceLastShot <= 0) {
//
//            float bulletPadding = 0.7f;
//            levelFactory.createBullet(
//                        b2dComp.body.getPosition().x,
//                        (b2dComp.body.getPosition().y-bulletPadding),
//                        0, -3, BulletComponent.Owner.ENEMY
//                );
//                this.timeSinceLastShot = this.shootDelay;
//            }


        if (enemyHealthComp.health <= 0) {
            enemyComp.isDead = true;
        }

        if (enemyComp.isDead) {
            b2dComp.isDead = true;
        }
    }

}
