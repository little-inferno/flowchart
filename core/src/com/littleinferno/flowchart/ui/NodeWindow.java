package com.littleinferno.flowchart.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class NodeWindow extends Window {

    boolean fill = false;

    float oldX;
    float oldY;
    float oldH;
    float oldW;


    public NodeWindow(Skin skin) {
        this("main", skin);
    }

    public NodeWindow(String title, Skin skin) {
        super(title, skin);

        setSize(300, 300);
        setResizable(true);
        setKeepWithinStage(false);
        final TextButton button = new TextButton("\u25F1", skin);
        getTitleTable().add(button).size(getPadTop());
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!fill) {
                    oldX = getX();
                    oldY = getY();
                    oldW = getWidth();
                    oldH = getHeight();
                    button.setText("\u25F1");
                    setPosition(0, 0);
                    setMovable(false);
                } else {
                    setSize(oldW, oldH);
                    setPosition(oldX, oldY);
                    button.setText("\u25F3");
                    setMovable(true);
                }

                NodeWindow.this.setFillParent(fill = !fill);
            }
        });

    }
}