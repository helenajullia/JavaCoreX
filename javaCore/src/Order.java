import java.time.LocalDate;
import java.util.Set;

public class Order {
    private Long id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String status;
    private Customer customer;
    private Set<Product> products;

    public Order(Long id, LocalDate orderDate, LocalDate deliveryDate,
                 String status, Customer customer, Set<Product> products ){
        this.id=id;
        this.orderDate=orderDate;
        this.deliveryDate=deliveryDate;
        this.status=status;
        this.customer=customer;
        this.products=products;
    }
    public Set<Product> getProducts() {
        return this.products;
    }

    public Long getID() {
        return this.id;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }



}
