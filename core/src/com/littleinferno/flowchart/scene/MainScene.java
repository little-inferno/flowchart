package com.littleinferno.flowchart.scene;


import com.littleinferno.flowchart.node.IntegerNode;
import com.littleinferno.flowchart.node.JSNode;
import com.littleinferno.flowchart.node.NodeManager;
import com.littleinferno.flowchart.node.PrintNode;
import com.littleinferno.flowchart.project.Project;

public class MainScene extends Scene {

    @SuppressWarnings("WeakerAccess")
    public MainScene(SceneHandle sceneHandle, Project project) {
        super(sceneHandle, project);
        getNodeManager().getBeginNode().setPosition(500, 200);
        getNodeManager().createNode(IntegerNode.class).setPosition(500, 200);
        getNodeManager().createNode(IntegerNode.class).setPosition(500, 200);
        getNodeManager().createNode(PrintNode.class).setPosition(500, 200);


        JSNode.nodeManager = getNodeManager();
    }

    @SuppressWarnings("unused")
    public MainScene(NodeManager nodeManager, Project project) {
        this(new SceneHandle(nodeManager, "main", false), project);
    }
}
