package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.ChapterRepository;
import FunFictionUserProject.funFictionUser.service.ChapterService;
import FunFictionUserProject.funFictionUser.view.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;

    public ChapterServiceImpl(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    @Override
    public Chapter save(Chapter chapter) {
        log.info("chapter by save in chapterService");
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter findById(Long id) {
        log.info("id by findById in chapterService");
        return chapterRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Chapter chapter) {
        log.info("chapter by delete in chapterService");
        chapterRepository.delete(chapter);
    }

    @Override
    public List<Chapter> findAll() {
        log.info("list chapter by findAll in chapterService");
        return chapterRepository.findAll();
    }

    @Transactional
    @Override
    public Chapter saveAndFlush(Chapter chapter) {
        log.info("chapter by saveAndFlush in chapterService");
        return chapterRepository.saveAndFlush(chapter);
    }

    @Override
    public List<Chapter> findChapterByFunFictionId(Long id) {
        log.info("id by findChapterByFunFictionId in chapterService");
        return chapterRepository.findChapterByFunFictionId(id);
    }
}
