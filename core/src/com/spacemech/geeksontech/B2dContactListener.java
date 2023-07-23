package com.spacemech.geeksontech;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.spacemech.geeksontech.components.CollisionComponent;

public class B2dContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("From B2dContactListener class: Contact");

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        System.out.println("From B2dContactListener class: " + fixtureA.getBody().getType() + " hat hit " + fixtureB.getBody().getType());

        if (fixtureA.getBody().getUserData() instanceof Entity) {
            Entity entity = (Entity) fixtureA.getBody().getUserData();
            entityCollision(entity, fixtureB);
            return;
        } else if (fixtureA.getBody().getUserData() instanceof Entity) {
            Entity entity = (Entity) fixtureB.getBody().getUserData();
            entityCollision(entity, fixtureA);
            return;
        }
    }

    private void entityCollision(Entity entity, Fixture fixtureB) {
        if(fixtureB.getBody().getUserData() instanceof Entity){
            Entity colEnt = (Entity) fixtureB.getBody().getUserData();
            CollisionComponent collisionComponent = entity.getComponent(CollisionComponent.class);
            CollisionComponent collisionEntity = colEnt.getComponent(CollisionComponent.class);

            if(collisionComponent != null){
                collisionComponent.collisionEntity = colEnt;
            }else if(collisionEntity != null){
                collisionEntity.collisionEntity = entity;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("From B2dContactListener class: Contact end");
    }
    @Override
    public void preSolve (Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve (Contact contact, ContactImpulse impulse) {

    }
}
