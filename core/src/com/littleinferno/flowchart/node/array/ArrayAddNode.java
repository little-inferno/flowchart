package com.littleinferno.flowchart.node.array;

import com.littleinferno.flowchart.DataType;
import com.littleinferno.flowchart.codegen.BaseCodeGenerator;
import com.littleinferno.flowchart.node.Node;
import com.littleinferno.flowchart.pin.Pin;

public class ArrayAddNode extends Node {

    private Pin next;
    private Pin array;
    private Pin item;
    private Pin length;

    public ArrayAddNode() {
        this(new NodeHandle("Add", true));
    }

    public ArrayAddNode(NodeHandle nodeHandle) {
        super(nodeHandle);

        next = addExecutionOutputPin();
        addExecutionInputPin();
        array = addDataInputPin("array", Pin.DEFAULT_CONVERT);
        array.setArray(true);

        item = addDataInputPin("item", Pin.DEFAULT_CONVERT);
        length = addDataOutputPin("length", DataType.INT);
    }

    @Override
    public String gen(BaseCodeGenerator builder, Pin with) {
        if (with == length) {
            Pin.Connector arr = array.getConnector();
            return builder.makeGetArrayLength(arr.parent.gen(builder, arr.pin));
        }

        String s = builder.makeAddArrayItem(array, item);

        Pin.Connector n = next.getConnector();
        String nextStr = n != null ? n.parent.gen(builder, n.pin) : "";

        return String.format("%s%s", s, nextStr);
    }
}
