package com.ugurhalil.web.flux.security.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@SuperBuilder
@Table("roles")
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity {
    private String name;
}
