package com.ugurhalil.web.flux.security.service;

import com.ugurhalil.web.flux.security.dao.entity.RoleEntity;
import com.ugurhalil.web.flux.security.dao.entity.UserEntity;
import com.ugurhalil.web.flux.security.dao.repository.RoleRepository;
import com.ugurhalil.web.flux.security.dao.repository.UserRepository;
import com.ugurhalil.web.flux.security.model.RoleDto;
import com.ugurhalil.web.flux.security.model.UserCreateRequest;
import com.ugurhalil.web.flux.security.model.UserDto;
import com.ugurhalil.web.flux.security.model.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Flux<UserDto> getAll() {
        return userRepository.findAll().map(userEntity -> UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .created(userEntity.getCreated())
                .updated(userEntity.getUpdated())
                .build());
    }

    public Mono<UserDto> getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userEntity -> UserDto.builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .created(userEntity.getCreated())
                        .updated(userEntity.getUpdated())
                        .build());
    }

    public Mono<UserDto> getById(String id) {
        return userRepository.findById(id)
                .map(userEntity -> UserDto.builder()
                        .id(userEntity.getId())
                        .username(userEntity.getUsername())
                        .email(userEntity.getEmail())
                        .created(userEntity.getCreated())
                        .updated(userEntity.getUpdated())
                        .build());
    }

    public Mono<RoleDto> saveRole(RoleDto roleDto) {
        RoleEntity roleEntity = RoleEntity.builder()
                .name(roleDto.getName())
                .build();
        return roleRepository.save(roleEntity)
                .map(savedRoleEntity -> RoleDto.builder()
                        .name(roleDto.getName())
                        .build());
    }

    public Mono<UserDto> save(UserCreateRequest request) {
        UserEntity userEntity = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();

        return userRepository.save(userEntity)
                .map(savedUserEntity -> UserDto.builder()
                        .id(savedUserEntity.getId())
                        .username(savedUserEntity.getUsername())
                        .email(savedUserEntity.getEmail())
                        .created(savedUserEntity.getCreated())
                        .updated(savedUserEntity.getUpdated())
                        .build());
    }

    public Mono<UserDto> update(String id, UserUpdateRequest updateRequest) {
        return userRepository.findById(id)
                .flatMap(userEntity -> {
                    userEntity.setUsername(updateRequest.username());
                    userEntity.setEmail(updateRequest.email());
                    return userRepository.save(userEntity);
                })
                .map(savedUserEntity -> UserDto.builder()
                        .id(savedUserEntity.getId())
                        .username(savedUserEntity.getUsername())
                        .email(savedUserEntity.getEmail())
                        .created(savedUserEntity.getCreated())
                        .updated(savedUserEntity.getUpdated())
                        .build());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(userEntity -> User.builder()
                        .username(userEntity.getUsername())
                        .password(userEntity.getPassword())
                        .authorities(userEntity.getRole())
                        .build());
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return userRepository.findByUsername(user.getUsername())
                .map(userEntity -> {
                    userEntity.setPassword(passwordEncoder.encode(newPassword));
                    return userEntity;
                })
                .flatMap(userRepository::save)
                .map(userEntity -> User.builder()
                        .username(userEntity.getUsername())
                        .password(userEntity.getPassword())
                        .authorities(userEntity.getRole())
                        .build());
    }
}
