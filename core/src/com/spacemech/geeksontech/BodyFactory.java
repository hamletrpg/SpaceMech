package com.spacemech.geeksontech;

import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {
      private static BodyFactory thisInstance;
      private World world;
      public Body b2body;

      private final float DEGTORAD = 0.0174533f;

      private BodyFactory(World world) {

          this.world = world;

      }

      public static BodyFactory getInstance(World world) {
          if (thisInstance == null) {
              thisInstance = new BodyFactory(world);
          } else {
              thisInstance.world = world;
          }
          return  thisInstance;
      }

      public void createPlayer() {
          BodyDef bdef = new BodyDef();
          bdef.position.set(32 / SpaceMech.PPM, 32 / SpaceMech.PPM);
          bdef.type = BodyDef.BodyType.DynamicBody;
          b2body = world.createBody(bdef);

          FixtureDef fdef = new FixtureDef();
          CircleShape shape = new CircleShape();
          shape.setRadius(6 / SpaceMech.PPM);

          fdef.shape = shape;
          b2body.createFixture(fdef).setUserData(this);

          b2body.createFixture(fdef);
      }
}
