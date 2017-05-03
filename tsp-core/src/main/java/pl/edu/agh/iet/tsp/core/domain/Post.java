package pl.edu.agh.iet.tsp.core.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.time.LocalDateTime;

/**
 * @author Wojciech Pachuta.
 */
@Entity
@Indexes({
        @Index(value = Post.AUTHOR_ID, fields = @Field(Post.AUTHOR_ID)),
        @Index(value = Post.CATEGORY, fields = @Field(Post.CATEGORY))
        //TODO sort by date (maybe possible insertion order optimization)
})
public class Post {

    public static final String AUTHOR_ID = "authorId";
    public static final String CREATION_TIME = "creationTime";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String CONTENT = "content";

    @Id
    private ObjectId id;

    private final ObjectId authorId;
    private final LocalDateTime creationTime;
    private final String title;
    private final String category;
    private final String content;

    public Post() {
        this.authorId = null;
        this.creationTime = null;
        this.title = null;
        this.category = null;
        this.content = null;
    }

    public Post(ObjectId authorId, LocalDateTime creationTime, String title, String category, String content) {
        this.authorId = authorId;
        this.creationTime = creationTime;
        this.title = title;
        this.category = category;
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }
}
