package FunFictionUserProject.funFictionUser.service;

import FunFictionUserProject.funFictionUser.view.RatingFunFic;

import java.util.List;

public interface RatingService {
    RatingFunFic save(RatingFunFic ratingFunFic);

    RatingFunFic findById(Long id);

    void delete(RatingFunFic ratingFunFic);

    List<RatingFunFic> findAll();

    RatingFunFic saveAndFlush(RatingFunFic ratingFunFic);

    RatingFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic);

}
