package az.trendyolaz.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table (name = " products ")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false )
    private String name;

    @Column(nullable = false)
    private BigDecimal originalPrice;

    @Column (nullable = false)
    private BigDecimal discountedPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = " category_id")
    private Category category;
}
