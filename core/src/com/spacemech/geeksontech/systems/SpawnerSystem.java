package com.spacemech.geeksontech.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.spacemech.geeksontech.BodyFactory;
import com.spacemech.geeksontech.components.B2dBodyComponent;
import com.spacemech.geeksontech.components.EnemyComponent;
import com.spacemech.geeksontech.components.SpawnerComponent;

public class SpawnerSystem extends IteratingSystem {
    private float timer = 0f;
    private float delay = 0.5f;
    private ComponentMapper<SpawnerComponent> spawnerComponent;
    private ComponentMapper<EnemyComponent> enemyComponent;
    private ComponentMapper<B2dBodyComponent> b2dComponent;
    private BodyFactory bodyFactory;
    public SpawnerSystem(BodyFactory bodyFact) {
        super(Family.all(SpawnerComponent.class).get());
        spawnerComponent = ComponentMapper.getFor(SpawnerComponent.class);
        enemyComponent = ComponentMapper.getFor(EnemyComponent.class);
        b2dComponent = ComponentMapper.getFor(B2dBodyComponent.class);
        bodyFactory = bodyFact;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComp = enemyComponent.get(entity);
        B2dBodyComponent b2dComp = b2dComponent.get(entity);
        SpawnerComponent spawnerComp = spawnerComponent.get(entity);

        if (this.timer > 0) {
            this.timer -= deltaTime;
        }

        if (this.timer <= 0) {
            bodyFactory.createEnemy(
                    b2dComp.body.getPosition().x,
                    b2dComp.body.getPosition().y,
                    0, -10
            );
            this.timer = this.delay;
            System.out.println(this.timer);
            System.out.println(this.delay);
        }
    }
}
