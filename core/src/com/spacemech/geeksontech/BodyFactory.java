//package com.spacemech.geeksontech;
//
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.PooledEngine;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.physics.box2d.*;
//import com.spacemech.geeksontech.components.*;
//
//
//import javax.xml.transform.TransformerFactory;
//
//public class BodyFactory {
//      private static BodyFactory thisInstance;
//      private World world;
//      public Body b2body;
//      public PooledEngine engine;
//    private TextureAtlas atlas;
//
//      private final float DEGTORAD = 0.0174533f;
//
//      private BodyFactory(World world) {
//
//          this.world = world;
//
//      }
//
//      public static BodyFactory getInstance(World world) {
//          if (thisInstance == null) {
//              thisInstance = new BodyFactory(world);
//          } else {
//              thisInstance.world = world;
//          }
//          return  thisInstance;
//      }
//
//      public Body createFigure() {
//          BodyDef bdef = new BodyDef();
//          bdef.position.set(32 / SpaceMech.PPM, 32 / SpaceMech.PPM);
//          bdef.type = BodyDef.BodyType.DynamicBody;
//          b2body = world.createBody(bdef);
//          FixtureDef fdef = new FixtureDef();
//
//          CircleShape shape = new CircleShape();
//          shape.setRadius(6 / SpaceMech.PPM);
//
//          fdef.shape = shape;
//          b2body.createFixture(fdef).setUserData(this);
//
//          b2body.createFixture(fdef);
//          return b2body;
//      }
//
//    public void createPlayer() {
//        Entity entity = engine.createEntity();
//        B2dBodyComponent b2dBodyComponent = engine.createComponent(B2dBodyComponent.class);
//        TransformComponent position = engine.createComponent(TransformComponent.class);
//        TextureComponent texture = engine.createComponent(TextureComponent.class);
//        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
//        PlayerComponent player = engine.createComponent(PlayerComponent.class);
//        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
//        TypeComponent type = engine.createComponent(TypeComponent.class);
//        StateComponent stateComponent = engine.createComponent(StateComponent.class);
//
//        b2dBodyComponent.body = this.createFigure();
//        b2dBodyComponent.body.setSleepingAllowed(false);
//        Animation animations = new Animation(0.1f, atlas.findRegion("badlogic"));
//        animations.setPlayMode(Animation.PlayMode.LOOP);
//        animationComponent.animations.put(StateComponent.STATE_NORMAL, animations);
//        stateComponent.set(StateComponent.STATE_NORMAL);
//
//
//
//        b2dBodyComponent.body.setUserData(entity);
//        entity.add(animationComponent);
//        entity.add(stateComponent);
//        entity.add(b2dBodyComponent);
//        entity.add(texture);
//        entity.add(type);
//        engine.addEntity(entity);
//
//        return entity;
//    }
//}
