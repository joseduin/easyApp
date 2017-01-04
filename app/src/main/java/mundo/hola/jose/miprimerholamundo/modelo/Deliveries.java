package mundo.hola.jose.miprimerholamundo.modelo;

/**
 * Created by Jose on 1/12/2016.
 */

public class Deliveries {

    private String id;
    private String id_range_price;
    private String id_range_weight;
    private String id_zone;
    private String id_shop_group;
    private String price;

    public Deliveries(String id, String id_range_price, String id_range_weight, String id_zone, String id_shop_group, String price) {
        this.id = id;
        this.id_range_price = id_range_price;
        this.id_range_weight = id_range_weight;
        this.id_zone = id_zone;
        this.id_shop_group = id_shop_group;
        this.price = price;
    }

    public Deliveries() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_range_price() {
        return id_range_price;
    }

    public void setId_range_price(String id_range_price) {
        this.id_range_price = id_range_price;
    }

    public String getId_range_weight() {
        return id_range_weight;
    }

    public void setId_range_weight(String id_range_weight) {
        this.id_range_weight = id_range_weight;
    }

    public String getId_zone() {
        return id_zone;
    }

    public void setId_zone(String id_zone) {
        this.id_zone = id_zone;
    }

    public String getId_shop_group() {
        return id_shop_group;
    }

    public void setId_shop_group(String id_shop_group) {
        this.id_shop_group = id_shop_group;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
