package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Favorites;

import java.util.List;

public interface FavoriteService {
    Favorites save(Favorites favorites);

    Favorites findById(Long id);

    void delete(Favorites favorites);

    List<Favorites> findAll();

    Favorites saveAndFlush(Favorites favorites);

    Favorites findByFunFictionId(Long id);

    List<Favorites> findAllByUserId(Long id);
}
