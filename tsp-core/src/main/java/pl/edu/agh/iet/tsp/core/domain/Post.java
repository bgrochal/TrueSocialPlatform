package pl.edu.agh.iet.tsp.core.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public class Post {

    @Id
    private UUID id;

    private final UUID authorId;
    private final LocalDateTime creationTime;
    private final String title;
    private final String category;
    private final String content;

    public Post(UUID authorId, LocalDateTime creationTime, String title, String category, String content) {
        this.authorId = authorId;
        this.creationTime = creationTime;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
