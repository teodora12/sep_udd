package com.ftn.sep_udd.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Table
@Entity(name = "WORK")
public class Work implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String abstr;

    @ManyToOne
    private ScientificField scientificField;

    @Column
    private String pdf;

    @ManyToMany
    private Set<KeyTerm> keyTerms;

    @ManyToMany
    private Set<User> users;

    public Work() {
        this.keyTerms = new HashSet<>();
        this.users = new HashSet<>();
    }
}
