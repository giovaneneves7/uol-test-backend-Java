package com.github.giovaneneves7.api.infrastructure.model;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class PersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
}
