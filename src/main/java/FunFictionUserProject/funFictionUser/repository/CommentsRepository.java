package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
