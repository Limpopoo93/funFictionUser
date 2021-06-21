package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.GenreRepository;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.view.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Genre genre) {
        genreRepository.delete(genre);
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre saveAndFlush(Genre genre) {
        return genreRepository.saveAndFlush(genre);
    }

    @Override
    public Genre findByTypeGenre(String typeGenre) {
        return genreRepository.findByTypeGenre(typeGenre);
    }
}
