package com.littleinferno.flowchart.node;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.littleinferno.flowchart.Connection;
import com.littleinferno.flowchart.DataType;
import com.littleinferno.flowchart.JsonManger;
import com.littleinferno.flowchart.codegen.CodeGen;
import com.littleinferno.flowchart.gui.Scene;
import com.littleinferno.flowchart.pin.Pin;

import java.util.UUID;

public abstract class Node extends VisWindow implements CodeGen {

    protected final VerticalGroup left;
    protected final VerticalGroup right;

    private NodeHandle nodeHandle;

    public Node(NodeHandle nodeHandle) {
        super(nodeHandle.name);

        this.nodeHandle = nodeHandle;
        this.nodeHandle.className = this.getClass().getName();

        setPosition(nodeHandle.x, nodeHandle.y);

        setName(nodeHandle.name);
        setKeepWithinStage(false);
        setKeepWithinParent(false);


        setWidth(200);

        if (nodeHandle.closable) {
            addCloseButton();
        }

        VisTable container = new VisTable();
        VisTable main = new VisTable();
        main.addSeparator();

        left = new VerticalGroup();
        left.top().left().fill();
        left.space(10);
        container.add(left).grow().width(100);

        container.addSeparator(true);

        right = new VerticalGroup();
        right.top().right().fill();
        right.space(10);
        container.add(right).grow().width(100).row();

        main.add(container).grow().row();

        add(main).grow();
        top();

    }

    public Pin addDataInputPin(final String name, DataType... possibleConvert) {
        Pin pin = new Pin(this, name, Connection.INPUT, possibleConvert);
        left.addActor(pin);
        pack();
        return pin;
    }

    public Pin addDataOutputPin(final String name, DataType... possibleConvert) {
        Pin pin = new Pin(this, name, Connection.OUTPUT, possibleConvert);
        right.addActor(pin);
        pack();
        return pin;
    }

    public Pin addExecutionInputPin() {
        return addExecutionInputPin("exec in");
    }

    public Pin addExecutionInputPin(final String name) {
        Pin pin = new Pin(this, name, Connection.INPUT, DataType.EXECUTION);
        left.addActor(pin);
        pack();
        return pin;
    }

    public Pin addExecutionOutputPin() {
        return addExecutionOutputPin("exec out");
    }

    public Pin addExecutionOutputPin(final String name) {
        Pin pin = new Pin(this, name, Connection.OUTPUT, DataType.EXECUTION);
        right.addActor(pin);
        pack();
        return pin;
    }

    public void removePin(final Pin pin) {
        left.removeActor(pin);
        right.removeActor(pin);
    }

    public void setTitle(String text) {
        getTitleLabel().setText(text);
    }

    public void removeCloseButton() {
        Array<Cell> cells = getTitleTable().getCells();
        cells.get(cells.size - 1).clearActor();
        cells.removeIndex(cells.size - 1);
    }

    @Override
    public void close() {

        SnapshotArray<Actor> leftChildren = left.getChildren();

        for (Actor actor : leftChildren) {
            if (actor instanceof Pin) {
                ((Pin) actor).disconnect();
            }
        }

        SnapshotArray<Actor> rightChildren = right.getChildren();

        for (Actor actor : rightChildren) {
            if (actor instanceof Pin) {
                ((Pin) actor).disconnect();
            }
        }

        getStage().getNodeManager().deleteNode(this);

        super.close();
    }

    public NodeHandle getHandle() {
        nodeHandle.x = getX();
        nodeHandle.y = getY();
        return nodeHandle;
    }

    public UUID getId() {
        return UUID.fromString(nodeHandle.id);
    }

    @Override
    public Scene getStage() {
        return (Scene) super.getStage();
    }

    static public class DefaultNodeSerializer<T extends Node> implements Json.Serializer<T> {
        @Override
        public void write(Json json, T object, Class knownType) {

            json.writeObjectStart();
            json.writeValue("x", object.getX());
            json.writeValue("y", object.getY());
            json.writeValue("id", object.getId().toString());
            json.writeObjectEnd();

        }

        @Override
        public T read(Json json, JsonValue jsonData, Class type) {

            float x = jsonData.get("x").asFloat();
            float y = jsonData.get("y").asFloat();
            UUID id = UUID.fromString(jsonData.get("id").asString());

            T node = null;

            try {
                //noinspection unchecked
                node = (T) type.newInstance();
                node.setPosition(x, y);
//                node(id);

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return node;
        }
    }

    static public class NodeHandle {
        public float x, y;
        public String name, className, id;
        public boolean closable;

        public NodeHandle(String name, boolean closable) {
            this(0, 0, name, null, closable);
        }

        public NodeHandle(float x, float y, String name, String className, boolean closable) {
            this.x = x;
            this.y = y;
            this.name = name;
            this.className = className;
            this.id = JsonManger.getID().toString();
            this.closable = closable;
        }

        public NodeHandle() {
        }

    }

    static public class NodeStyle {

        int width;

        public NodeStyle() {
        }

        public NodeStyle(int width) {
            this.width = width;
        }
    }
}
