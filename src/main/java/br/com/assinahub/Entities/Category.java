package br.com.assinahub.Entities;

import br.com.assinahub.Entities.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_categories")
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
