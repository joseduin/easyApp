package mundo.hola.jose.miprimerholamundo.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jose on 23/9/2016.
 */
@SuppressWarnings("serial")
public class Categoria implements Serializable {

    private String id;
    private String id_parent;
    private String level_depth;
    private String nb_products_recursive;
    private String active;
    private String id_shop_default;
    private String is_root_category;
    private String position;
    private String  date_add;
    private String  date_upd;
    private String  name;
    private String link_rewrite;
    private String description;
    private String meta_title;
    private String meta_description;
    private String meta_keywords;
    private String imagen;                  // Bitmap
    private String imagen_default;          // Bitmap
    private ArrayList<String> subCategorias;
    private ArrayList<String> productos;

    public Categoria(String id, String id_parent, String level_depth, String nb_products_recursive, String active, String id_shop_default, String is_root_category, String position, String date_add, String date_upd, String name, String link_rewrite, String description, String meta_title, String meta_description, String meta_keywords, String imagen, String imagen_mini, ArrayList<String> subCategorias, ArrayList<String> productos) {
        this.id = id;
        this.id_parent = id_parent;
        this.level_depth = level_depth;
        this.nb_products_recursive = nb_products_recursive;
        this.active = active;
        this.id_shop_default = id_shop_default;
        this.is_root_category = is_root_category;
        this.position = position;
        this.date_add = date_add;
        this.date_upd = date_upd;
        this.name = name;
        this.link_rewrite = link_rewrite;
        this.description = description;
        this.meta_title = meta_title;
        this.meta_description = meta_description;
        this.meta_keywords = meta_keywords;
        this.imagen = imagen;
        this.imagen_default = imagen_mini;
        this.subCategorias = subCategorias;
        this.productos = productos;
    }

    public Categoria() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_parent() {
        return id_parent;
    }

    public void setId_parent(String id_parent) {
        this.id_parent = id_parent;
    }

    public String getLevel_depth() {
        return level_depth;
    }

    public void setLevel_depth(String level_depth) {
        this.level_depth = level_depth;
    }

    public String getNb_products_recursive() {
        return nb_products_recursive;
    }

    public void setNb_products_recursive(String nb_products_recursive) { this.nb_products_recursive = nb_products_recursive; }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId_shop_default() {
        return id_shop_default;
    }

    public void setId_shop_default(String id_shop_default) { this.id_shop_default = id_shop_default; }

    public String getIs_root_category() {
        return is_root_category;
    }

    public void setIs_root_category(String is_root_category) { this.is_root_category = is_root_category; }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink_rewrite() {
        return link_rewrite;
    }

    public void setLink_rewrite(String link_rewrite) {
        this.link_rewrite = link_rewrite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeta_title() {
        return meta_title;
    }

    public void setMeta_title(String meta_title) {
        this.meta_title = meta_title;
    }

    public String getMeta_description() {
        return meta_description;
    }

    public void setMeta_description(String meta_description) { this.meta_description = meta_description; }

    public String getMeta_keywords() {
        return meta_keywords;
    }

    public void setMeta_keywords(String meta_keywords) {
        this.meta_keywords = meta_keywords;
    }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getImagen_default() { return imagen_default; }

    public void setImagen_default(String imagen_default) { this.imagen_default = imagen_default; }

    public ArrayList<String> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(ArrayList<String> subCategorias) { this.subCategorias = subCategorias; }

    public ArrayList<String> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<String> productos) { this.productos = productos; }

}
