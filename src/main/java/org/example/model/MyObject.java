package org.example.model;

import javafx.beans.property.*;

public class MyObject {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty value = new SimpleIntegerProperty();
    private String owner;
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty size = new SimpleDoubleProperty();

    public MyObject() { }

    public long getId() { return id.get(); }
    public void setId(long id) { this.id.set(id); }
    public LongProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public int getValue() { return value.get(); }
    public void setValue(int value) { this.value.set(value); }
    public IntegerProperty valueProperty() { return value; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }

    public double getSize() { return size.get(); }
    public void setSize(double size) { this.size.set(size); }
    public DoubleProperty sizeProperty() { return size; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyObject)) return false;
        MyObject other = (MyObject) o;
        return getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId());
    }
}
