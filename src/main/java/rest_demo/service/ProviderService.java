package rest_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest_demo.dao.ProviderRepository;
import rest_demo.entity.Provider;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    ProviderRepository providerRepo;

    @Autowired
    public ProviderService(ProviderRepository providerRepo) {
        this.providerRepo = providerRepo;
    }

    public List<Provider> findAll() {
        return providerRepo.findByOrderByContractYearDesc();
    }

    public Optional<Provider> findById(long id) {
        return providerRepo.findById(id);
    }

    public List<Provider> findBy(String orderBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.ASC, orderBy);
        if (direction.equals("DESC")) sort = Sort.by(Sort.Direction.DESC, orderBy);
        return providerRepo.findAll(sort);
    }

    public List<Provider> findByNamePattern(String namePattern, String direction) {
        Sort sort = Sort.by(Sort.Direction.ASC);
        if (direction.equals("DESC")) sort = Sort.by(Sort.Direction.DESC);
        return providerRepo.findByNameLike(namePattern, Sort.by(Sort.Direction.ASC));
    }

    public List<Provider> findByNameStartingWith(String name) {
        return providerRepo.findByNameStartingWith(name);
    }

    public List<Provider> findByClinicalFocus(String clinicalFocus) {
        Sort sort = Sort.by(Sort.Direction.DESC, "contractYear");
        return providerRepo.findByClinicalFocus(clinicalFocus, sort);
    }

    public List<Provider> findByContractYearGreaterThanEqual(int year) {
        Sort sort = Sort.by(Sort.Direction.DESC, "contractYear");
        return providerRepo.findByContractYearGreaterThanEqual(year, sort);
    }

    public List<Provider> findPaginated(int page, int size, String orderBy) {
        Pageable pageableRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, orderBy));
        return providerRepo.findAll(pageableRequest).getContent();
    }

    public void deleteById(long id) {
        providerRepo.deleteById(id);
    }

    @Transactional
    public Provider updateProvider(Provider p) {
        return providerRepo.saveAndFlush(p);
    }

    @Transactional
    public Provider saveProvider(Provider p) {
        return providerRepo.save(p);
    }
}
