/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.celula;

import adapters.Exporter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author pushdword
 */
@Entity
@Table(name = "Celula")
@Inheritance(
    strategy = InheritanceType.JOINED
)
public abstract class Celula implements Serializable, Exporter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;

    public Celula() {
        //for JPA
    }

    public abstract boolean match(Celula c);
}
