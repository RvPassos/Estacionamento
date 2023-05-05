package br.com.uniamerica.Estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "modelos", schema = "public")
@Audited
@AuditTable(value = "modelos_audit", schema = "audit")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "marca", nullable = false)
    private Marca marcaId;
}
