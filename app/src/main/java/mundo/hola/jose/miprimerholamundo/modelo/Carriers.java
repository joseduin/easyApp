package mundo.hola.jose.miprimerholamundo.modelo;

/**
 * Created by Jose on 11/11/2016.
 */

public class Carriers {

    private String id;
    private String deleted;
    private String is_module;
    private String id_tax_rules_group;      // 1.- TIENE IVA                           0.- NO TIENE IVA
    private String id_reference;
    private String name;
    private String active;
    private String is_free;
    private String url;
    private String shipping_handling;       // COSTES DE MANIPULACION 1.- SI            0.- NO
    private String shipping_external;
    private String range_behavior;          //1.- Aplicar el coste mas alto de la gama  0.- Desabilitar transportista
    private String shipping_method;
    private String max_width;
    private String max_height;
    private String max_depth;
    private String max_weight;
    private String grade;
    private String external_module_name;
    private String need_range;
    private String position;
    private String delay;
    private Deliveries deliveries;

    public Carriers(String delay, String id, String deleted, String is_module, String id_tax_rules_group, String id_reference, String name, String active, String is_free, String url, String shipping_handling, String shipping_external, String range_behavior, String shipping_method, String max_width, String max_height, String max_depth, String max_weight, String grade, String external_module_name, String need_range, String position, Deliveries deliveries) {
        this.delay = delay;
        this.id = id;
        this.deleted = deleted;
        this.is_module = is_module;
        this.id_tax_rules_group = id_tax_rules_group;
        this.id_reference = id_reference;
        this.name = name;
        this.active = active;
        this.is_free = is_free;
        this.url = url;
        this.shipping_handling = shipping_handling;
        this.shipping_external = shipping_external;
        this.range_behavior = range_behavior;
        this.shipping_method = shipping_method;
        this.max_width = max_width;
        this.max_height = max_height;
        this.max_depth = max_depth;
        this.max_weight = max_weight;
        this.grade = grade;
        this.external_module_name = external_module_name;
        this.need_range = need_range;
        this.position = position;
        this.deliveries = deliveries;
    }

    public Carriers() {}

    public Deliveries getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Deliveries deliveries) {
        this.deliveries = deliveries;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getIs_module() {
        return is_module;
    }

    public void setIs_module(String is_module) {
        this.is_module = is_module;
    }

    public String getId_tax_rules_group() {
        return id_tax_rules_group;
    }

    public void setId_tax_rules_group(String id_tax_rules_group) {
        this.id_tax_rules_group = id_tax_rules_group;
    }

    public String getId_reference() {
        return id_reference;
    }

    public void setId_reference(String id_reference) {
        this.id_reference = id_reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShipping_handling() {
        return shipping_handling;
    }

    public void setShipping_handling(String shipping_handling) {
        this.shipping_handling = shipping_handling;
    }

    public String getShipping_external() {
        return shipping_external;
    }

    public void setShipping_external(String shipping_external) {
        this.shipping_external = shipping_external;
    }

    public String getRange_behavior() {
        return range_behavior;
    }

    public void setRange_behavior(String range_behavior) {
        this.range_behavior = range_behavior;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public String getMax_width() {
        return max_width;
    }

    public void setMax_width(String max_width) {
        this.max_width = max_width;
    }

    public String getMax_height() {
        return max_height;
    }

    public void setMax_height(String max_height) {
        this.max_height = max_height;
    }

    public String getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(String max_depth) {
        this.max_depth = max_depth;
    }

    public String getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(String max_weight) {
        this.max_weight = max_weight;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getExternal_module_name() {
        return external_module_name;
    }

    public void setExternal_module_name(String external_module_name) {
        this.external_module_name = external_module_name;
    }

    public String getNeed_range() {
        return need_range;
    }

    public void setNeed_range(String need_range) {
        this.need_range = need_range;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
