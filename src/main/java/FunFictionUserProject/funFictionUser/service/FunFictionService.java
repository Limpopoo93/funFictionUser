package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.FunFiction;

import java.util.List;

public interface FunFictionService {
    FunFiction save(FunFiction funFiction);

    FunFiction findById(Long id);

    void delete(FunFiction funFiction);

    List<FunFiction> findAll();

    FunFiction saveAndFlush(FunFiction funFiction);

    List<FunFiction> findFunFictionByUserIdOrderByRatingDesc(Long id);

    List<FunFiction> findByNameFunContainingOrderByRatingDesc(String name);

    List<FunFiction> findAllFun();

    List<FunFiction> findByNameFunLike(String name);
}
