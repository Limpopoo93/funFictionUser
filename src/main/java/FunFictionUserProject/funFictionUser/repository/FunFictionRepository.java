package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.FunFiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunFictionRepository extends JpaRepository<FunFiction, Long> {
    List<FunFiction> findFunFictionByUserId(Long id);

    List<FunFiction> findByNameFunContaining(String name);
}
