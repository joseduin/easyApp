package mundo.hola.jose.miprimerholamundo.modelo;

import java.util.ArrayList;

/**
 * Created by Jose on 11/10/2016.
 */

public class Carrito {
    private String id;
    private String id_address_delivery;
    private String id_address_invoice;
    private String id_currency;
    private String id_customer;
    private String id_guest;
    private String id_lang;
    private String id_shop_group;
    private String id_shop;
    private String id_carrier;
    private String recyclable;
    private String gift;
    private String gift_message;
    private String mobile_theme;
    private String delivery_option;             // Pedido
    private String secure_key;
    private String allow_seperated_package;
    private String date_add;
    private String date_upd;
    private ArrayList<CarritoDetalle> carritoDetalles;

    public Carrito(String id, String id_address_delivery, String id_address_invoice, String id_currency, String id_customer, String id_guest, String id_lang, String id_shop_group, String id_shop, String id_carrier, String recyclable, String gift, String gift_message, String mobile_theme, String delivery_option, String secure_key, String allow_seperated_package, String date_add, String date_upd, ArrayList<CarritoDetalle> carritoDetalles) {
        this.id = id;
        this.id_address_delivery = id_address_delivery;
        this.id_address_invoice = id_address_invoice;
        this.id_currency = id_currency;
        this.id_customer = id_customer;
        this.id_guest = id_guest;
        this.id_lang = id_lang;
        this.id_shop_group = id_shop_group;
        this.id_shop = id_shop;
        this.id_carrier = id_carrier;
        this.recyclable = recyclable;
        this.gift = gift;
        this.gift_message = gift_message;
        this.mobile_theme = mobile_theme;
        this.delivery_option = delivery_option;
        this.secure_key = secure_key;
        this.allow_seperated_package = allow_seperated_package;
        this.date_add = date_add;
        this.date_upd = date_upd;
        this.carritoDetalles = carritoDetalles;
    }

    public Carrito() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_address_delivery() {
        return id_address_delivery;
    }

    public void setId_address_delivery(String id_address_delivery) {
        this.id_address_delivery = id_address_delivery;
    }

    public String getId_address_invoice() {
        return id_address_invoice;
    }

    public void setId_address_invoice(String id_address_invoice) {
        this.id_address_invoice = id_address_invoice;
    }

    public String getId_currency() {
        return id_currency;
    }

    public void setId_currency(String id_currency) {
        this.id_currency = id_currency;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_guest() {
        return id_guest;
    }

    public void setId_guest(String id_guest) {
        this.id_guest = id_guest;
    }

    public String getId_lang() {
        return id_lang;
    }

    public void setId_lang(String id_lang) {
        this.id_lang = id_lang;
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

    public String getId_carrier() {
        return id_carrier;
    }

    public void setId_carrier(String id_carrier) {
        this.id_carrier = id_carrier;
    }

    public String getRecyclable() {
        return recyclable;
    }

    public void setRecyclable(String recyclable) {
        this.recyclable = recyclable;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public String getGift_message() {
        return gift_message;
    }

    public void setGift_message(String gift_message) {
        this.gift_message = gift_message;
    }

    public String getMobile_theme() {
        return mobile_theme;
    }

    public void setMobile_theme(String mobile_theme) {
        this.mobile_theme = mobile_theme;
    }

    public String getDelivery_option() {
        return delivery_option;
    }

    public void setDelivery_option(String delivery_option) {
        this.delivery_option = delivery_option;
    }

    public String getSecure_key() {
        return secure_key;
    }

    public void setSecure_key(String secure_key) {
        this.secure_key = secure_key;
    }

    public String getAllow_seperated_package() {
        return allow_seperated_package;
    }

    public void setAllow_seperated_package(String allow_seperated_package) {
        this.allow_seperated_package = allow_seperated_package;
    }

    public String getDate_add() {
        return date_add;
    }

    public void setDate_add(String date_add) {
        this.date_add = date_add;
    }

    public String getDate_upd() {
        return date_upd;
    }

    public void setDate_upd(String date_upd) {
        this.date_upd = date_upd;
    }

    public ArrayList<CarritoDetalle> getCarritoDetalles() {
        return carritoDetalles;
    }

    public void setCarritoDetalles(ArrayList<CarritoDetalle> carritoDetalles) {
        this.carritoDetalles = carritoDetalles;
    }
}
