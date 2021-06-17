package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.ChapterRepository;
import FunFictionUserProject.funFictionUser.service.ChapterService;
import FunFictionUserProject.funFictionUser.view.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter findById(Long id) {
        return chapterRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Chapter chapter) {
        chapterRepository.delete(chapter);
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public Chapter saveAndFlush(Chapter chapter) {
        return chapterRepository.saveAndFlush(chapter);
    }
}
