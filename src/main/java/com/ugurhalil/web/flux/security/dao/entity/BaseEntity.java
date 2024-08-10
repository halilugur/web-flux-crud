package com.ugurhalil.web.flux.security.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable, Persistable<String> {
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    @Override
    public String getId() {
        return Objects.isNull(this.id) ? UUID.randomUUID().toString() + System.currentTimeMillis() : this.id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.id == null;
    }
}
