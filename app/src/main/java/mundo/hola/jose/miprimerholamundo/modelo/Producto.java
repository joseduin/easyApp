package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jose on 23/9/2016.
 */

@SuppressWarnings("serial")
public class Producto implements Serializable {

    private String id;
    private String id_manufacturer;
    private String id_supplier;
    private String id_category_default;
    private String neew;
    private String cache_default_attribute;
    private String id_default_image;
    private String id_default_combination;
    private String id_tax_rules_group;
    private String position_in_category;
    private String type;
    private String id_shop_default;
    private String reference;
    private String supplier_reference;
    private String location;
    private String width;
    private String height;
    private String depth;
    private String weight;
    private String quantity_discount;
    private String ean13;
    private String upc;
    private String cache_is_pack;
    private String cache_has_attachments;
    private String is_virtual;
    private String on_sale;
    private String online_only;
    private String ecotax;
    private String minimal_quantity;
    private String price;
    private String wholesale_price;
    private String unity;
    private String unit_price_ratio;
    private String additional_shipping_cost;
    private String customizable;
    private String text_fields;
    private String uploadable_files;
    private String active;
    private String redirect_type;
    private String id_product_redirected;
    private String available_for_order;
    private String available_date;
    private String condition;
    private String show_price;
    private String indexed;
    private String visibility;
    private String advanced_stock_management;
    private String date_add;
    private String date_upd;
    private String pack_stock_type;
    private String meta_description;
    private String meta_keywords;
    private String meta_title;
    private String link_rewrite;
    private String name;
    private String description;
    private String description_short;
    private String available_now;
    private String available_later;
    private ArrayList<String> categoria;
    private ArrayList<String> imagenes;
    private ArrayList<String> tags;
    private ArrayList<String> stock_availables;
    private ArrayList<Descuento> descuentos;
    private ArrayList<ProductFeatures> features;

    public Producto() {}

    public Producto(String id, String id_manufacturer, String id_supplier, String id_category_default, String neew, String cache_default_attribute, String id_default_image, String id_default_combination, String id_tax_rules_group, String position_in_category, String type, String id_shop_default, String reference, String supplier_reference, String location, String width, String height, String depth, String weight, String quantity_discount, String ean13, String upc, String cache_is_pack, String cache_has_attachments, String is_virtual, String on_sale, String online_only, String ecotax, String minimal_quantity, String price, String wholesale_price, String unity, String unit_price_ratio, String additional_shipping_cost, String customizable, String text_fields, String uploadable_files, String active, String redirect_type, String id_product_redirected, String available_for_order, String available_date, String condition, String show_price, String indexed, String visibility, String advanced_stock_management, String date_add, String date_upd, String pack_stock_type, String meta_description, String meta_keywords, String meta_title, String link_rewrite, String name, String description, String description_short, String available_now, String available_later, ArrayList<String> categoria, ArrayList<String> imagenes, ArrayList<String> tags, ArrayList<String> stock_availables, ArrayList<Descuento> descuentos, ArrayList<ProductFeatures> features) {
        this.id = id;
        this.id_manufacturer = id_manufacturer;
        this.id_supplier = id_supplier;
        this.id_category_default = id_category_default;
        this.neew = neew;
        this.cache_default_attribute = cache_default_attribute;
        this.id_default_image = id_default_image;
        this.id_default_combination = id_default_combination;
        this.id_tax_rules_group = id_tax_rules_group;
        this.position_in_category = position_in_category;
        this.type = type;
        this.id_shop_default = id_shop_default;
        this.reference = reference;
        this.supplier_reference = supplier_reference;
        this.location = location;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.quantity_discount = quantity_discount;
        this.ean13 = ean13;
        this.upc = upc;
        this.cache_is_pack = cache_is_pack;
        this.cache_has_attachments = cache_has_attachments;
        this.is_virtual = is_virtual;
        this.on_sale = on_sale;
        this.online_only = online_only;
        this.ecotax = ecotax;
        this.minimal_quantity = minimal_quantity;
        this.price = price;
        this.wholesale_price = wholesale_price;
        this.unity = unity;
        this.unit_price_ratio = unit_price_ratio;
        this.additional_shipping_cost = additional_shipping_cost;
        this.customizable = customizable;
        this.text_fields = text_fields;
        this.uploadable_files = uploadable_files;
        this.active = active;
        this.redirect_type = redirect_type;
        this.id_product_redirected = id_product_redirected;
        this.available_for_order = available_for_order;
        this.available_date = available_date;
        this.condition = condition;
        this.show_price = show_price;
        this.indexed = indexed;
        this.visibility = visibility;
        this.advanced_stock_management = advanced_stock_management;
        this.date_add = date_add;
        this.date_upd = date_upd;
        this.pack_stock_type = pack_stock_type;
        this.meta_description = meta_description;
        this.meta_keywords = meta_keywords;
        this.meta_title = meta_title;
        this.link_rewrite = link_rewrite;
        this.name = name;
        this.description = description;
        this.description_short = description_short;
        this.available_now = available_now;
        this.available_later = available_later;
        this.categoria = categoria;
        this.imagenes = imagenes;
        this.tags = tags;
        this.stock_availables = stock_availables;
        this.descuentos = descuentos;
        this.features = features;
    }

    public ArrayList<ProductFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<ProductFeatures> features) {
        this.features = features;
    }

    public ArrayList<Descuento> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(ArrayList<Descuento> descuentos) {
        this.descuentos = descuentos;
    }

    public ArrayList<String> getStock_availables() {
        return stock_availables;
    }

    public void setStock_availables(ArrayList<String> stock_availables) {
        this.stock_availables = stock_availables;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId_category_default() {
        return id_category_default;
    }

    public void setId_category_default(String id_category_default) {
        this.id_category_default = id_category_default;
    }

    public String getNeew() {
        return neew;
    }

    public void setNeew(String neew) {
        this.neew = neew;
    }

    public String getCache_default_attribute() {
        return cache_default_attribute;
    }

    public void setCache_default_attribute(String cache_default_attribute) {
        this.cache_default_attribute = cache_default_attribute;
    }

    public String getId_default_image() {
        return id_default_image;
    }

    public void setId_default_image(String id_default_image) {
        this.id_default_image = id_default_image;
    }

    public String getId_default_combination() {
        return id_default_combination;
    }

    public void setId_default_combination(String id_default_combination) {
        this.id_default_combination = id_default_combination;
    }

    public String getId_tax_rules_group() {
        return id_tax_rules_group;
    }

    public void setId_tax_rules_group(String id_tax_rules_group) {
        this.id_tax_rules_group = id_tax_rules_group;
    }

    public String getPosition_in_category() {
        return position_in_category;
    }

    public void setPosition_in_category(String position_in_category) {
        this.position_in_category = position_in_category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId_shop_default() {
        return id_shop_default;
    }

    public void setId_shop_default(String id_shop_default) {
        this.id_shop_default = id_shop_default;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSupplier_reference() {
        return supplier_reference;
    }

    public void setSupplier_reference(String supplier_reference) {
        this.supplier_reference = supplier_reference;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getQuantity_discount() {
        return quantity_discount;
    }

    public void setQuantity_discount(String quantity_discount) {
        this.quantity_discount = quantity_discount;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCache_is_pack() {
        return cache_is_pack;
    }

    public void setCache_is_pack(String cache_is_pack) {
        this.cache_is_pack = cache_is_pack;
    }

    public String getCache_has_attachments() {
        return cache_has_attachments;
    }

    public void setCache_has_attachments(String cache_has_attachments) {
        this.cache_has_attachments = cache_has_attachments;
    }

    public String getIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(String is_virtual) {
        this.is_virtual = is_virtual;
    }

    public String getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(String on_sale) {
        this.on_sale = on_sale;
    }

    public String getOnline_only() {
        return online_only;
    }

    public void setOnline_only(String online_only) {
        this.online_only = online_only;
    }

    public String getEcotax() {
        return ecotax;
    }

    public void setEcotax(String ecotax) {
        this.ecotax = ecotax;
    }

    public String getMinimal_quantity() {
        return minimal_quantity;
    }

    public void setMinimal_quantity(String minimal_quantity) {
        this.minimal_quantity = minimal_quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWholesale_price() {
        return wholesale_price;
    }

    public void setWholesale_price(String wholesale_price) {
        this.wholesale_price = wholesale_price;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getUnit_price_ratio() {
        return unit_price_ratio;
    }

    public void setUnit_price_ratio(String unit_price_ratio) {
        this.unit_price_ratio = unit_price_ratio;
    }

    public String getAdditional_shipping_cost() {
        return additional_shipping_cost;
    }

    public void setAdditional_shipping_cost(String additional_shipping_cost) {
        this.additional_shipping_cost = additional_shipping_cost;
    }

    public String getCustomizable() {
        return customizable;
    }

    public void setCustomizable(String customizable) {
        this.customizable = customizable;
    }

    public String getText_fields() {
        return text_fields;
    }

    public void setText_fields(String text_fields) {
        this.text_fields = text_fields;
    }

    public String getUploadable_files() {
        return uploadable_files;
    }

    public void setUploadable_files(String uploadable_files) {
        this.uploadable_files = uploadable_files;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRedirect_type() {
        return redirect_type;
    }

    public void setRedirect_type(String redirect_type) {
        this.redirect_type = redirect_type;
    }

    public String getId_product_redirected() {
        return id_product_redirected;
    }

    public void setId_product_redirected(String id_product_redirected) {
        this.id_product_redirected = id_product_redirected;
    }

    public String getAvailable_for_order() {
        return available_for_order;
    }

    public void setAvailable_for_order(String available_for_order) {
        this.available_for_order = available_for_order;
    }

    public String getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(String available_date) {
        this.available_date = available_date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getShow_price() {
        return show_price;
    }

    public void setShow_price(String show_price) {
        this.show_price = show_price;
    }

    public String getIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getAdvanced_stock_management() {
        return advanced_stock_management;
    }

    public void setAdvanced_stock_management(String advanced_stock_management) {
        this.advanced_stock_management = advanced_stock_management;
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

    public String getPack_stock_type() {
        return pack_stock_type;
    }

    public void setPack_stock_type(String pack_stock_type) {
        this.pack_stock_type = pack_stock_type;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) {
        this.meta_description = meta_description;
    }

    public String getMeta_keywords() {
        return meta_keywords;
    }

    public void setMeta_keywords(String meta_keywords) {
        this.meta_keywords = meta_keywords;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getLink_rewrite() {
        return link_rewrite;
    }

    public void setLink_rewrite(String link_rewrite) {
        this.link_rewrite = link_rewrite;
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

    public String getDescription_short() {
        return description_short;
    }

    public void setDescription_short(String description_short) {
        this.description_short = description_short;
    }

    public String getAvailable_now() {
        return available_now;
    }

    public void setAvailable_now(String available_now) {
        this.available_now = available_now;
    }

    public String getAvailable_later() {
        return available_later;
    }

    public void setAvailable_later(String available_later) {
        this.available_later = available_later;
    }

    public ArrayList<String> getCategoria() {
        return categoria;
    }

    public void setCategoria(ArrayList<String> categoria) {
        this.categoria = categoria;
    }

    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


}
