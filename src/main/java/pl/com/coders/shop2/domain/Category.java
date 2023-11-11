package pl.com.coders.shop2.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @EqualsAndHashCode
    @ToString
    public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
        private Set<Product> products;
        private String name;
        @CreationTimestamp
        private LocalDateTime created;
        @UpdateTimestamp
        private LocalDateTime updated;
    }
