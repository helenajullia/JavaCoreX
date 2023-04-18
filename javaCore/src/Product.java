public class Product {
    private Long id;
    private String name;
    private String category;
    private Double price;

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Product(Long id, String name, String category, Double price){
        this.id=id;
        this.name=name;
        this.category=category;
        this.price=price;
    }

    public void setPrice(double newPrice) {
        this.price=newPrice;
    }

    public Long getID() {
        return this.id;
    }
}
