import entities.Product;
import entities.Sale;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManager sales = Persistence
                .createEntityManagerFactory("Sales")
                .createEntityManager();

        sales.getTransaction().begin();

//        new Sale and Product

//        Sale sale = new Sale();
//        sale.setDate(LocalDateTime.now());
//
//        Product product = new Product();
//        product.setName("TestProduct");
//        product.setPrice(BigDecimal.TEN);
//        product.setQuantity(5.0);
//        product.getSales().add(sale);
//        sale.setProduct(product);
//
//        sales.persist(product);


//      Delete product

//        Product product = sales.find(Product.class, 1L);
//        sales.remove(product);

        sales.getTransaction().commit();
    }
}
