package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.CommentsRepository;
import FunFictionUserProject.funFictionUser.service.CommentsService;
import FunFictionUserProject.funFictionUser.view.Comments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Transactional
    @Override
    public Comments save(Comments comments) {
        log.info("comments by save in commentsService");
        return commentsRepository.save(comments);
    }

    @Override
    public Comments findById(Long id) {
        log.info("id by findById in commentsService");
        return commentsRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Comments comments) {
        log.info("comments by delete in commentsService");
        commentsRepository.delete(comments);
    }

    @Override
    public List<Comments> findAll() {
        log.info("list comments by findAll in commentsService");
        return commentsRepository.findAll();
    }

    @Transactional
    @Override
    public Comments saveAndFlush(Comments comments) {
        log.info("comments by saveAndFlush in commentsService");
        return commentsRepository.saveAndFlush(comments);
    }

    @Override
    public List<Comments> findByTextCommentContaining(String name) {
        log.info("name by findByTextCommentContaining in commentsService");
        return commentsRepository.findByTextCommentContaining(name);
    }

    @Override
    public List<Comments> findAllByChapterId(Long id) {
        log.info("id by findAllByChapterId in commentsService");
        return commentsRepository.findAllByChapterId(id);
    }

    @Override
    public List<Comments> findAllByUserId(Long id) {
        log.info("id by findAllByUserId in commentsService");
        return commentsRepository.findAllByUserId(id);
    }

}
