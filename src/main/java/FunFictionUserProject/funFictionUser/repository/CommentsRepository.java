package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Comments;
import FunFictionUserProject.funFictionUser.view.FunFiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTextCommentContaining(String name);
}
