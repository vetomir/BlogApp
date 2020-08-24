package pl.gregorymartin.blogapp.post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable page);
    List<Post> findAllByCategory_Name(final String category_name);
    List<Post> findAllByUser_Name(final String user_name);
    List<Post> findAllByUser_NameAndCategory_Name(final String user_name, String category);
}
