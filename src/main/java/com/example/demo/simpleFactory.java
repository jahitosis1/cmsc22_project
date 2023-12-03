package com.example.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.action.Action;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class simpleFactory implements EntityFactory{
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        
        return FXGL.entityBuilder(data)
                .type(GameTypes.PLAYER)
                .viewWithBBox(new Rectangle(45, 45, Color.BLUE))
                .with(physics)
                .with(new CollidableComponent(true))
                .build();

    }
    @Spawns("coin")
    public Entity newCoin(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameTypes.COIN)
                .viewWithBBox(new Circle(data.<Integer>get("width") / 2, Color.GOLD))
                .with(new CollidableComponent(true))
                .build();

    }
    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameTypes.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();

    }
    @Spawns("door")
    public Entity newDoor(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameTypes.DOOR)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .build();

    }
    @Spawns("water")
    public Entity newWater(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(GameTypes.WATER)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .build();

    }
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data){

        return FXGL.entityBuilder(data)
                .type(GameTypes.ENEMY)
                .viewWithBBox(new Rectangle(45, 45, Color.BLUE))
                .with(new CollidableComponent(true))
                .build();

    }
}
