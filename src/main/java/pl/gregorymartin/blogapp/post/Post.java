package pl.gregorymartin.blogapp.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.gregorymartin.blogapp.user.User;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Getter @Setter @NoArgsConstructor
public
class Post extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private boolean published;
    private String text;
    private String imageUrl;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(final String title, final boolean published, final String text, final String imageUrl) {
        this.title = title;
        this.published = published;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public void toUpdate(Post toUpdate) {
        this.title = toUpdate.title;
        this.published = toUpdate.published;
        this.text = toUpdate.text;
        this.imageUrl = toUpdate.imageUrl;
    }


}
