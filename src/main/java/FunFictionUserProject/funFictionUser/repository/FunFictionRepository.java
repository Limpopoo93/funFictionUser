package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.FunFiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunFictionRepository extends JpaRepository<FunFiction, Long> {
}
