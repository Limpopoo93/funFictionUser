package FunFictionUserProject.funFictionUser.repository;

import FunFictionUserProject.funFictionUser.view.FunFiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;

@Repository
public interface FunFictionRepository extends JpaRepository<FunFiction, Long> {
    @Query("select f from FunFiction f order by f.rating desc ")
    List<FunFiction> findAllFun();

    List<FunFiction> findFunFictionByUserIdOrderByRatingDesc(Long id);

    List<FunFiction> findByNameFunContainingOrderByRatingDesc(String name);
    @Query("select f from FunFiction f where f.nameFun LIKE  %:name%")
    List<FunFiction> findByNameFunLike(String name);
}
