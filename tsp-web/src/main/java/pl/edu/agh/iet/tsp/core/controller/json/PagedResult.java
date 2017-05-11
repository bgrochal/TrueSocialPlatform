package pl.edu.agh.iet.tsp.core.controller.json;

import java.util.List;

/**
 * @author Wojciech Pachuta.
 */
public class PagedResult<T> {
    public final List<T> data;
    public final String next;

    public PagedResult(List<T> data, String next) {
        this.data = data;
        this.next = next;
    }
}
