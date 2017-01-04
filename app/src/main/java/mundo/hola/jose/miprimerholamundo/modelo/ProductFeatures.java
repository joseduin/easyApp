package mundo.hola.jose.miprimerholamundo.modelo;

import java.io.Serializable;

/**
 * Created by Jose on 13/12/2016.
 */

@SuppressWarnings("serial")
public class ProductFeatures implements Serializable {

    private String id;
    private String id_feature_value;

    public ProductFeatures(String id_feature_value, String id) {
        this.id_feature_value = id_feature_value;
        this.id = id;
    }

    public ProductFeatures() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_feature_value() {
        return id_feature_value;
    }

    public void setId_feature_value(String id_feature_value) {
        this.id_feature_value = id_feature_value;
    }
}
