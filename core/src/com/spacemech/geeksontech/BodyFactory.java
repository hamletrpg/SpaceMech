package com.spacemech.geeksontech;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {

    // making this class a singleton
    private static BodyFactory thisInstance;
    private World world;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) {
            thisInstance = new BodyFactory(world);
        } else {
            thisInstance.world = world;
        }
        return thisInstance;
    }

    public void makeSensorFixture(Body body, float size){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(size);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
        circleShape.dispose();
    }


    Body createOval(float x, float y, float w, boolean dynamic){
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        if(dynamic){
            boxBodyDef.type = BodyDef.BodyType.DynamicBody;
        }else{
            boxBodyDef.type = BodyDef.BodyType.StaticBody;
        }

        boxBodyDef.position.x = x;
        boxBodyDef.position.y = y;
        boxBodyDef.fixedRotation = true;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(w /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0f;

        boxBody.createFixture(fixtureDef);
        circleShape.dispose();

        return boxBody;

    }


}
