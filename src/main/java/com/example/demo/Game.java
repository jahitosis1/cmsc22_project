package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.example.demo.ui.TestMainMenu;
import javafx.scene.Camera;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.lang.invoke.VarHandle;
import java.security.PrivilegedAction;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

//import java.io.IOException;

public class Game extends GameApplication {
    private int canJump = 2;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(32 * 30);
        settings.setHeight(32 * 20);
        settings.setTitle("CMSC 22 Project Prototype");
        settings.setVersion("0.1");
//        settings.setPreserveResizeRatio(true);
//        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
//            @Override
//            public LoadingScene newLoadingScene() {
//                return super.newLoadingScene();
//            }

            @Override
            public FXGLMenu newMainMenu() {
                return new TestMainMenu();
            }
        });
    }
    @Override
    protected void initInput() {
        onKey(KeyCode.D, () -> {
            player.getComponent(PhysicsComponent.class).setVelocityX(300);
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.A, () -> {
            player.getComponent(PhysicsComponent.class).setVelocityX(-300);
            inc("pixelsMoved", -5);
        });

        onKeyDown(KeyCode.W, () -> {
            if (canJump > 0) {
                player.getComponent(PhysicsComponent.class).setVelocityY(-450);
                canJump--;
            }
            inc("pixelsMoved", +5);
        });

        onKeyDown(KeyCode.S, () -> {
            player.getComponent(PhysicsComponent.class).setVelocityX(0);
        });

        onKeyDown(KeyCode.F, () -> {
            play("bg-calm.mp3");
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    private Entity player;
    private Camera camera;

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new simpleFactory());
        FXGL.setLevelFromMap("test_map.tmx");
        player = getGameWorld().spawn("player", 50, -100);
        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        getGameScene().getViewport().setZoom(1.5);
        getGameScene().getViewport().setBounds(0,0,32 * 30,32 * 18);
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph

    }
    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameTypes.PLAYER, GameTypes.COIN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameTypes.PLAYER, GameTypes.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
                showMessage("Level Complete", () -> {
                    getGameController().exit();
//                    System.out.println("Dialog Closed!");
                });
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameTypes.PLAYER, GameTypes.WATER) {
            @Override
            protected void onCollisionBegin(Entity player, Entity water) {
                System.out.println("water");
                player.setPosition(50, -100);
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameTypes.PLAYER, GameTypes.WALL) {
            @Override
            protected void onCollisionBegin(Entity player, Entity wall) {
                canJump++;
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameTypes.PLAYER, GameTypes.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity player, Entity platform) {
                canJump = 2;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}