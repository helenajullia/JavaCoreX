import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        //having the following lists:
        List<Customer> customerList=new ArrayList<Customer>();
        List<Order> orderList=new ArrayList<Order>();
        List<Product> productList=new ArrayList<Product>();


        //1.Obtain a list of products belongs to category “Books” with price > 100
        Product product1 = new Product(1L, "first Book", "Books", 120.0); //creating objects of type Product
        Product product2 = new Product(2L, "second Book", "Books", 90.0);
        Product product3 = new Product(3L, "third Book", "Books", 109.0);
        Product product4 = new Product(4L, "diapers", "Baby", 200.0);
        Product product5 = new Product(5L, "baby oil", "Baby", 20.0);
        Product product6 = new Product(6L, "barbie doll", "Toys", 100.0);
        Product product7 = new Product(7L, "car", "Toys", 200.0);

        productList.add(product1); // adding the objects to productList
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);


        List<String> books = productList.stream() //if i would have wanted to recieve the product's(books) adress, i would have changed the returned type into 'Product' instead of String
                .filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100)
                .map(Product::getName) //the returned type is String because i wanted to obtain the book's name instead of it's address, using this map function
                .collect(Collectors.toList());
        System.out.println("Books with the price > 100: "+books);






        //2.Obtain a list of orders with at least one product belonging to category “Baby”

        //in order to be able to create an object of type Order, i have to create a customer first
        Customer customer1= new Customer(1L, "Andrew", 1);
        customerList.add(customer1);
        Customer customer2= new Customer(2L, "Andy", 2);
        customerList.add(customer2);
        Customer customer3= new Customer(3L, "Alex", 2);
        customerList.add(customer3);
        List<String> customers = customerList.stream()
                .map(Customer::getName)
                .collect(Collectors.toList());
        System.out.println("Customers: "+ customers);

        //
        Set<Product> productsOrderList1=new HashSet<>();
        productsOrderList1.add(product4);
        productsOrderList1.add(product2);
        Set<Product> productsOrderList2=new HashSet<>();
        //productsOrderList2.add(product5);
        productsOrderList2.add(product2);


        //going back:
        LocalDate orderDate1=LocalDate.of(2021,3,3);
        LocalDate deliveryDate1=LocalDate.of(2021,3,9);
        Order order1 = new Order(1234L,orderDate1, deliveryDate1, "delivered", customer1,  productsOrderList1);
        orderList.add(order1);
        LocalDate orderDate2=LocalDate.of(2021,3,14);
        LocalDate deliveryDate2=LocalDate.of(2021,3,18);
        Order order2 = new Order(12345L,orderDate2, deliveryDate2, "delivered", customer2,  productsOrderList2);
        orderList.add(order2);

        LocalDate orderDate3=LocalDate.of(2022,3,3);
        LocalDate deliveryDate3=LocalDate.of(2023,3,9);
        Order order3 = new Order(001L,orderDate3, deliveryDate3, "delivered", customer2,  productsOrderList2);
        orderList.add(order3);

        LocalDate orderDate4=LocalDate.of(2023,3,3);
        LocalDate deliveryDate4=LocalDate.of(2023,4,20);
        Order order4 = new Order(002L,orderDate4, deliveryDate4, "in progress", customer1,  productsOrderList1);
        orderList.add(order4);


        List<Long> babyOrders = orderList.stream()
                .filter(order -> order.getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory().equals("Baby")))
                        .map(Order::getID)
                        .collect(Collectors.toList());
        System.out.println("Order with at least one product for babies: "+babyOrders);






        //3.Obtain a list of products with category = “Toys” and then apply 10% discount
        List<String> discountedToys = productList.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .map(p -> {
                    p.setPrice(p.getPrice() * 0.9);
                    return p.getName();
                })
                .collect(Collectors.toList());
        System.out.println("Toys 10% off:  "+discountedToys);



        //4.	Obtain a list of products ordered by customers of tier 2 between 01-Feb-2021 and 01-Apr-2021
        List<String> tier2Products = orderList.stream()
                .filter(o -> o.getCustomer().getTier() == 2)
                .filter(o -> o.getOrderDate().isAfter(LocalDate.parse("2021-02-01")) && o.getOrderDate().isBefore(LocalDate.parse("2021-04-01")))
                .flatMap(o -> o.getProducts().stream())
                .map(p -> p.getName())
                .collect(Collectors.toList());
        System.out.println("products ordered by customers of tier 2 between 01-Feb-2021 and 01-Apr-2021: "+tier2Products);



        //5.Get the cheapest product of “Books” category
        Optional<String> cheapestBook = productList.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .min(Comparator.comparingDouble(Product::getPrice))
                .map(Product::getName);
        System.out.println("Cheapest book: "+cheapestBook);


        //6.Get the 3 most recent placed orders
        List<Long> mostRecentOrders = orderList.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .sorted(Comparator.comparing(Order::getID))
                .map(Order::getID)
                .collect(Collectors.toList());
        System.out.println("most recent placed orders "+mostRecentOrders);



        //7.Get the order with the highest total price
        Order mostExpensiveOrder = null;
        Double max = 0.0;

        for (Order order : orderList) {
            Double totalPrice = 0.0;
            for (Product product : order.getProducts()) {
                totalPrice += product.getPrice();
            }
            if (totalPrice > max) {
                max = totalPrice;
                mostExpensiveOrder = order;
            }
        }
        System.out.println("most expensive order is: "+ mostExpensiveOrder.getID());




        //8.Calculate order average payment placed on 14-Mar-2021
        LocalDate targetDate = LocalDate.of(2021, 3, 14);
        List<Order> filteredOrders = orderList.stream()
                .filter(order -> order.getOrderDate().equals(targetDate))
                .collect(Collectors.toList());

        double totalPayment = 0;
        for (Order order : filteredOrders) {
            for (Product product : order.getProducts()) {
                totalPayment += product.getPrice();
            }
        }
        double averagePayment = totalPayment / filteredOrders.size();
        System.out.println("Average payment placed on " + targetDate + ": " + averagePayment);



        //9.Get the most expensive product by category
        Map<String, List<Product>> productsByCategory = productList.stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        Map<String, Product> mostExpensiveProducts = new HashMap<>();
        for (String category : productsByCategory.keySet()) {
            List<Product> products = productsByCategory.get(category);
            products.sort(Comparator.comparingDouble(Product::getPrice).reversed());
            if (!products.isEmpty()) {
                mostExpensiveProducts.put(category, products.get(0));
            }
        }

        for (String category : mostExpensiveProducts.keySet()) {
            Product product = mostExpensiveProducts.get(category);
            System.out.println("Most expensive product by category " + category + " is " + product.getName() + ": price " + product.getPrice());
        }




        //10.	Get the the product that was ordered the highest number of times
        Map<Long, Integer> productCountMap = new HashMap<>(); // map to store the count of each product


        for (Order order : orderList) {
            for (Product product : order.getProducts()) {
                if (productCountMap.containsKey(product.getID())) {//// check if the product is already in the map
                    productCountMap.put(product.getID(), productCountMap.get(product.getID()) + 1);
                } else {
                    productCountMap.put(product.getID(), 1);
                }
            }
        }

        Long productId = null;
        int maxCount = 0;
        for (Map.Entry<Long, Integer> entry : productCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                productId = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        Product mostOrderedProduct = null;
        for (Product product : productList) {
            if (product.getID().equals(productId)) {
                mostOrderedProduct = product;
                break;
            }
        }

        System.out.println("The product that was ordered the highest number of times is: " + mostOrderedProduct.getName());

    }
}