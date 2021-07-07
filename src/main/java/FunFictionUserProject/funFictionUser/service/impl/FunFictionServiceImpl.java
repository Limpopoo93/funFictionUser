package FunFictionUserProject.funFictionUser.service.impl;

import FunFictionUserProject.funFictionUser.repository.FunFictionRepository;
import FunFictionUserProject.funFictionUser.service.FunFictionService;
import FunFictionUserProject.funFictionUser.view.FunFiction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class FunFictionServiceImpl implements FunFictionService {
    private final FunFictionRepository funFictionRepository;

    public FunFictionServiceImpl(FunFictionRepository funFictionRepository) {
        this.funFictionRepository = funFictionRepository;
    }

    @Transactional
    @Override
    public FunFiction save(FunFiction funFiction) {
        log.info("funFiction by save in funFicService");
        return funFictionRepository.save(funFiction);
    }

    @Override
    public FunFiction findById(Long id) {
        log.info("id by findById in funFicService");
        return funFictionRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(FunFiction funFiction) {
        log.info("funFiction by delete in funFicService");
        funFictionRepository.delete(funFiction);
    }

    @Override
    public List<FunFiction> findAll() {
        log.info("list funFiction by findAll in funFicService");
        return funFictionRepository.findAll();
    }

    @Transactional
    @Override
    public FunFiction saveAndFlush(FunFiction funFiction) {
        log.info("funFiction by saveAndFlush in funFicService");
        return funFictionRepository.saveAndFlush(funFiction);
    }

    @Override
    public List<FunFiction> findFunFictionByUserId(Long id) {
        log.info("id by findFunFictionByUserId in funFicService");
        return funFictionRepository.findFunFictionByUserId(id);
    }

    @Override
    public List<FunFiction> findByNameFunContaining(String name) {
        log.info("name by findByNameFunContaining in funFicService");
        return funFictionRepository.findByNameFunContaining(name);
    }
}
