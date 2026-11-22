package br.com.assinahub.entities;

import br.com.assinahub.entities.base.BaseEntity;
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
public class Category extends BaseEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;
}
