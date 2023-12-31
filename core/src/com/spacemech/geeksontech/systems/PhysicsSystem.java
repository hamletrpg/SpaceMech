package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.TransformComponent;

import javax.xml.transform.TransformerFactory;

public class PhysicsSystem extends IteratingSystem {
    private static final float MAX_STEP_TIME = 1/45F;
    private static float accumulator = 0f;
    private World world;
    private Array<Entity> bodiesQueue;

    private ComponentMapper<B2dBodyComponent> b2dbodyComponent = ComponentMapper.getFor(B2dBodyComponent.class);
    private ComponentMapper<TransformComponent> transforComponent = ComponentMapper.getFor(TransformComponent.class);

    @SuppressWarnings("unchecked")
    public PhysicsSystem(World world) {
        super(Family.all(B2dBodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<Entity>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if(accumulator >= MAX_STEP_TIME){
            world.step(MAX_STEP_TIME, 6,2);
            accumulator -= MAX_STEP_TIME;

            for (Entity entity : bodiesQueue) {
                TransformComponent transformComponentInsideLoop = transforComponent.get(entity);
                B2dBodyComponent b2dBodyComponentInsideLoop = b2dbodyComponent.get(entity);
                Vector2 position = b2dBodyComponentInsideLoop.body.getPosition();

                transformComponentInsideLoop.position.x = position.x;
                transformComponentInsideLoop.position.y = position.y;
                transformComponentInsideLoop.rotation = b2dBodyComponentInsideLoop.body.getAngle() * MathUtils.radiansToDegrees;
                if(b2dBodyComponentInsideLoop.isDead) {
                    System.out.println("removing entity");
                    world.destroyBody(b2dBodyComponentInsideLoop.body);
                    getEngine().removeEntity(entity);
                }
            }
        }
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodiesQueue.add(entity);
    }

}
