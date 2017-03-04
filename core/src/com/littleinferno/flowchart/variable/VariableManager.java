package com.littleinferno.flowchart.variable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.kotcrab.vis.ui.util.adapter.ArrayListAdapter;
import com.kotcrab.vis.ui.widget.ListView;
import com.kotcrab.vis.ui.widget.VisTable;
import com.littleinferno.flowchart.DataType;
import com.littleinferno.flowchart.codegen.BaseCodeGenerator;

import java.util.ArrayList;

public class VariableManager {

    private ArrayList<Variable> variables;
    private int counter;
    private UI ui;

    public VariableManager(VariableManagerHandle variableManagerHandle) {
        this();
        counter = variableManagerHandle.counter;

        Stream.of(variableManagerHandle.variableHandles)
                .map(Variable.VariableHandle.class::cast)
                .forEach(this::createVariable);
    }

    public VariableManager() {
        variables = new ArrayList<>();
        counter = 0;
        ui = new UI(variables);
    }

    public Variable createVariable(Variable.VariableHandle variableHandle) {
        Variable variable =
                new Variable(variableHandle.name, variableHandle.dataType, variableHandle.isArray);

        variables.add(variable);
        ui.update();

        return variable;
    }

    public Variable createVariable() {
        Variable variable = new Variable("newVar" + counter++, DataType.BOOL, false);

        variables.add(variable);
        ui.update();

        return variable;
    }

    void removeVariable(Variable variable) {
        variable.destroy();
        variables.remove(variable);
        ui.update();
    }

    public String gen(BaseCodeGenerator builder) {
        final StringBuilder stringBuilder = new StringBuilder();

        Stream.of(variables)
                .forEach(variable -> stringBuilder.append(variable.gen(builder)));

        return stringBuilder.toString();
    }

    public Variable getVariable(String name) {
        return Stream.of(variables)
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Cannot find varable with name:\"" + name + "\""));
    }

    public UI getUi() {
        return ui;
    }

    public VariableManagerHandle getHandle() {
        return new VariableManagerHandle(counter,
                Stream.of(variables)
                        .map(Variable::getHandle)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }

    public static class UI {

        private ArrayListAdapter<Variable, VisTable> variableAdapter;

        private final VisTable detailsTable;

        public VisTable getVarTable() {
            return varTable;
        }

        public VisTable getDetailsTable() {
            return detailsTable;
        }

        public void update() {
            variableAdapter.itemsChanged();
        }

        private final VisTable varTable;

        public UI(ArrayList<Variable> variables) {
            variableAdapter = new VariableListAdapter(variables);

            detailsTable = new VisTable(true);
            varTable = new VisTable(true);

            ListView<Variable> view = new ListView<>(variableAdapter);

            view.setItemClickListener(item -> {
                detailsTable.clearChildren();
                detailsTable.add(item.getTable()).grow();
            });

            varTable.add(view.getMainTable()).grow().row();
        }
    }

    public static class VariableManagerHandle {
        int counter;
        ArrayList<Variable.VariableHandle> variableHandles;

        public VariableManagerHandle() {
        }

        public VariableManagerHandle(int counter, ArrayList<Variable.VariableHandle> variableHandles) {
            this.counter = counter;
            this.variableHandles = variableHandles;
        }
    }
}
