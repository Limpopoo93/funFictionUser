package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.Comments;

import java.util.List;

public interface CommentsService {
    Comments save(Comments comments);

    Comments findById(Long id);

    void delete(Comments comments);

    List<Comments> findAll();

    Comments saveAndFlush(Comments comments);
}
