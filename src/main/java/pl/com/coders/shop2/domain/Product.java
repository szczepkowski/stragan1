package pl.com.coders.shop2.domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
@ToString

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    @CreationTimestamp
    private LocalDateTime created;
    @CreationTimestamp
    private LocalDateTime updated;
}
