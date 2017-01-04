package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;

/**
 * Created by Jose on 14/11/2016.
 */

@SuppressWarnings("serial")
public class Descuento implements Serializable {

    private String id;
    private String id_shop_group;
    private String id_shop;
    private String id_cart;
    private String id_product;
    private String id_product_attribute;
    private String id_currency;
    private String id_country;
    private String id_group;
    private String id_customer;
    private String id_specific_price_rule;
    private String price;
    private String from_quantity;
    private String reduction;
    private String reduction_tax;
    private String reduction_type;
    private String from;
    private String to;

    public Descuento(String to, String id, String id_shop_group, String id_shop, String id_cart, String id_product, String id_product_attribute, String id_currency, String id_country, String id_group, String id_customer, String id_specific_price_rule, String price, String from_quantity, String reduction, String reduction_tax, String reduction_type, String from) {
        this.to = to;
        this.id = id;
        this.id_shop_group = id_shop_group;
        this.id_shop = id_shop;
        this.id_cart = id_cart;
        this.id_product = id_product;
        this.id_product_attribute = id_product_attribute;
        this.id_currency = id_currency;
        this.id_country = id_country;
        this.id_group = id_group;
        this.id_customer = id_customer;
        this.id_specific_price_rule = id_specific_price_rule;
        this.price = price;
        this.from_quantity = from_quantity;
        this.reduction = reduction;
        this.reduction_tax = reduction_tax;
        this.reduction_type = reduction_type;
        this.from = from;
    }

    public Descuento() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_shop_group() {
        return id_shop_group;
    }

    public void setId_shop_group(String id_shop_group) {
        this.id_shop_group = id_shop_group;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getId_cart() {
        return id_cart;
    }

    public void setId_cart(String id_cart) {
        this.id_cart = id_cart;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getId_product_attribute() {
        return id_product_attribute;
    }

    public void setId_product_attribute(String id_product_attribute) {
        this.id_product_attribute = id_product_attribute;
    }

    public String getId_currency() {
        return id_currency;
    }

    public void setId_currency(String id_currency) {
        this.id_currency = id_currency;
    }

    public String getId_country() {
        return id_country;
    }

    public void setId_country(String id_country) {
        this.id_country = id_country;
    }

    public String getId_group() {
        return id_group;
    }

    public void setId_group(String id_group) {
        this.id_group = id_group;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_specific_price_rule() {
        return id_specific_price_rule;
    }

    public void setId_specific_price_rule(String id_specific_price_rule) {
        this.id_specific_price_rule = id_specific_price_rule;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFrom_quantity() {
        return from_quantity;
    }

    public void setFrom_quantity(String from_quantity) {
        this.from_quantity = from_quantity;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public String getReduction_tax() {
        return reduction_tax;
    }

    public void setReduction_tax(String reduction_tax) {
        this.reduction_tax = reduction_tax;
    }

    public String getReduction_type() {
        return reduction_type;
    }

    public void setReduction_type(String reduction_type) {
        this.reduction_type = reduction_type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
