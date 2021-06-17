package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.CommentsRepository;
import FunFictionUserProject.funFictionUser.service.CommentsService;
import FunFictionUserProject.funFictionUser.view.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public Comments save(Comments comments) {
        return commentsRepository.save(comments);
    }

    @Override
    public Comments findById(Long id) {
        return commentsRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Comments comments) {
        commentsRepository.delete(comments);
    }

    @Override
    public List<Comments> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public Comments saveAndFlush(Comments comments) {
        return commentsRepository.saveAndFlush(comments);
    }
}
