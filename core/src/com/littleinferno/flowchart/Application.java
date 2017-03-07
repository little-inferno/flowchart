package com.littleinferno.flowchart;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kotcrab.vis.ui.VisUI;
import com.littleinferno.flowchart.project.Project;
import com.littleinferno.flowchart.screen.ScreenManager;


public class Application extends Game {

    private Screen scene;
    private static boolean change;

    private Project project;
    private ScreenManager sm;
    private final float SCALE = 1.f;


    @Override
    public void create() {
        sm = new ScreenManager(this);
//             Project project = Project.createProject("test", "flowchart_projects", new JSCodeGenerator(), new JSCodeExecution());
        Project.load("test", Gdx.files.external("flowchart_projects"));

        //  MenuScreen menuScreen = new MenuScreen();
        // this.scene = ;
        //  scene.show();
        setScreen(Project.instance().getProjectScreen());

    }

    //   @Override
    // public void render() {
    // scene.render(Gdx.graphics.getDeltaTime());
//    }


    @Override
    public void dispose() {
        super.dispose();
//        scene.dispose();
        sm.dispose();
        VisUI.dispose();
    }


}
