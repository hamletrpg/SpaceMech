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
        super(Family.all(CollisionComponent.class).get());

        collisionComponent = ComponentMapper.getFor(CollisionComponent.class);
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
//        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
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
                if (type != null && type.type != TypeComponent.BULLET) {
                    System.out.println("type.type: " + type.type);
                    System.out.println("TypeComponent: " + TypeComponent.PLAYER);
                    switch (type.type) {
                        case TypeComponent.ENEMY:
                            System.out.println(playerComp + " Player hit enemy");
                            break;
                        case TypeComponent.BULLET:
                            BulletComponent bullet = bulletComponent.get(collidedEntity);
                            HealthComponent health = healthComponent.get(entity);
                            if (bullet.owner != BulletComponent.Owner.PLAYER) {
                                bullet.isDead = true;
                                health.health -= 10;
                                System.out.println("Player's health " + health.health);
                            }
                            break;
                        case TypeComponent.OTHER:
                            System.out.println("player hit other");
                            break;
                        default:
                            System.out.println("Player: No matching type found");
                    }
                    collisionComponentToProcess.collisionEntity = null;
                }
            }
        }else if(thisType.type == TypeComponent.ENEMY) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case TypeComponent.BULLET:
                            BulletComponent bullet = bulletComponent.get(collidedEntity);
                            HealthComponent health = healthComponent.get(entity);
                            if (bullet.owner != BulletComponent.Owner.ENEMY) {
                                bullet.isDead = true;
                                health.health -= 10;
                                System.out.println("enemy's health " + health.health);
                            }
                            break;
                        default:
                            System.out.println("Enemy: No matching type found");
                    }
                    collisionComponentToProcess.collisionEntity = null;
                }
            }
        }
        else {
            collisionComponentToProcess.collisionEntity = null;
        }
    }
}
