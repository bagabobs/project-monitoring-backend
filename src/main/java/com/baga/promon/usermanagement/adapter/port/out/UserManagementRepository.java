package com.baga.promon.usermanagement.adapter.port.out;

import com.baga.promon.usermanagement.util.PersistenceRepositoryException;

public interface UserManagementRepository<T, V> {
    T save(V v) throws PersistenceRepositoryException;
}
