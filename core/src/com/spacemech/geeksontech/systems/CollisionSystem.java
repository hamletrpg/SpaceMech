package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.spacemech.geeksontech.components.CollisionComponent;
import com.spacemech.geeksontech.components.PlayerComponent;
import com.spacemech.geeksontech.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<CollisionComponent> collisionComponent;
    ComponentMapper<PlayerComponent> playerComponent;

    @SuppressWarnings("unchecked")
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

        collisionComponent = ComponentMapper.getFor(CollisionComponent.class);
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collisionComponentToProcess = collisionComponent.get(entity);
        Entity collidedEntity = collisionComponentToProcess.collisionEntity;
        if (collidedEntity != null) {
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if (type != null) {
                switch(type.type){
                    case TypeComponent.ENEMY:
                        //do player hit enemy thing
                        System.out.println("player hit enemy");
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
}
