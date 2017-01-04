package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;

/**
 * Created by Jose on 6/11/2016.
 */

@SuppressWarnings("serial")
public class Direccion implements Serializable {

    private String id;
    private String id_customer;
    private String id_manufacturer;
    private String id_supplier;
    private String id_warehouse;
    private String id_country;
    private String id_state;
    private String alias;
    private String company;
    private String lastname;
    private String firstname;
    private String vat_number;
    private String address1;
    private String address2;
    private String postcode;
    private String city;
    private String other;
    private String phone;
    private String phone_mobile;
    private String dni;
    private String deleted;
    private String date_add;
    private String date_upd;

    public Direccion(String id, String id_customer, String id_manufacturer, String id_supplier, String id_warehouse, String id_country, String id_state, String alias, String company, String lastname, String firstname, String vat_number, String address1, String address2, String postcode, String city, String other, String phone, String phone_mobile, String dni, String deleted, String date_add, String date_upd) {
        this.id = id;
        this.id_customer = id_customer;
        this.id_manufacturer = id_manufacturer;
        this.id_supplier = id_supplier;
        this.id_warehouse = id_warehouse;
        this.id_country = id_country;
        this.id_state = id_state;
        this.alias = alias;
        this.company = company;
        this.lastname = lastname;
        this.firstname = firstname;
        this.vat_number = vat_number;
        this.address1 = address1;
        this.address2 = address2;
        this.postcode = postcode;
        this.city = city;
        this.other = other;
        this.phone = phone;
        this.phone_mobile = phone_mobile;
        this.dni = dni;
        this.deleted = deleted;
        this.date_add = date_add;
        this.date_upd = date_upd;
    }

    public Direccion() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_manufacturer() {
        return id_manufacturer;
    }

    public void setId_manufacturer(String id_manufacturer) {
        this.id_manufacturer = id_manufacturer;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getId_warehouse() {
        return id_warehouse;
    }

    public void setId_warehouse(String id_warehouse) {
        this.id_warehouse = id_warehouse;
    }

    public String getId_country() {
        return id_country;
    }

    public void setId_country(String id_country) {
        this.id_country = id_country;
    }

    public String getId_state() {
        return id_state;
    }

    public void setId_state(String id_state) {
        this.id_state = id_state;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_mobile() {
        return phone_mobile;
    }

    public void setPhone_mobile(String phone_mobile) {
        this.phone_mobile = phone_mobile;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
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
}
