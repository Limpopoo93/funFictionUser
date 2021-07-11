package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.FavoriteRepository;
import FunFictionUserProject.funFictionUser.service.FavoriteService;
import FunFictionUserProject.funFictionUser.view.Favorites;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Favorites save(Favorites favorites) {
        return favoriteRepository.save(favorites);
    }

    @Override
    public Favorites findById(Long id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Favorites favorites) {
        favoriteRepository.delete(favorites);
    }

    @Override
    public List<Favorites> findAll() {
        return favoriteRepository.findAll();
    }

    @Override
    public Favorites saveAndFlush(Favorites favorites) {
        return favoriteRepository.saveAndFlush(favorites);
    }

    @Override
    public Favorites findByFunFictionId(Long id) {
        return favoriteRepository.findByFunFictionId(id);
    }

    @Override
    public List<Favorites> findAllByUserId(Long id) {
        return favoriteRepository.findAllByUserId(id);
    }
}
