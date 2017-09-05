package iad.eventprog;

import java.util.EventListener;

public interface ImageChangedListener extends EventListener {

    void imageChanged(ImageChangedEvent event);


}
