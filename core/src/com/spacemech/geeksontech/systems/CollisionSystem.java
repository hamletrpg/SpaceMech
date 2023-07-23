package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.spacemech.geeksontech.components.*;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> collisionComponent;
    ComponentMapper<PlayerComponent> playerComponent;
    ComponentMapper<EnemyComponent> enemyComponent;
    ComponentMapper<BulletComponent> bulletComponent;
    ComponentMapper<HealthComponent> healthComponent;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

        collisionComponent = ComponentMapper.getFor(CollisionComponent.class);
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
        bulletComponent = ComponentMapper.getFor(BulletComponent.class);
        healthComponent = ComponentMapper.getFor(HealthComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collisionComponentToProcess = collisionComponent.get(entity);
        Entity collidedEntity = collisionComponentToProcess.collisionEntity;
        TypeComponent thisType = entity.getComponent(TypeComponent.class);
        if (thisType.type == TypeComponent.PLAYER) {
            PlayerComponent playerComp = playerComponent.get(entity);
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.ENEMY:
                            //do player hit enemy thing
                            System.out.println(playerComp + " Player hit enemy");
                            HealthComponent health = healthComponent.get(entity);
                            health.health -= 10;
                            System.out.println(health.health + " health left");
                            break;
                        case TypeComponent.OTHER:
                            //do player hit other thing
                            System.out.println("player hit other");
                            break; //technically this isn't needed
                    }
                    collisionComponentToProcess.collisionEntity = null; // collision handled reset component
                }
            }
        }
        else if(thisType.type == TypeComponent.ENEMY) {
            System.out.println("yeap, hit enemy tho");
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.BULLET:
//                            EnemyComponent enemy = enemyComponent.get(entity);
                            BulletComponent bullet = bulletComponent.get(collidedEntity);
                            HealthComponent health = healthComponent.get(entity);
                            if (bullet.owner != BulletComponent.Owner.ENEMY) {
                                bullet.isDead = true;
                                health.health -= 10;
                                System.out.println("enemy's health " + health.health);
                            }
                            break;
                    }
                    collisionComponentToProcess.collisionEntity = null;
                }
            }
        }
    }
}
