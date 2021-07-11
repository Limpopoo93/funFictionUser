package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.LikeFunFic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeFunFic, Long> {
    LikeFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic);
}
