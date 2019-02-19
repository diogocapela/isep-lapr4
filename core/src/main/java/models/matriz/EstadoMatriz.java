/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.matriz;

import settings.Settings;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EstadoMatriz implements Serializable {

    private String estado;

    protected EstadoMatriz() {
    }

    private EstadoMatriz(String estado) {
        this.estado = estado;
    }

    public static EstadoMatriz estadoBase() {
        return new EstadoMatriz(Settings.base);
    }

    public static EstadoMatriz estadoCaracterizada() {
        return new EstadoMatriz(Settings.caracterizada);
    }

    public static EstadoMatriz estadoDetalhada() {
        return new EstadoMatriz(Settings.detalhada);
    }

    public static EstadoMatriz estadoPublicada() {
        return new EstadoMatriz(Settings.publicada);
    }

    public static EstadoMatriz estadoCancelada() {
        return new EstadoMatriz(Settings.cancelada);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof EstadoMatriz))
            return false;

        EstadoMatriz other = (EstadoMatriz) obj;
        return other.estado.equals(this.estado);
    }

    @Override
    public String toString() {
        if (this.equals(EstadoMatriz.estadoBase())) {
            return Settings.base;
        } else if (this.equals(EstadoMatriz.estadoCaracterizada())) {
            return Settings.caracterizada;
        } else if (this.equals(EstadoMatriz.estadoDetalhada())) {
            return Settings.detalhada;
        } else if (this.equals(EstadoMatriz.estadoPublicada())) {
            return Settings.publicada;
        } else {
            return Settings.cancelada;
        }
    }
}
