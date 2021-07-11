package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.LikeFunFic;

import java.util.List;

public interface LikeService {
    LikeFunFic save(LikeFunFic likeFunFic);

    LikeFunFic findById(Long id);

    void delete(LikeFunFic likeFunFic);

    List<LikeFunFic> findAll();

    LikeFunFic saveAndFlush(LikeFunFic likeFunFic);

    LikeFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic);

}
