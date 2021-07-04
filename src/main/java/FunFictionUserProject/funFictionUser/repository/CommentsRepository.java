package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTextCommentContaining(String name);

    List<Comments> findAllByChapterId(Long id);
}
