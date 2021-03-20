package com.usermodule.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usermodule.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "country")
public class Country extends Auditable<String> implements Serializable {

    @Column(name = "sortname")
    private String sortName;

    @Column(name = "name")
    private String name;

    @Column(name = "phonecode")
    private String phoneCode;

    @JsonIgnore
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<State> states = new HashSet<>();
}
