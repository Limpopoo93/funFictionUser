package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.TagsRepository;
import FunFictionUserProject.funFictionUser.service.TagsService;
import FunFictionUserProject.funFictionUser.view.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tags save(Tags tags) {
        return tagsRepository.save(tags);
    }

    @Override
    public Tags findById(Long id) {
        return tagsRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Tags tags) {
        tagsRepository.delete(tags);
    }

    @Override
    public List<Tags> findAll() {
        return tagsRepository.findAll();
    }

    @Override
    public Tags saveAndFlush(Tags tags) {
        return tagsRepository.saveAndFlush(tags);
    }

    @Override
    public Tags findByTypeTags(String typeTag) {
        return tagsRepository.findByTypeTags(typeTag);
    }
}
