package com.example.demo.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import kotlin.contracts.Returns;

public class TestMainMenu extends FXGLMenu {
    public TestMainMenu() {
        super(MenuType.MAIN_MENU);
    }

    protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
        return new Button();
    }
    protected Button createActionButton(String s, Runnable runnable) {
        return new Button();
    }
    protected Node createBackground(double v, double v1){
        return FXGL.texture("background/alice2.jpg");
    }
    protected Node ProfileView(String s){
        return new Rectangle();
    }
    protected Node createTitleView(String s){
        return new Rectangle();
    }
    protected Node createVersionView(String s){
        return new Rectangle();
    }
}
