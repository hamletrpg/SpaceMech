package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.spacemech.geeksontech.LevelFactory;
import com.spacemech.geeksontech.SpaceMech;
import com.spacemech.geeksontech.components.*;
import com.spacemech.geeksontech.controller.KeyboardController;

public class PlayerControlSystem extends IteratingSystem {
    private LevelFactory levelFactory;
    ComponentMapper<PlayerComponent> playerComponent;
    ComponentMapper<B2dBodyComponent> b2dBodyComponent;
    ComponentMapper<StateComponent> stateComponent;
    KeyboardController controller;
    ComponentMapper<HealthComponent> playerHealth;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem(KeyboardController keyboardController, LevelFactory levelFactory) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyboardController;
        this.levelFactory = levelFactory;
        playerComponent = ComponentMapper.getFor(PlayerComponent.class);
        b2dBodyComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        stateComponent = ComponentMapper.getFor(StateComponent.class);
        playerHealth = ComponentMapper.getFor(HealthComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = b2dBodyComponent.get(entity);
        StateComponent state = stateComponent.get(entity);
        PlayerComponent player = playerComponent.get(entity);
        HealthComponent playerHealthComp = playerHealth.get(entity);


        if (b2body.body.getLinearVelocity().y != 0 && b2body.body.getLinearVelocity().x != 0) {
            state.set(StateComponent.STATE_MOVING);

        }
        if(controller.left){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f),b2body.body.getLinearVelocity().y);
        }
        if(controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f),b2body.body.getLinearVelocity().y);
        }

        if(!controller.left && ! controller.right){
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.2f),b2body.body.getLinearVelocity().y);
        }
        if(controller.up){
            b2body.body.setLinearVelocity(b2body.body.getLinearVelocity().x, MathUtils.lerp(b2body.body.getLinearVelocity().y, 5f, 0.2f));
        }
        if (controller.down) {
            b2body.body.setLinearVelocity(b2body.body.getLinearVelocity().x, MathUtils.lerp(b2body.body.getLinearVelocity().y, -5f, 0.2f));
        }
        if (!controller.down && !controller.up && !controller.left && !controller.right) {
            b2body.body.setLinearVelocity(0, 0);
        }

        if (playerHealthComp.health <= 0) {
            player.isDead = true;
            b2body.isDead = true;
        }

        if (player.timeSinceLastShot > 0) {
            player.timeSinceLastShot -= deltaTime;
        }

        if(controller.isMouse1Down) {
//            Vector3 mousePost = new Vector3(controller.mouseLocation.x, controller.mouseLocation.y, 0);
//            float distanceX = mousePost.x - b2body.body.getPosition().x;
//            float distanceY = mousePost.y - b2body.body.getPosition().y;
//
//            float angle = (float)Math.atan2(distanceY, distanceX);
//            float velocityX = 10.0f * (float)Math.cos(angle);
//            float velocityY = 10.0f * (float)Math.sin(angle);

            Vector2 aim = new Vector2();
            Vector3 mousePost = new Vector3(controller.mouseLocation.x, controller.mouseLocation.y, 0);
            player.cam.unproject(mousePost);
            float velx = mousePost.x - b2body.body.getPosition().x;
            float vely = mousePost.y - b2body.body.getPosition().y;
            float length = (float) Math.sqrt(velx * velx + vely * vely);
            if (length != 0) {
                aim.x = velx / length;
                aim.y = vely / length;
            }

            System.out.println(mousePost);
            System.out.println("player position: " + b2body.body.getTransform().getPosition());



            if(player.timeSinceLastShot <= 0) {

                levelFactory.createBullet(
                        b2body.body.getPosition().x,
                        b2body.body.getPosition().y,
                        aim.x * 10, aim.y * 10, BulletComponent.Owner.PLAYER
                );
                player.timeSinceLastShot = player.shootDelay;
            }
        }
    }
}
