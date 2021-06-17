package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Tags;

import java.util.List;

public interface TagsService {
    Tags save(Tags tags);

    Tags findById(Long id);

    void delete(Tags tags);

    List<Tags> findAll();

    Tags saveAndFlush(Tags tags);
}
