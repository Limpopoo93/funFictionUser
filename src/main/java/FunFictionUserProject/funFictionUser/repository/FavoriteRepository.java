package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorites, Long> {
    Favorites findByFunFictionId(Long id);

    List<Favorites> findAllByUserId(Long id);
}
