package com.iotproject.iotproject.Service;

import com.iotproject.iotproject.Dto.BaseSensorFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class BaseSensorService<T , F, R extends CrudRepository<T, UUID> & JpaSpecificationExecutor<T>> {

    protected final R repository;
    private final String[] locations = {"Alexandria", "Smart Village", "Nasr City", "5th Settlement"};
    private final Random random = new Random();

    protected BaseSensorService(R repository) {
        this.repository = repository;
    }

    protected String getRandomLocation() {
        return locations[random.nextInt(locations.length)];
    }

    public List<T> getAll() {
        return (List<T>) repository.findAll();
    }



    public Optional<T> getLatest() {
        try {
            return (Optional<T>) repository.getClass()
                    .getMethod("findTopByOrderByTimestampDesc")
                    .invoke(repository);
        } catch (Exception e) {
            throw new RuntimeException("Method findTopByOrderByTimestampDesc not found in repository.", e);
        }
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public Optional<T> getById(UUID id) {
        return repository.findById(id);
    }

    public Page<T> filter(F filter, Pageable pageable, Specification<T> spec) {
        return repository.findAll(spec, pageable);
    }
    public Specification<T> buildBaseSpecification(BaseSensorFilter filter) {
        Specification<T> spec = Specification.where(null);

        if (filter.getLocation() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("location"), filter.getLocation()));
        }

        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get("timestamp"), filter.getStartDate(), filter.getEndDate()));
        }

        return spec;
    }

}
