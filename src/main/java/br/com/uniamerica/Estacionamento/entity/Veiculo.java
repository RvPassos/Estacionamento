package br.com.uniamerica.Estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "veiculos", schema = "public")
@Audited
@AuditTable(value = "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "placa", length = 10, nullable = false, unique = true)
    private String placa;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelos", nullable = false)
    private Modelo modeloId;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "cor", nullable = false)
    private Cor cor;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private int ano;
}
