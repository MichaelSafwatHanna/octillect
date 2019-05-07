package octillect.models.builders;

import java.util.function.Consumer;

import javafx.collections.ObservableList;

import octillect.models.Column;
import octillect.models.TaskBase;

public class ColumnBuilder implements Builder<Column, ColumnBuilder> {

    public String id;
    public String name;
    public ObservableList<TaskBase> tasks;

    @Override
    public ColumnBuilder with(Consumer<ColumnBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    @Override
    public Column build() {
        return new Column(id, name, tasks);
    }

    public ColumnBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ColumnBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ColumnBuilder withTasks(ObservableList<TaskBase> tasks) {
        this.tasks = tasks;
        return this;
    }

}
