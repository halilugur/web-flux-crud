package com.ugurhalil.web.flux.security.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String email;
    @Builder.Default
    private Boolean enabled = true;
}


