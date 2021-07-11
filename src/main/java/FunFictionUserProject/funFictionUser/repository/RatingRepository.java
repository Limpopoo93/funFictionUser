package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.RatingFunFic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<RatingFunFic, Long> {

    RatingFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic);
}
