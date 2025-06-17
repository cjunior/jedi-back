package com.ifce.jedi.model.SecoesSite.Contents;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table("content_section")
public class Content {
    @Id
    private Long id;

}
