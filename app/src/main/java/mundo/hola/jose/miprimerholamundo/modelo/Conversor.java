package mundo.hola.jose.miprimerholamundo.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Jose on 23/9/2016.
 */

public class Conversor {

    private String KEY = "ID1TSBAWH6DRZ3TBGY2Q3MK2NJ11KEVT";
    private static String COOKIE_KEY = "GDgdWYxjQFPOyabc7OCT5zk8L4xB2UZzTyyIzZ7E4KLUOw8g4i67Q9s2";

    public String WEB_PAGE = "http://easymarket.ec/alpha/api";
    public String WEB_API_AUX = "http://app-easymarket.com/service/examples/";
    //                           https://webserviceeasy-kevingn.c9users.io/examples/
    public String ERROR_LOADING = "Revise su conexion a internet";
    public DateFormat dateFormat = new SimpleDateFormat("yyyy-MMdd HH:mm:ss");
    private int IVA = 12;
    private Context context;

    public Conversor() {}

    /*  Si se usa parametros de filtros, se deben colocar antes de llamar
    *   a este metodo
    * */
    public void parametrosBasicos(RequestParams parametros) {
        parametrosKey(parametros);
        parametros.add("output_format","JSON");
    }

    public void parametrosKey(RequestParams parametros) {
        parametros.add("ws_key", KEY);
    }

    /*  Manejar JSON
    * * * * * * * * * * */
    public JsonObject stringToJsonObject(String cadena) {
        JsonParser jp = new JsonParser ();
        JsonElement je = jp.parse(cadena);
        return JsonElementToJsonObject(je);
    }
    public JsonObject JsonElementToJsonObject(JsonElement je) {
        return je.getAsJsonObject();
    }
    public String buscarJson(JsonElement je, String buscar, String valor) {
        if (valor.contains( "\"" + buscar + "\"")) {
            JsonObject jo = JsonElementToJsonObject(je);
            return validarJsonObjToString(jo.get(buscar));
        } else {
            return "Revisar Json";
        }
    }
    public String validarJsonObjToString(JsonElement jsonElement) {
        String a = jsonElement.toString();
        return a.replaceAll("\"", "");
    }
   /* public String buscarJson(JsonElement je, String array, String buscar) {
        JsonObject jo = JsonElementToJsonObject(je);
        return buscarJson(jo.get(array), buscar);
    }*/

    public void setContext(Context context) {
        this.context = context;
    }
    /*  Buscar Categorias
    * * * * * * * * * * */
    public Categoria buscarCategoria(String res) {
        JsonObject joCategoria = stringToJsonObject(res);
        JsonElement jeCategoria = joCategoria.getAsJsonObject("category");

        ArrayList<String> subCategorias = new ArrayList<String>();
        ArrayList<String> productos = new ArrayList<String>();

        if (res.contains("\"associations\"")) {
            JsonObject hijos = stringToJsonObject(buscarJson(jeCategoria, "associations", res));

            if (res.contains("\"categories\"")) {
                llenarHijos(subCategorias, hijos.getAsJsonArray("categories"), res);
            }
            if (res.contains("\"products\"")) {
                llenarHijos(productos, hijos.getAsJsonArray("products"), res);
            }
        }

        return  new Categoria(
                buscarJson(jeCategoria, "id", res),
                buscarJson(jeCategoria, "id_parent", res),
                buscarJson(jeCategoria, "level_depth", res),
                buscarJson(jeCategoria, "nb_products_recursive", res),
                buscarJson(jeCategoria, "active", res),
                buscarJson(jeCategoria, "id_shop_default", res),
                buscarJson(jeCategoria, "is_root_category", res),
                buscarJson(jeCategoria, "position", res),
                buscarJson(jeCategoria, "date_add", res),
                buscarJson(jeCategoria, "date_upd", res),
                buscarJson(jeCategoria, "name", res),
                buscarJson(jeCategoria, "link_rewrite", res),
                buscarJson(jeCategoria, "description", res),
                buscarJson(jeCategoria, "meta_title", res),
                buscarJson(jeCategoria, "meta_description", res),
                buscarJson(jeCategoria, "meta_keywords", res),
                null,
                null,
                subCategorias,
                productos);
    }

    /*  Buscar Productos
    * * * * * * * * * * */
    public Producto buscarProducto(String res) {
        JsonObject joProducto = stringToJsonObject(res);
        JsonElement jeProducto = joProducto.getAsJsonObject("product");

        return getProducto(jeProducto, jeProducto.toString());
    }

    public ArrayList<Producto> buscarProductoSearch(String res) {
        ArrayList<Producto> productos = new ArrayList<>();
        if (res.equals("[]")) {
            return productos;
        } else {
            JsonObject joProducto = stringToJsonObject(res);
            JsonArray jeProducto = joProducto.getAsJsonArray("products");
            for (JsonElement producto : jeProducto) {
                productos.add(getProducto(producto, producto.toString()));
            }
        }
        return productos;
    }

    private Producto getProducto(JsonElement jeProducto, String res) {
        ArrayList<String> categoria = new ArrayList<String>();
        ArrayList<String> imagenes = new ArrayList<String>();
        ArrayList<String> tags = new ArrayList<String>();
        ArrayList<String> stock_availables = new ArrayList<String>();
        ArrayList<ProductFeatures> features = new ArrayList<ProductFeatures>();

        if (res.contains("\"associations\"")) {
            JsonObject hijos = stringToJsonObject(buscarJson(jeProducto, "associations",res));

            if (res.contains("\"categories\"")) {
                llenarHijos(categoria, hijos.getAsJsonArray("categories"), res);
            }
            if (res.contains("\"images\"")) {
                llenarHijos(imagenes, hijos.getAsJsonArray("images"), res);
            }
            if (res.contains("\"tags\"")) {
                llenarHijos(tags, hijos.getAsJsonArray("tags"), res);
            }
            if (res.contains("\"stock_availables\"")) {
                llenarHijos(stock_availables, hijos.getAsJsonArray("stock_availables"), res);
            }
            if (res.contains("\"product_features\"")) {
                llenarProductFeature(features, hijos.getAsJsonArray("product_features"), res);
            }
        }

        return  new Producto(
                buscarJson(jeProducto, "id", res),
                buscarJson(jeProducto, "id_manufacturer", res),
                buscarJson(jeProducto, "id_supplier", res),
                buscarJson(jeProducto, "id_category_default", res),
                buscarJson(jeProducto, "new", res),
                buscarJson(jeProducto, "cache_default_attribute", res),
                buscarJson(jeProducto, "id_default_image", res),
                buscarJson(jeProducto, "id_default_combination", res),
                buscarJson(jeProducto, "id_tax_rules_group", res),
                buscarJson(jeProducto, "position_in_category", res),
                buscarJson(jeProducto, "type", res),
                buscarJson(jeProducto, "id_shop_default", res),
                buscarJson(jeProducto, "reference", res),
                buscarJson(jeProducto, "supplier_reference", res),
                buscarJson(jeProducto, "location", res),
                buscarJson(jeProducto, "width", res),
                buscarJson(jeProducto, "height", res),
                buscarJson(jeProducto, "depth", res),
                buscarJson(jeProducto, "weight", res),
                buscarJson(jeProducto, "quantity_discount", res),
                buscarJson(jeProducto, "ean13", res),
                buscarJson(jeProducto, "upc", res),
                buscarJson(jeProducto, "cache_is_pack", res),
                buscarJson(jeProducto, "cache_has_attachments", res),
                buscarJson(jeProducto, "is_virtual", res),
                buscarJson(jeProducto, "on_sale", res),
                buscarJson(jeProducto, "online_only", res),
                buscarJson(jeProducto, "ecotax", res),
                buscarJson(jeProducto, "minimal_quantity", res),
                String.format("%.2f", Double.valueOf(buscarJson(jeProducto, "price", res)) + ((Double.valueOf(buscarJson(jeProducto, "price", res)) * IVA) / 100)),
                buscarJson(jeProducto, "wholesale_price", res),
                buscarJson(jeProducto, "unity", res),
                buscarJson(jeProducto, "unit_price_ratio", res),
                buscarJson(jeProducto, "additional_shipping_cost", res),
                buscarJson(jeProducto, "customizable", res),
                buscarJson(jeProducto, "text_fields", res),
                buscarJson(jeProducto, "uploadable_files", res),
                buscarJson(jeProducto, "active", res),
                buscarJson(jeProducto, "redirect_type", res),
                buscarJson(jeProducto, "id_product_redirected", res),
                buscarJson(jeProducto, "available_for_order", res),
                buscarJson(jeProducto, "available_date", res),
                buscarJson(jeProducto, "condition", res).equals("new") ? "Nuevo" : buscarJson(jeProducto, "condition", res).equals("used") ? "Usado" : "Renovado",
                buscarJson(jeProducto, "show_price", res),
                buscarJson(jeProducto, "indexed", res),
                buscarJson(jeProducto, "visibility", res),
                buscarJson(jeProducto, "advanced_stock_management", res),
                buscarJson(jeProducto, "date_add", res),
                buscarJson(jeProducto, "date_upd", res),
                buscarJson(jeProducto, "pack_stock_type", res),
                buscarJson(jeProducto, "meta_description", res),
                buscarJson(jeProducto, "meta_keywords", res),
                buscarJson(jeProducto, "meta_title", res),
                buscarJson(jeProducto, "link_rewrite", res),
                buscarJson(jeProducto, "name", res),
                buscarJson(jeProducto, "description", res),
                buscarJson(jeProducto, "description_short", res),
                buscarJson(jeProducto, "available_now", res),
                buscarJson(jeProducto, "available_later", res),
                categoria,
                imagenes,
                tags,
                stock_availables,
                null,
                features
        );
    }

    public void llenarProductFeature(ArrayList<ProductFeatures> lista, JsonArray hijos, String res) {
        for (JsonElement hijo : hijos) {
            lista.add(new ProductFeatures(buscarJson(hijo, "id", res), buscarJson(hijo, "id_feature_value", res)));
        }
    }

    /*  Buscar Categorias & Productos
    * * * * * * * * * * */
    public void llenarHijos(ArrayList<String> lista, JsonArray hijos, String res) {
        for (JsonElement hijo : hijos) {
            lista.add(buscarJson(hijo, "id", res));
        }
    }

    public String buscarStock(String res) {
        JsonObject joProducto = stringToJsonObject(res);
        JsonElement jeProducto = joProducto.getAsJsonObject("stock_available");
        return buscarJson(jeProducto, "quantity", res).equals("0") ? "No está disponible" : buscarJson(jeProducto, "quantity", res);
    }

    public String existeCarrito(String res) {
        if (res.equals("[]")) {
            return "no";
        } else {
            String carritoActual = "";
            JsonObject joCategoria = stringToJsonObject(res);
            JsonArray carritos = joCategoria.getAsJsonArray("carts");
            for (JsonElement carrito : carritos) {
                carritoActual =  buscarJson(carrito, "id", res);
            }
            return carritoActual;
        }
    }

    public String buscarFeatures(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("product_feature_value");
        return buscarJson(je, "value", res);
    }

    public String buscarFeaturesMedidas(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("product_feature");
        return buscarJson(je, "name", res);
    }

    public Carrito buscarCarrito(String res) {
        JsonObject joCarrito = stringToJsonObject(res);
        JsonElement jeCarrito = joCarrito.getAsJsonObject("cart");

        ArrayList<CarritoDetalle> carritoDetalle = new ArrayList<>();

        boolean existenArticulosEnElCarrito = true;
        if (res.contains("\"associations\"")) {
            JsonObject hijos = stringToJsonObject(buscarJson(jeCarrito, "associations",res));

            if (res.contains("\"cart_rows\"")) {
                carritoDetalle(carritoDetalle, hijos.getAsJsonArray("cart_rows"), res);
            }
        } else {
            existenArticulosEnElCarrito = false;        // No se encontraron articulos en el carrito
        }

        Carrito carrito = new Carrito();
        if (!existenArticulosEnElCarrito) {
            carrito.setId("no");
        } else {
            carrito = new Carrito(buscarJson(jeCarrito, "id", res),
                    buscarJson(jeCarrito, "id_address_delivery", res),
                    buscarJson(jeCarrito, "id_address_invoice", res),
                    buscarJson(jeCarrito, "id_currency", res),
                    buscarJson(jeCarrito, "id_customer", res),
                    buscarJson(jeCarrito, "id_guest", res),
                    buscarJson(jeCarrito, "id_lang", res),
                    buscarJson(jeCarrito, "id_shop_group", res),
                    buscarJson(jeCarrito, "id_shop", res),
                    buscarJson(jeCarrito, "id_carrier", res),
                    buscarJson(jeCarrito, "recyclable", res),
                    buscarJson(jeCarrito, "gift", res),
                    buscarJson(jeCarrito, "gift_message", res),
                    buscarJson(jeCarrito, "mobile_theme", res),
                    buscarJson(jeCarrito, "delivery_option", res),
                    buscarJson(jeCarrito, "secure_key", res),
                    buscarJson(jeCarrito, "allow_seperated_package", res),
                    buscarJson(jeCarrito, "date_add", res),
                    buscarJson(jeCarrito, "date_upd", res),
                    carritoDetalle);
        }
        return carrito;
    }

    private void carritoDetalle(ArrayList<CarritoDetalle> carritoDetalle, JsonArray cart_rows, String res) {
        for (JsonElement hijo : cart_rows) {
            carritoDetalle.add(new CarritoDetalle(buscarJson(hijo, "id_product", res),
                    buscarJson(hijo, "id_product_attribute", res),
                    buscarJson(hijo, "id_address_delivery", res),
                    buscarJson(hijo, "quantity", res)));
        }
    }

    public String existeCustomer(String res) {
        if (res.equals("[]")) {
            return "no";
        } else {
            String id = "";
            JsonObject joCustomer = stringToJsonObject(res);
            JsonArray customers = joCustomer.getAsJsonArray("customers");
            for (JsonElement customer : customers) {
                id =  buscarJson(customer, "id", res);
            }

            return id;
        }
    }

    public ArrayList<String> buscarDirecciones(String res) {
        ArrayList<String> ids = new ArrayList<>();
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("addresses");
            for (JsonElement element : array) {
                ids.add(buscarJson(element, "id", res));
            }

            return ids;
        }
    }

    public String validarQueCarritoNoSeaUnaOrden(String res) {
        String ids = "no";
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("orders");
            for (JsonElement element : array) {
                ids = buscarJson(element, "id", res);
            }

            return ids;
        }
    }

    public Direccion buscarDireccion(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("address");

        return new Direccion(buscarJson(je, "id", res),
                buscarJson(je, "id_customer", res),
                buscarJson(je, "id_manufacturer", res),
                buscarJson(je, "id_supplier", res),
                buscarJson(je, "id_warehouse", res),
                buscarJson(je, "id_country", res),
                buscarJson(je, "id_state", res),
                buscarJson(je, "alias", res),
                buscarJson(je, "company", res),
                buscarJson(je, "lastname", res),
                buscarJson(je, "firstname", res),
                buscarJson(je, "vat_number", res),
                buscarJson(je, "address1", res),
                buscarJson(je, "address2", res),
                buscarJson(je, "postcode", res),
                buscarJson(je, "city", res),
                buscarJson(je, "other", res),
                buscarJson(je, "phone", res),
                buscarJson(je, "phone_mobile", res),
                buscarJson(je, "dni", res),
                buscarJson(je, "deleted", res),
                buscarJson(je, "date_add", res),
                buscarJson(je, "date_upd", res)
                );
    }

    public Customer buscarCustomer(String res) {
        JsonObject joCustomer = stringToJsonObject(res);
        JsonElement jeCustomer = joCustomer.getAsJsonObject("customer");

        return new Customer(
                buscarJson(jeCustomer, "id", res),
                buscarJson(jeCustomer, "date_upd", res),
                buscarJson(jeCustomer, "id_default_group", res),
                buscarJson(jeCustomer, "id_lang", res),
                buscarJson(jeCustomer, "newsletter_date_add", res),
                buscarJson(jeCustomer, "ip_registration_newsletter", res),
                buscarJson(jeCustomer, "last_passwd_gen", res),
                buscarJson(jeCustomer, "secure_key", res),
                buscarJson(jeCustomer, "deleted", res),
                buscarJson(jeCustomer, "passwd", res),
                buscarJson(jeCustomer, "lastname", res),
                buscarJson(jeCustomer, "firstname", res),
                buscarJson(jeCustomer, "email", res),
                buscarJson(jeCustomer, "id_gender", res),
                buscarJson(jeCustomer, "birthday", res),
                buscarJson(jeCustomer, "newsletter", res),
                buscarJson(jeCustomer, "optin", res),
                buscarJson(jeCustomer, "website", res),
                buscarJson(jeCustomer, "company", res),
                buscarJson(jeCustomer, "siret", res),
                buscarJson(jeCustomer, "ape", res),
                buscarJson(jeCustomer, "outstanding_allow_amount", res),
                buscarJson(jeCustomer, "show_public_prices", res),
                buscarJson(jeCustomer, "id_risk", res),
                buscarJson(jeCustomer, "max_payment_days", res),
                buscarJson(jeCustomer, "active", res),
                buscarJson(jeCustomer, "note", res),
                buscarJson(jeCustomer, "is_guest", res),
                buscarJson(jeCustomer, "id_shop", res),
                buscarJson(jeCustomer, "id_shop_group", res),
                buscarJson(jeCustomer, "date_add", res));
    }


    public ArrayList<String> buscarEnvios(String res) {
        ArrayList<String> ids = new ArrayList<>();
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("carriers");
            for (JsonElement element : array) {
                ids.add(buscarJson(element, "id", res));
            }

            return ids;
        }
    }

    public Carriers buscarEnvio(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("carrier");

            return new Carriers(buscarJson(je, "delay", res),
                buscarJson(je, "id", res),
                    buscarJson(je, "deleted", res),
                    buscarJson(je, "is_module", res),
                    buscarJson(je, "id_tax_rules_group", res),
                    buscarJson(je, "id_reference", res),
                    buscarJson(je, "name", res),
                    buscarJson(je, "active", res),
                    buscarJson(je, "is_free", res),
                    buscarJson(je, "url", res),
                    buscarJson(je, "shipping_handling", res),
                    buscarJson(je, "shipping_external", res),
                    buscarJson(je, "range_behavior", res),
                    buscarJson(je, "shipping_method", res),
                    buscarJson(je, "max_width", res),
                    buscarJson(je, "max_height", res),
                    buscarJson(je, "max_depth", res),
                    buscarJson(je, "max_weight", res),
                    buscarJson(je, "grade", res),
                    buscarJson(je, "external_module_name", res),
                    buscarJson(je, "need_range", res),
                    buscarJson(je, "position", res),
                    null
        );
    }

    public ArrayList<String> buscarDescuentos(String res) {
        ArrayList<String> ids = new ArrayList<>();
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("specific_prices");
            for (JsonElement element : array) {
                ids.add(buscarJson(element, "id", res));
            }

            return ids;
        }
    }

    public Descuento buscarDescuento(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("specific_price");

        return new Descuento(buscarJson(je, "to", res),
                buscarJson(je, "id", res),
                buscarJson(je, "id_shop_group", res),
                buscarJson(je, "id_shop", res),
                buscarJson(je, "id_cart", res),
                buscarJson(je, "id_product", res),
                buscarJson(je, "id_product_attribute", res),
                buscarJson(je, "id_currency", res),
                buscarJson(je, "id_country", res),
                buscarJson(je, "id_group", res),
                buscarJson(je, "id_customer", res),
                buscarJson(je, "id_specific_price_rule", res),
                buscarJson(je, "price", res),
                buscarJson(je, "from_quantity", res),
                buscarJson(je, "reduction", res),
                buscarJson(je, "reduction_tax", res),
                buscarJson(je, "reduction_type", res),
                buscarJson(je, "from", res)
                );
    }

    public ArrayList<String> buscarDeliveries(String res) {
        ArrayList<String> ids = new ArrayList<>();
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("deliveries");
            for (JsonElement element : array) {
                ids.add(buscarJson(element, "id", res));
            }

            return ids;
        }
    }

    public Deliveries buscarDelivery(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("delivery");

            return new Deliveries(buscarJson(je, "id", res),
                buscarJson(je, "id_range_price", res),
                buscarJson(je, "id_range_weight", res),
                buscarJson(je, "id_zone", res),
                buscarJson(je, "id_shop_group", res),
                buscarJson(je, "price", res)
        );
    }

    public ArrayList<String> existeOrdenes(String res) {
        ArrayList<String> ids = new ArrayList<>();
        if (res.equals("[]")) {
            return ids;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("orders");
            for (JsonElement element : array) {
                ids.add(buscarJson(element, "id", res));
            }

            return ids;
        }
    }

    public Order buscarOrden(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("order");

        ArrayList<OrderItem> productos = new ArrayList<>();
        JsonObject hij = je.getAsJsonObject();
        JsonObject hijos =  hij.getAsJsonObject("associations");

        for (JsonElement row : hijos.getAsJsonArray("order_rows")) {
            productos.add(new OrderItem(buscarJson(row, "id", row.toString()),
                    buscarJson(row, "product_id", row.toString()),
                    buscarJson(row, "product_attribute_id", row.toString()),
                    buscarJson(row, "product_quantity", row.toString()),
                    buscarJson(row, "product_name", row.toString()),
                    buscarJson(row, "product_reference", row.toString()),
                    buscarJson(row, "product_ean13", row.toString()),
                    buscarJson(row, "product_upc", row.toString()),
                    buscarJson(row, "product_price", row.toString()),
                    buscarJson(row, "unit_price_tax_incl", row.toString()),
                    buscarJson(row, "unit_price_tax_excl", row.toString())
            ));
        }

        return new Order(buscarJson(je, "id", res),
                    buscarJson(je, "id_address_delivery", res),
                    buscarJson(je, "id_address_invoice", res),
                    buscarJson(je, "id_cart", res),
                    buscarJson(je, "id_currency", res),
                    buscarJson(je, "id_lang", res),
                    buscarJson(je, "id_customer", res),
                    buscarJson(je, "id_carrier", res),
                    buscarJson(je, "current_state", res),
                    buscarJson(je, "module", res),
                    buscarJson(je, "invoice_number", res),
                    buscarJson(je, "invoice_date", res),
                    buscarJson(je, "delivery_number", res),
                    buscarJson(je, "delivery_date", res),
                    buscarJson(je, "valid", res),
                    buscarJson(je, "date_add", res),
                    buscarJson(je, "date_upd", res),
                    buscarJson(je, "shipping_number", res),
                    buscarJson(je, "id_shop_group", res),
                    buscarJson(je, "id_shop", res),
                    buscarJson(je, "secure_key", res),
                    buscarJson(je, "payment", res),
                    buscarJson(je, "recyclable", res),
                    buscarJson(je, "gift", res),
                    buscarJson(je, "gift_message", res),
                    buscarJson(je, "mobile_theme", res),
                    buscarJson(je, "total_discounts", res),
                    buscarJson(je, "total_discounts_tax_incl", res),
                    buscarJson(je, "total_discounts_tax_excl", res),
                    buscarJson(je, "total_paid", res),
                    buscarJson(je, "total_paid_tax_incl", res),
                    buscarJson(je, "total_paid_tax_excl", res),
                    buscarJson(je, "total_paid_real", res),
                    buscarJson(je, "total_products", res),
                    buscarJson(je, "total_products_wt", res),
                    buscarJson(je, "total_shipping", res),
                    buscarJson(je, "total_shipping_tax_incl", res),
                    buscarJson(je, "total_shipping_tax_excl", res),
                    buscarJson(je, "carrier_tax_rate", res),
                    buscarJson(je, "total_wrapping", res),
                    buscarJson(je, "total_wrapping_tax_incl", res),
                    buscarJson(je, "total_wrapping_tax_excl", res),
                    buscarJson(je, "round_mode", res),
                    buscarJson(je, "round_type", res),
                    buscarJson(je, "conversion_rate", res),
                    buscarJson(je, "reference", res),
                    productos
                    );
        }

    public String configurations(String res) {
        String id = "";
        if (res.equals("[]")) {
            return id;
        } else {
            JsonObject jo = stringToJsonObject(res);
            JsonArray array = jo.getAsJsonArray("configurations");
            for (JsonElement element : array) {
                id = buscarJson(element, "id", res);
            }

            return id;
        }
    }

    public String configuration(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("configuration");

        return buscarJson(je, "value", res);
    }

    // Revisar Luego
    public String buscarTerminos(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("content");

        return buscarJson(je, "content", res);
    }

    public String[] buscarEstado(String res) {
        JsonObject jo = stringToJsonObject(res);
        JsonElement je = jo.getAsJsonObject("order_state");

        String[] valores = new String[2];
        valores[0] = buscarJson(je, "color", res);
        valores[1] = buscarJson(je, "name", res);
        return valores;
    }

    /*  Mensajes
    * */
    public void mensajeCorto(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void mensajeLargo(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }
    public void errorLoading() {
        mensajeCorto(ERROR_LOADING);
    }

    /*  Convertidores AnythingToAnything
    * */
    public String getMD5(String input) {
        input = COOKIE_KEY + input;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public String html(String cadena) {
        char[] array = cadena.toCharArray();
        String aux = "";
        boolean encontro = false;
        boolean salto = false;
        for(char a : array) {
            if (a == '<') {
                encontro = true;
            } else if (a == '>') {
                aux += a;
                cadena = eliminarhtml(cadena, aux, salto);
                aux = "";
                encontro = false;
                salto = false;
            }
            if (encontro) {
                aux += a;
                if (a == 'p') {
                    salto = true;
                }
            }
        }
        // Salto cadena
        cadena = cadena.replaceAll("#", "\n");

        return cadena;
    }

    private String eliminarhtml(String cadena, String aux, boolean salto) {
        if (salto) {
            return cadena.replace(aux, " # ");    // Valor centinela
        } else {
            return cadena.replace(aux, "");
        }
    }

    public void ActionProcessButtonValidation(ActionProcessButton boton, boolean valor) {
        if (valor) {
            boton.setProgress(100);
            boton.setEnabled(valor);
        } else {
            boton.setProgress(80);
            boton.setEnabled(valor);
        }
    }

}
