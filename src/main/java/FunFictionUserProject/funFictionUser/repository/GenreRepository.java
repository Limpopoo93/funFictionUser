package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByTypeGenre(String typeGenre);
}
