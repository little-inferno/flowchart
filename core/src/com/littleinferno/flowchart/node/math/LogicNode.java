package com.littleinferno.flowchart.node.math;


import com.littleinferno.flowchart.DataType;
import com.littleinferno.flowchart.node.Node;
import com.littleinferno.flowchart.pin.Pin;

public abstract class LogicNode extends Node {

    protected final Pin a;
    protected final Pin b;

    public LogicNode(NodeHandle nodeHandle, DataType... possibleConvert) {
        super(nodeHandle);

        a = addDataInputPin("A", possibleConvert);
        b = addDataInputPin("B", possibleConvert);
        addDataOutputPin("result", DataType.BOOL);

        Pin.PinListener listener = t -> {
            a.setType(t);
            b.setType(t);
        };

        a.addListener(listener);
        b.addListener(listener);
    }
}
