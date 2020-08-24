package pl.gregorymartin.blogapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gregorymartin.blogapp.post.Post;
import pl.gregorymartin.blogapp.post.PostService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
class BlogApi {
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(BlogApi.class);

    public BlogApi(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    ResponseEntity<List<Post>> readAllPosts(Pageable page) {
        logger.warn("Exposing all the POSTS!");
        return ResponseEntity.ok(postService.showAllPosts(page));
    }
    @GetMapping
    ResponseEntity<List<Post>> readAllPostsByCategory(
            @RequestHeader String category
    ) {
        logger.warn("Exposing all the POSTS with name: " + category);
        return ResponseEntity.ok(postService.showAllPostsByCategory(category));
    }
    @GetMapping
    ResponseEntity<List<Post>> readAllPostsByUser(
            @RequestHeader String nameOfUser
    ) {
        logger.warn("Exposing all the POSTS with NAME of USER: " + nameOfUser);
        return ResponseEntity.ok(postService.showAllPostsByUser(nameOfUser));
    }
    @GetMapping
    ResponseEntity<List<Post>> readAllPostsByNameOfUserAndCategory(
            @RequestHeader String nameOfUser,
            @RequestHeader String nameOfCategory
    ) {
        logger.warn("Exposing all the POSTS with NAME of USER: " + nameOfUser + " and CATEGORY " + nameOfCategory);
        return ResponseEntity.ok(postService.showAllPostsByUserAndCategory(nameOfUser, nameOfCategory));
    }
    @PostMapping
    ResponseEntity<Post> createPost(
            @RequestBody @Valid Post toCreate,
            @RequestHeader String nameOfCategory,
            @RequestHeader Long userId
    ) {
        Post result = postService.createPost(toCreate , nameOfCategory, userId);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @PatchMapping("/{postId}")
    ResponseEntity<Post> updatePost(
            @RequestBody @Valid Post toUpdate,
            @RequestHeader String nameOfCategory,
            @RequestHeader Long postId
    ) {
        Post result = postService.editPost(toUpdate, nameOfCategory, postId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{postId}")
    ResponseEntity<Post> deletePost(
            @RequestHeader Long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
