package mundo.hola.jose.miprimerholamundo.modelo;

/**
 * Created by Jose on 11/10/2016.
 */

public class CarritoDetalle {
    private String id_product;
    private String id_product_attribute;
    private String id_address_delivery;
    private String quantity;

    public CarritoDetalle(String id_product, String id_product_attribute, String id_address_delivery, String quantity) {
        this.id_product = id_product;
        this.id_product_attribute = id_product_attribute;
        this.id_address_delivery = id_address_delivery;
        this.quantity = quantity;
    }

    public CarritoDetalle() {}

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

    public String getId_address_delivery() {
        return id_address_delivery;
    }

    public void setId_address_delivery(String id_address_delivery) {
        this.id_address_delivery = id_address_delivery;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
