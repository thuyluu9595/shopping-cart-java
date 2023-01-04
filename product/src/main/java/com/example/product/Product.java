package com.example.product;

import jakarta.persistence.*;

@Entity
@Table(name= "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, unique = true)
//    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private int qty;

    public Product() {
    }

    public Product(String name, Long price, String description, int qty) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.qty = qty;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                '}';
    }
}
