package pl.edu.agh.iet.tsp.core.controller.json;

import org.bson.types.ObjectId;

/**
 * @author Wojciech Pachuta.
 */
public class IdWrapper {
    public final String id;

    public IdWrapper(String id) {
        this.id = id;
    }

    public IdWrapper(ObjectId id) {
        this.id = id.toString();
    }
}
