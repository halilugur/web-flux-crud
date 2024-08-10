package com.ugurhalil.web.flux.security.service;

import com.ugurhalil.web.flux.security.dao.entity.RoleEntity;
import com.ugurhalil.web.flux.security.dao.repository.RoleRepository;
import com.ugurhalil.web.flux.security.model.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Flux<RoleDto> getAll() {
        return roleRepository.findAll()
                .map(roleEntity -> RoleDto.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .created(roleEntity.getCreated())
                        .updated(roleEntity.getUpdated())
                        .build());
    }

    public Mono<RoleDto> getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .map(roleEntity -> RoleDto.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .created(roleEntity.getCreated())
                        .updated(roleEntity.getUpdated())
                        .build());
    }

    public Mono<RoleDto> getById(String id) {
        return roleRepository.findById(id)
                .map(roleEntity -> RoleDto.builder()
                        .id(roleEntity.getId())
                        .name(roleEntity.getName())
                        .created(roleEntity.getCreated())
                        .updated(roleEntity.getUpdated())
                        .build());
    }

    public Mono<RoleDto> save(RoleDto roleDto) {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(roleDto.getName())
                .build();

        return roleRepository.save(roleEntity)
                .map(entity -> RoleDto.builder().name(entity.getName()).build());
    }

    public Mono<RoleDto> update(String id, RoleDto roleDto) {
        return roleRepository.findById(id)
                .map(roleEntity -> {
                    roleEntity.setName(roleDto.getName());
                    return roleEntity;
                })
                .flatMap(roleRepository::save)
                .map(entity -> RoleDto.builder().name(entity.getName()).build());
    }
}
