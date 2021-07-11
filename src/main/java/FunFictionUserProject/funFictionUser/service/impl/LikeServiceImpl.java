package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.LikeRepository;
import FunFictionUserProject.funFictionUser.service.LikeService;
import FunFictionUserProject.funFictionUser.view.LikeFunFic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public LikeFunFic save(LikeFunFic likeFunFic) {
        return likeRepository.save(likeFunFic);
    }

    @Override
    public LikeFunFic findById(Long id) {
        return likeRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(LikeFunFic likeFunFic) {
        likeRepository.delete(likeFunFic);
    }

    @Override
    public List<LikeFunFic> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public LikeFunFic saveAndFlush(LikeFunFic likeFunFic) {
        return likeRepository.saveAndFlush(likeFunFic);
    }

    @Override
    public LikeFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic) {
        return likeRepository.findByUserIdAndAndFunFictionId(idUser, idFunFic);
    }
}
