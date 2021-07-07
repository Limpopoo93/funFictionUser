package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.GenreRepository;
import FunFictionUserProject.funFictionUser.service.GenreService;
import FunFictionUserProject.funFictionUser.view.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        log.info("genre by save in genreService");
        return genreRepository.save(genre);
    }

    @Override
    public Genre findById(Long id) {
        log.info("id by findById in genreService");
        return genreRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Genre genre) {
        log.info("genre by delete in genreService");
        genreRepository.delete(genre);
    }

    @Override
    public List<Genre> findAll() {
        log.info("list genre by findAll in genreService");
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public Genre saveAndFlush(Genre genre) {
        log.info("genre by saveAndFlush in genreService");
        return genreRepository.saveAndFlush(genre);
    }

    @Override
    public Genre findByTypeGenre(String typeGenre) {
        log.info("typeGenre by findByTypeGenre in genreService");
        return genreRepository.findByTypeGenre(typeGenre);
    }
}
