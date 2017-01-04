package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;

/**
 * Created by Jose on 3/12/2016.
 */

@SuppressWarnings("serial")
public class OrderItem implements Serializable {

    private String id;
    private String product_id;
    private String product_attribute_id;
    private String product_quantity;
    private String product_name;
    private String product_reference;
    private String product_ean13;
    private String product_upc;
    private String product_price;
    private String unit_price_tax_incl;
    private String unit_price_tax_excl;

    public OrderItem(String id, String product_id, String product_attribute_id, String product_quantity, String product_name, String product_reference, String product_ean13, String product_upc, String product_price, String unit_price_tax_incl, String unit_price_tax_excl) {
        this.id = id;
        this.product_id = product_id;
        this.product_attribute_id = product_attribute_id;
        this.product_quantity = product_quantity;
        this.product_name = product_name;
        this.product_reference = product_reference;
        this.product_ean13 = product_ean13;
        this.product_upc = product_upc;
        this.product_price = product_price;
        this.unit_price_tax_incl = unit_price_tax_incl;
        this.unit_price_tax_excl = unit_price_tax_excl;
    }

    public OrderItem() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_attribute_id() {
        return product_attribute_id;
    }

    public void setProduct_attribute_id(String product_attribute_id) {
        this.product_attribute_id = product_attribute_id;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_reference() {
        return product_reference;
    }

    public void setProduct_reference(String product_reference) {
        this.product_reference = product_reference;
    }

    public String getProduct_ean13() {
        return product_ean13;
    }

    public void setProduct_ean13(String product_ean13) {
        this.product_ean13 = product_ean13;
    }

    public String getProduct_upc() {
        return product_upc;
    }

    public void setProduct_upc(String product_upc) {
        this.product_upc = product_upc;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getUnit_price_tax_incl() {
        return unit_price_tax_incl;
    }

    public void setUnit_price_tax_incl(String unit_price_tax_incl) {
        this.unit_price_tax_incl = unit_price_tax_incl;
    }

    public String getUnit_price_tax_excl() {
        return unit_price_tax_excl;
    }

    public void setUnit_price_tax_excl(String unit_price_tax_excl) {
        this.unit_price_tax_excl = unit_price_tax_excl;
    }
}
