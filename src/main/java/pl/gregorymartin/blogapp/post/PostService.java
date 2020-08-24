package pl.gregorymartin.blogapp.post;

import org.springframework.stereotype.Service;
import pl.gregorymartin.blogapp.user.UserRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public
class PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public PostService(final PostRepository postRepository, final CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Post> showAllPosts(Pageable page){
        return postRepository.findAll(page).getContent();
    }
    public List<Post> showAllPostsByCategory(String category){
        return postRepository.findAllByCategory_Name(category);
    }
    public List<Post> showAllPostsByUser(String name){
        return postRepository.findAllByUser_Name(name);
    }
    public List<Post> showAllPostsByUserAndCategory(String name, String category){
        return postRepository.findAllByUser_NameAndCategory_Name(name, category);
    }

    public Post createPost(Post toAdd, String category, Long userId){
        if(!category.isEmpty()){
            Category categoryToAdd = categoryRepository.findByName(category);
            toAdd.setCategory(categoryToAdd);
        }
        toAdd.setUser(
                userRepository.findById(userId).get()
        );
        return postRepository.save(toAdd);
    }
    public Post editPost(Post toUpdate, String category, Long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        if(byId.isPresent()){
            if(!category.isEmpty()){
                byId.get().setCategory(
                        categoryRepository.findByName(category)
                );
            }
            byId.get().toUpdate(toUpdate);
        }

        return byId.get();
    }
    public boolean deletePost(Long postId){
        if(postRepository.existsById(postId)){
            postRepository.deleteById(postId);
        }
        return !postRepository.existsById(postId);
    }

}
