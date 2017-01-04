package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jose on 3/12/2016.
 */

@SuppressWarnings("serial")
public class Order implements Serializable {

    private String id;
    private String id_address_delivery;
    private String id_address_invoice;
    private String id_cart;
    private String id_currency;
    private String id_lang;
    private String id_customer;
    private String id_carrier;
    private String current_state;
    private String module;
    private String invoice_number;
    private String invoice_date;
    private String delivery_number;
    private String delivery_date;
    private String valid;
    private String date_add;
    private String date_upd;
    private String shipping_number;
    private String id_shop_group;
    private String id_shop;
    private String secure_key;
    private String payment;
    private String recyclable;
    private String gift;
    private String gift_message;
    private String mobile_theme;
    private String total_discounts;
    private String total_discounts_tax_incl;
    private String total_discounts_tax_excl;
    private String total_paid;
    private String total_paid_tax_incl;
    private String total_paid_tax_excl;
    private String total_paid_real;
    private String total_products;
    private String total_products_wt;
    private String total_shipping;
    private String total_shipping_tax_incl;
    private String total_shipping_tax_excl;
    private String carrier_tax_rate;
    private String total_wrapping;
    private String total_wrapping_tax_incl;
    private String total_wrapping_tax_excl;
    private String round_mode;
    private String round_type;
    private String conversion_rate;
    private String reference;
    private ArrayList<OrderItem> productos;

    public Order(String id, String id_address_delivery, String id_address_invoice, String id_cart, String id_currency, String id_lang, String id_customer, String id_carrier, String current_state, String module, String invoice_number, String invoice_date, String delivery_number, String delivery_date, String valid, String date_add, String date_upd, String shipping_number, String id_shop_group, String id_shop, String secure_key, String payment, String recyclable, String gift, String gift_message, String mobile_theme, String total_discounts, String total_discounts_tax_incl, String total_discounts_tax_excl, String total_paid, String total_paid_tax_incl, String total_paid_tax_excl, String total_paid_real, String total_products, String total_products_wt, String total_shipping, String total_shipping_tax_incl, String total_shipping_tax_excl, String carrier_tax_rate, String total_wrapping, String total_wrapping_tax_incl, String total_wrapping_tax_excl, String round_mode, String round_type, String conversion_rate, String reference, ArrayList<OrderItem> productos) {
        this.id = id;
        this.id_address_delivery = id_address_delivery;
        this.id_address_invoice = id_address_invoice;
        this.id_cart = id_cart;
        this.id_currency = id_currency;
        this.id_lang = id_lang;
        this.id_customer = id_customer;
        this.id_carrier = id_carrier;
        this.current_state = current_state;
        this.module = module;
        this.invoice_number = invoice_number;
        this.invoice_date = invoice_date;
        this.delivery_number = delivery_number;
        this.delivery_date = delivery_date;
        this.valid = valid;
        this.date_add = date_add;
        this.date_upd = date_upd;
        this.shipping_number = shipping_number;
        this.id_shop_group = id_shop_group;
        this.id_shop = id_shop;
        this.secure_key = secure_key;
        this.payment = payment;
        this.recyclable = recyclable;
        this.gift = gift;
        this.gift_message = gift_message;
        this.mobile_theme = mobile_theme;
        this.total_discounts = total_discounts;
        this.total_discounts_tax_incl = total_discounts_tax_incl;
        this.total_discounts_tax_excl = total_discounts_tax_excl;
        this.total_paid = total_paid;
        this.total_paid_tax_incl = total_paid_tax_incl;
        this.total_paid_tax_excl = total_paid_tax_excl;
        this.total_paid_real = total_paid_real;
        this.total_products = total_products;
        this.total_products_wt = total_products_wt;
        this.total_shipping = total_shipping;
        this.total_shipping_tax_incl = total_shipping_tax_incl;
        this.total_shipping_tax_excl = total_shipping_tax_excl;
        this.carrier_tax_rate = carrier_tax_rate;
        this.total_wrapping = total_wrapping;
        this.total_wrapping_tax_incl = total_wrapping_tax_incl;
        this.total_wrapping_tax_excl = total_wrapping_tax_excl;
        this.round_mode = round_mode;
        this.round_type = round_type;
        this.conversion_rate = conversion_rate;
        this.reference = reference;
        this.productos = productos;
    }

    public Order() {

    }

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

    public String getId_cart() {
        return id_cart;
    }

    public void setId_cart(String id_cart) {
        this.id_cart = id_cart;
    }

    public String getId_currency() {
        return id_currency;
    }

    public void setId_currency(String id_currency) {
        this.id_currency = id_currency;
    }

    public String getId_lang() {
        return id_lang;
    }

    public void setId_lang(String id_lang) {
        this.id_lang = id_lang;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_carrier() {
        return id_carrier;
    }

    public void setId_carrier(String id_carrier) {
        this.id_carrier = id_carrier;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getDelivery_number() {
        return delivery_number;
    }

    public void setDelivery_number(String delivery_number) {
        this.delivery_number = delivery_number;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
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

    public String getShipping_number() {
        return shipping_number;
    }

    public void setShipping_number(String shipping_number) {
        this.shipping_number = shipping_number;
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

    public String getSecure_key() {
        return secure_key;
    }

    public void setSecure_key(String secure_key) {
        this.secure_key = secure_key;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
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

    public String getTotal_discounts() {
        return total_discounts;
    }

    public void setTotal_discounts(String total_discounts) {
        this.total_discounts = total_discounts;
    }

    public String getTotal_discounts_tax_incl() {
        return total_discounts_tax_incl;
    }

    public void setTotal_discounts_tax_incl(String total_discounts_tax_incl) {
        this.total_discounts_tax_incl = total_discounts_tax_incl;
    }

    public String getTotal_discounts_tax_excl() {
        return total_discounts_tax_excl;
    }

    public void setTotal_discounts_tax_excl(String total_discounts_tax_excl) {
        this.total_discounts_tax_excl = total_discounts_tax_excl;
    }

    public String getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(String total_paid) {
        this.total_paid = total_paid;
    }

    public String getTotal_paid_tax_incl() {
        return total_paid_tax_incl;
    }

    public void setTotal_paid_tax_incl(String total_paid_tax_incl) {
        this.total_paid_tax_incl = total_paid_tax_incl;
    }

    public String getTotal_paid_tax_excl() {
        return total_paid_tax_excl;
    }

    public void setTotal_paid_tax_excl(String total_paid_tax_excl) {
        this.total_paid_tax_excl = total_paid_tax_excl;
    }

    public String getTotal_paid_real() {
        return total_paid_real;
    }

    public void setTotal_paid_real(String total_paid_real) {
        this.total_paid_real = total_paid_real;
    }

    public String getTotal_products() {
        return total_products;
    }

    public void setTotal_products(String total_products) {
        this.total_products = total_products;
    }

    public String getTotal_products_wt() {
        return total_products_wt;
    }

    public void setTotal_products_wt(String total_products_wt) {
        this.total_products_wt = total_products_wt;
    }

    public String getTotal_shipping() {
        return total_shipping;
    }

    public void setTotal_shipping(String total_shipping) {
        this.total_shipping = total_shipping;
    }

    public String getTotal_shipping_tax_incl() {
        return total_shipping_tax_incl;
    }

    public void setTotal_shipping_tax_incl(String total_shipping_tax_incl) {
        this.total_shipping_tax_incl = total_shipping_tax_incl;
    }

    public String getTotal_shipping_tax_excl() {
        return total_shipping_tax_excl;
    }

    public void setTotal_shipping_tax_excl(String total_shipping_tax_excl) {
        this.total_shipping_tax_excl = total_shipping_tax_excl;
    }

    public String getCarrier_tax_rate() {
        return carrier_tax_rate;
    }

    public void setCarrier_tax_rate(String carrier_tax_rate) {
        this.carrier_tax_rate = carrier_tax_rate;
    }

    public String getTotal_wrapping() {
        return total_wrapping;
    }

    public void setTotal_wrapping(String total_wrapping) {
        this.total_wrapping = total_wrapping;
    }

    public String getTotal_wrapping_tax_incl() {
        return total_wrapping_tax_incl;
    }

    public void setTotal_wrapping_tax_incl(String total_wrapping_tax_incl) {
        this.total_wrapping_tax_incl = total_wrapping_tax_incl;
    }

    public String getTotal_wrapping_tax_excl() {
        return total_wrapping_tax_excl;
    }

    public void setTotal_wrapping_tax_excl(String total_wrapping_tax_excl) {
        this.total_wrapping_tax_excl = total_wrapping_tax_excl;
    }

    public String getRound_mode() {
        return round_mode;
    }

    public void setRound_mode(String round_mode) {
        this.round_mode = round_mode;
    }

    public String getRound_type() {
        return round_type;
    }

    public void setRound_type(String round_type) {
        this.round_type = round_type;
    }

    public String getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(String conversion_rate) {
        this.conversion_rate = conversion_rate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public ArrayList<OrderItem> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<OrderItem> productos) {
        this.productos = productos;
    }
}
