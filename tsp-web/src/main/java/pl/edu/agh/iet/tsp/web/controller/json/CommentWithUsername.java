package pl.edu.agh.iet.tsp.web.controller.json;

import org.bson.types.ObjectId;
import pl.edu.agh.iet.tsp.database.domain.Comment;

import java.time.LocalDateTime;

/**
 * @author Wojciech Pachuta.
 */
public class CommentWithUsername {

    //comment data
    public final ObjectId id;
    public final ObjectId authorId;
    public final ObjectId postId;
    public final LocalDateTime creationTime;
    public final String content;
    public final LocalDateTime lastModificationTime;

    //additional data
    public final String authorUsername;


    public CommentWithUsername(Comment comment, String authorUsername) {
        this.id = comment.getId();
        this.authorId = comment.getAuthorId();
        this.postId = comment.getPostId();
        this.creationTime = comment.getCreationTime();
        this.content = comment.getContent();
        this.lastModificationTime = comment.getLastModificationTime().orElse(null);
        this.authorUsername = authorUsername;
    }
}
