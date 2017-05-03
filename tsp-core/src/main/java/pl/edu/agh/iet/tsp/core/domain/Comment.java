package pl.edu.agh.iet.tsp.core.domain;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Wojciech Pachuta.
 */
public class Comment {

    @Id
    private UUID id;

    private final UUID authorId;
    private final UUID postId;
    private final LocalDateTime creationTime;
    private final String content;

    public Comment(UUID authorId, UUID postId, LocalDateTime creationTime, String content) {
        this.authorId = authorId;
        this.postId = postId;
        this.creationTime = creationTime;
        this.content = content;
    }
}
