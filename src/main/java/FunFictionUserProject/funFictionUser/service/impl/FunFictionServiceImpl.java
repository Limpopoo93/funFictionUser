package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.FunFictionRepository;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.view.FunFiction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunFictionServiceImpl implements FunFictionService {
    @Autowired
    private FunFictionRepository funFictionRepository;

    @Override
    public FunFiction save(FunFiction funFiction) {
        return funFictionRepository.save(funFiction);
    }

    @Override
    public FunFiction findById(Long id) {
        return funFictionRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(FunFiction funFiction) {
        funFictionRepository.delete(funFiction);
    }

    @Override
    public List<FunFiction> findAll() {
        return funFictionRepository.findAll();
    }

    @Override
    public FunFiction saveAndFlush(FunFiction funFiction) {
        return funFictionRepository.saveAndFlush(funFiction);
    }

    @Override
    public List<FunFiction> findFunFictionByUserId(Long id) {
        return funFictionRepository.findFunFictionByUserId(id);
    }

    @Override
    public List<FunFiction> findByNameFunContaining(String name) {
        return funFictionRepository.findByNameFunContaining(name);
    }
}
