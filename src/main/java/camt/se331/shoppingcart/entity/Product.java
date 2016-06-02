package camt.se331.shoppingcart.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dto on 2/7/2015.
 */
@Entity
public class Product implements Comparable{
    @Id
    @GeneratedValue
     Long id;
     String name;
     String description;
     Double totalPrice;
     Double wholesalePrice;

    public Product(Long id,String name, String description, Double totalPrice, Double wholesalePrice,Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.wholesalePrice = wholesalePrice;
        this.images.add(image) ;
    }
    public Product(Long id,String name, String description, Double totalPrice, Double wholesalePrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.wholesalePrice = wholesalePrice;

    }
    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!id.equals(product.id)) return false;
        if (!name.equals(product.name)) return false;
        if (!description.equals(product.description)) return false;
        if (!totalPrice.equals(product.totalPrice)) return false;
        return wholesalePrice.equals(product.wholesalePrice);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + wholesalePrice.hashCode();
        return result;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Set<Image> images = new HashSet<>();

    public Product(Long id,String name, String description, Double totalPrice, Image image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalPrice = totalPrice;
        this.images.add(image) ;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product(){

    };

    public Double getNetPrice(){
        return getTotalPrice()*(1-VatEntity.getInstance().getVat());
    }

    public Double getTax(){
        return 0.0;
    }

    public Product(Long id,String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.totalPrice = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int compareTo(Object o) {

        return (int) (this.getId() - ((Product)o).getId());
    }

    public Product(Product product){
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.totalPrice = product.totalPrice;
        this.wholesalePrice = product.wholesalePrice;
    }
}