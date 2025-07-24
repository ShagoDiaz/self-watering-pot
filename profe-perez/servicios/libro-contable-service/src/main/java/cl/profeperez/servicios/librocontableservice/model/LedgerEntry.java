package cl.profeperez.servicios.librocontableservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * LedgerEntry entity representing a financial accounting entry.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String account;

    @NotNull
    private Double amount;

    private String description;

    private LocalDateTime entryDate;

    private String type; // e.g. DEBIT, CREDIT
}
