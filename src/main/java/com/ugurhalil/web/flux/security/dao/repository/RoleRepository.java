package com.ugurhalil.web.flux.security.dao.repository;

import com.ugurhalil.web.flux.security.dao.entity.RoleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends R2dbcRepository<RoleEntity, String> {
    Mono<RoleEntity> findByName(String name);
}
