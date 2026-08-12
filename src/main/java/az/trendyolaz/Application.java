package az.trendyolaz;
import az.trendyolaz.entity.Category;
import az.trendyolaz.entity.Product;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository , ProductRepository productRepository){
        return args -> {
            if (categoryRepository.count()==0){
                Category c1 =  categoryRepository.save(Category.builder().name("Electronica").build());
                Category c2 =  categoryRepository.save(Category.builder().name("Geyim").build());
                Category c3 =  categoryRepository.save(Category.builder().name("Cosmetics").build());
                Category c4 = categoryRepository.save(Category.builder().name("Ev və yaşam").build());
                Category c5 = categoryRepository.save(Category.builder().name("İdman").build());
                Category c6 = categoryRepository.save(Category.builder().name("Kitab").build());

                List<Category> categories = List.of(c1 , c2 , c3 , c4 , c5 , c6);
                for (Category cat : categories){
                    for (int i = 1 ; i<6; i++){
                        BigDecimal orig = BigDecimal.valueOf(100L*i);
                        BigDecimal disc = BigDecimal.valueOf(80L*i);
                        productRepository.save(Product.builder()
                                .name(cat.getName()+ "Məhsul " + i )
                                .originalPrice(orig)
                                .discountedPrice(disc)
                                .category(cat)
                                .build());
                    }
                }
            }
        };
    }

}
