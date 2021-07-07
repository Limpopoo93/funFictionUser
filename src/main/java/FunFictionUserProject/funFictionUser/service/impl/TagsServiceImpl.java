package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.TagsRepository;
import FunFictionUserProject.funFictionUser.service.TagsService;
import FunFictionUserProject.funFictionUser.view.Tags;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class TagsServiceImpl implements TagsService {
    private final TagsRepository tagsRepository;

    public TagsServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Transactional
    @Override
    public Tags save(Tags tags) {
        log.info("tags by save in tagsService");
        return tagsRepository.save(tags);
    }

    @Override
    public Tags findById(Long id) {
        log.info("id by findById in tagsService");
        return tagsRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Tags tags) {
        log.info("tags by delete in tagsService");
        tagsRepository.delete(tags);
    }

    @Override
    public List<Tags> findAll() {
        log.info("List Tags by findAll in tagsService");
        return tagsRepository.findAll();
    }

    @Transactional
    @Override
    public Tags saveAndFlush(Tags tags) {
        log.info("tags by saveAndFlush in tagsService");
        return tagsRepository.saveAndFlush(tags);
    }

    @Override
    public Tags findByTypeTags(String typeTag) {
        log.info("typeTag by findByTypeTags in tagsService");
        return tagsRepository.findByTypeTags(typeTag);
    }
}
