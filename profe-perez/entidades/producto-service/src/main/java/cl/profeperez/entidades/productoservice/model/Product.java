package cl.profeperez.entidades.productoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Product entity representing a product in the inventory and finance ERP system.
 * Includes attributes useful for inventory management and financial accounting.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String sku; // Stock Keeping Unit

    @NotBlank
    private String name;

    private String description;

    @Column(unique = true)
    private String barcode;

    private String category;

    private String supplier;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private Integer reorderLevel;

    @NotNull
    private Double costPrice;

    @NotNull
    private Double salePrice;

    private Double taxRate; // e.g. 0.19 for 19%

    private String unitOfMeasure; // e.g. pcs, kg, liters

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
