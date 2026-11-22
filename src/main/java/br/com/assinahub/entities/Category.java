package br.com.assinahub.entities;

import br.com.assinahub.entities.base.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_categories")
public class Category extends AuditableEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
