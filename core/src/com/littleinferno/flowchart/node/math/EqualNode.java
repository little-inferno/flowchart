package com.littleinferno.flowchart.node.math;

import com.littleinferno.flowchart.codegen.BaseCodeGenerator;
import com.littleinferno.flowchart.pin.Pin;

public class EqualNode extends LogicNode {
    public EqualNode() {
        super("equals", Pin.DEFAULT_CONVERT);
    }

    @Override
    public String gen(BaseCodeGenerator builder, Pin with) {
        Pin.Connector aConnector = a.getConnector();
        Pin.Connector bConnector = b.getConnector();

        String aStr = aConnector.parent.gen(builder, aConnector.pin);
        String bStr = bConnector.parent.gen(builder, bConnector.pin);

        return builder.makeEq(aStr, bStr);
    }
}
