package com.littleinferno.flowchart.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

public abstract class MainTab extends Tab {
    public MainTab() {
    }

    public MainTab(boolean savable) {
        super(savable);
    }

    public MainTab(boolean savable, boolean closeableByUser) {
        super(savable, closeableByUser);
    }

    public void render(Batch batch) {

    }
}