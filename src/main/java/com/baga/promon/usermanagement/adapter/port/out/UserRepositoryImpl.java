package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.generated.tables.pojos.UserEntity;
import com.baga.promon.usermanagement.util.RepositoryImplementationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Long save(UserEntity userEntity) throws RepositoryImplementationException {
        return null;
    }

    @Override
    public Long update(UserEntity userEntity) throws RepositoryImplementationException {
        return null;
    }

    @Override
    public Long delete(Long id) throws RepositoryImplementationException {
        return null;
    }

    @Override
    public List<UserEntity> findAll() throws RepositoryImplementationException {
        return null;
    }

    @Override
    public List<UserEntity> findAfterId(Long id, int size) throws RepositoryImplementationException {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws RepositoryImplementationException {
        return Optional.empty();
    }
}
