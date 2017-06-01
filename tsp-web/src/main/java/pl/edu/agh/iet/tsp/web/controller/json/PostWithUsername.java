package pl.edu.agh.iet.tsp.web.controller.json;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.Post;

import java.time.LocalDateTime;

/**
 * @author Wojciech Pachuta.
 */
public class PostWithUsername {

    //post data
    public final ObjectId id;
    public final ObjectId authorId;
    public final LocalDateTime creationTime;
    public final LocalDateTime lastModificationTime;
    public final String title;
    public final String category;
    public final String content;

    //additional data
    public final String authorUsername;

    public PostWithUsername(Post post, String authorUsername) {
        this.id = post.getId();
        this.authorId = post.getAuthorId();
        this.creationTime = post.getCreationTime();
        this.lastModificationTime = post.getLastModificationTime().orElse(null);
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.content = post.getContent();
        this.authorUsername = authorUsername;
    }
}
