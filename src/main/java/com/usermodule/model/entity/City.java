package com.usermodule.model.entity;

import com.usermodule.model.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city")
public class City extends Auditable<String> implements Serializable {

    private String name;

    @ManyToOne
    @JoinColumn(name = RelationshipConstants.STATE_ID)
    private State state;

  //  private Double latitude;

  //  private Double longitude;
}


