package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.util.RepositoryImplementationException;

import java.util.List;

public interface UserManagementRepository<T, V> {
    T save(V v) throws RepositoryImplementationException;

    T update(V v) throws RepositoryImplementationException;

    T delete(T id) throws RepositoryImplementationException;

    List<V> findAll() throws RepositoryImplementationException;

    List<V> findAfterId(T id, int size) throws RepositoryImplementationException;
}
