package iad.eventprog;

import java.awt.*;
import java.util.EventObject;

public class ImageChangedEvent extends EventObject {


    private String name;
    private Dimension dim;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ImageChangedEvent(Object source) {
        this(source, null, null);
    }

    public ImageChangedEvent(Object source, String name, Dimension dim) {
        super(source);

        this.name = name;
        this.dim = dim;
    }


    public String getName() {
        return name;
    }

    public Dimension getDim() {
        return dim;
    }
}
