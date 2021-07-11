package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.RatingRepository;
import FunFictionUserProject.funFictionUser.service.RatingService;
import FunFictionUserProject.funFictionUser.view.RatingFunFic;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository repository) {
        this.ratingRepository = repository;
    }
    @Override
    public RatingFunFic save(RatingFunFic ratingFunFic) {
        return ratingRepository.save(ratingFunFic);
    }

    @Override
    public RatingFunFic findById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(RatingFunFic ratingFunFic) {
        ratingRepository.delete(ratingFunFic);
    }

    @Override
    public List<RatingFunFic> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public RatingFunFic saveAndFlush(RatingFunFic ratingFunFic) {
        return ratingRepository.saveAndFlush(ratingFunFic);
    }

    @Override
    public RatingFunFic findByUserIdAndAndFunFictionId(Long idUser, Long idFunFic) {
        return ratingRepository.findByUserIdAndAndFunFictionId(idUser, idFunFic);
    }
}
