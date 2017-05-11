package pl.edu.agh.iet.tsp.core.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Wojciech Pachuta.
 */
@Entity
@Indexes(@Index(value = Comment.POST_ID, fields = @Field(Comment.POST_ID)))
public class Comment {

    public static final String AUTHOR_ID = "authorId";
    public static final String POST_ID = "postId";
    public static final String CREATION_TIME = "creationTime";
    public static final String CONTENT = "content";

    @Id
    private ObjectId id;

    private final ObjectId authorId;
    private final ObjectId postId;
    private final LocalDateTime creationTime;
    private final String content;
    private final LocalDateTime lastModificationTime;

    public Comment(){
        this(null, null, null, null, null, null);
    }

    public Comment(ObjectId authorId, ObjectId postId, LocalDateTime creationTime, String content) {
        this.authorId = authorId;
        this.postId = postId;
        this.creationTime = creationTime;
        this.content = content;
        this.lastModificationTime = null;
    }

    public Comment(ObjectId id, ObjectId authorId, ObjectId postId, LocalDateTime creationTime, String content, LocalDateTime lastModificationTime) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.creationTime = creationTime;
        this.content = content;
        this.lastModificationTime = lastModificationTime;
    }

    public ObjectId getId() {
        return id;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public ObjectId getPostId() {
        return postId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getContent() {
        return content;
    }

    public Optional<LocalDateTime> getLastModificationTime() {
        return Optional.ofNullable(lastModificationTime);
    }
}
