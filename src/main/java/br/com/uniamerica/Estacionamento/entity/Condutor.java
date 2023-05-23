package br.com.uniamerica.Estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "condutores", schema = "public")
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", length = 20, nullable = false, unique = true)
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_pago")
    private BigDecimal tempoPago;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

}
