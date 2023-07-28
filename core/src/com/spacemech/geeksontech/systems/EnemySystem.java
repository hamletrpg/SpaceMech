package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.spacemech.geeksontech.components.EnemyComponent.Type;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.EnemyComponent;
import com.spacemech.geeksontech.components.HealthComponent;

public class EnemySystem extends IteratingSystem {
    private ComponentMapper<EnemyComponent> enemyComponent;
    private ComponentMapper<B2dBodyComponent> b2dComponent;
    private ComponentMapper<HealthComponent> enemyHealth;
    private BodyFactory bodyFactory;

    @SuppressWarnings("unchecked")
    public EnemySystem(BodyFactory bodyFact) {
        super(Family.all(EnemyComponent.class).get());
        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
        b2dComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        enemyHealth = ComponentMapper.getFor(HealthComponent.class);
        bodyFactory = bodyFact;
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

        if (enemyHealthComp.health <= 0) {
            enemyComp.isDead = true;
        }

        if (enemyComp.isDead) {
            b2dComp.isDead = true;
        }
    }

}
