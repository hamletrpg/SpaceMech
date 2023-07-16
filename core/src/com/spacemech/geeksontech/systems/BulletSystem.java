package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.BulletComponent;
import com.spacemech.geeksontech.components.PlayerComponent;

public class BulletSystem extends IteratingSystem {
    ComponentMapper<B2dBodyComponent> b2dbodyComponent;
    ComponentMapper<BulletComponent> bulletComponent;
    ComponentMapper<PlayerComponent> playerComponent;
    private BodyFactory bodyFactory;

    @SuppressWarnings("unchecked")
    public BulletSystem(BodyFactory bodyFactory) {
        super(Family.all(BulletComponent.class).get());
        bulletComponent = ComponentMapper.getFor(BulletComponent.class);
        b2dbodyComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
        this.bodyFactory = bodyFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2dbody = b2dbodyComponent.get(entity);
        BulletComponent bullet = bulletComponent.get(entity);

        b2dbody.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        B2dBodyComponent playerBodyComp = b2dbodyComponent.get(bodyFactory.player);

        float playerX = playerBodyComp.body.getPosition().x;
        float playerY = playerBodyComp.body.getPosition().y;

        float bulletX = b2dbody.body.getPosition().x;
        float bulletY = b2dbody.body.getPosition().y;

        if (bulletX - playerX > 30 || bulletY - playerY > 30) {
            bullet.isDead = true;
        }

        if (bullet.isDead) {
            System.out.println("Bullet died");
            b2dbody.isDead = true;
        }
    }
}