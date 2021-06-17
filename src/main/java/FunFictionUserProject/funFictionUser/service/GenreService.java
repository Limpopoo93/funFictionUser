package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Genre;

import java.util.List;

public interface GenreService {
    Genre save(Genre genre);

    Genre findById(Long id);

    void delete(Genre genre);

    List<Genre> findAll();

    Genre saveAndFlush(Genre genre);
}
