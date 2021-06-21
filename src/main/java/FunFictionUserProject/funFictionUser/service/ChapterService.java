package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Chapter;

import java.util.List;

public interface ChapterService {
    Chapter save(Chapter chapter);

    Chapter findById(Long id);

    void delete(Chapter chapter);

    List<Chapter> findAll();

    Chapter saveAndFlush(Chapter chapter);

    List<Chapter> findChapterByFunFictionId(Long id);
}
