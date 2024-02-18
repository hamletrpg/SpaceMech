package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.LevelFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.BulletComponent;
import com.spacemech.geeksontech.components.PlayerComponent;
import com.spacemech.geeksontech.controller.KeyboardController;

public class BulletSystem extends IteratingSystem {
    ComponentMapper<B2dBodyComponent> b2dbodyComponent;
    ComponentMapper<BulletComponent> bulletComponent;
    ComponentMapper<PlayerComponent> playerComponent;
    KeyboardController controller;
    private LevelFactory levelFactory;

    @SuppressWarnings("unchecked")
    public BulletSystem(LevelFactory levelFactory, KeyboardController controller) {
        super(Family.all(BulletComponent.class).get());
        bulletComponent = ComponentMapper.getFor(BulletComponent.class);
        b2dbodyComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
        this.levelFactory = levelFactory;
        this.controller = controller;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2dbody = b2dbodyComponent.get(entity);
        BulletComponent bullet = bulletComponent.get(entity);



        B2dBodyComponent playerBodyComp = b2dbodyComponent.get(levelFactory.player);

        float playerX = playerBodyComp.body.getPosition().x;
        float playerY = playerBodyComp.body.getPosition().y;

        float bulletX = b2dbody.body.getPosition().x;
        float bulletY = b2dbody.body.getPosition().y;

        b2dbody.body.setLinearVelocity(bullet.xVel, bullet.yVel);

        if (bulletX - playerX > 30 || bulletY - playerY > 30) {
            bullet.isDead = true;
        }

        if (bullet.isDead) {
            System.out.println("Bullet died");
            b2dbody.isDead = true;
        }
    }
}