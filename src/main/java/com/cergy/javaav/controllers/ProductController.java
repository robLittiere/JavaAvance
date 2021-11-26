package com.cergy.javaav.controllers;

import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    /**
     * Effectue la requète en fonction des paramètres donnés.
     * Une fois la requète effectuée, on récupère une liste de produit
     * @param asc La valeur attribuée au paramètre ASC
     * @param desc La valeur attribuée au paramètre DESC
     * @param params L'ensemble des paramètres rentrés
     * @return Une réponse JSON contenant la liste des produits triée ou non
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> index(@RequestParam (required = false) String asc, @RequestParam (required = false) String desc, @RequestParam (required = false) Map<String,String> params){
        // Regarde si l'utilisateur a saisit desc dans la requète
        if (desc != null){
            if(desc.contains(",")){
                return productDao.listAllBySameOrder(asc, desc);
            }
        }
            // Regarde si l'utilisateur a saisit asc dans la requète
        if (asc != null){
            if(asc.contains(",")){
                return productDao.listAllBySameOrder(asc, desc);
            }
        }
        // Récupère un string contenant DESC ou ASC pour connaître l'ordre de rentrée des paramètres
        if(params.containsKey("asc") || params.containsKey("desc")){
            String firstParam = params.keySet().stream().findFirst().get();
            return productDao.listAll(asc , desc, firstParam);
        }
        //Si on a des paramètres, mais ceux ne sont pas ASC et DESC
        if (!params.isEmpty() && !params.containsKey("asc") && !params.containsKey("desc")){
            return productDao.listFiltered(params);

        }
        productDao.isColumnValid("coucou");
        return productDao.listAll(asc , desc, null);

    }

    /**
     * Ajoute un produit à la base de donnée
     * @param product Le produit récupéré du formulaire
     * @return Une réponse JSON contenant un message de validation ou d'erreur
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addProduct(@RequestBody Product product){
        return productDao.addProduct(product);
    }

    /**
     * Modifie un produit de la base de donnée
     * @param product Le produit récupéré du formulaire
     * @return Une réponse JSON contenant un message de validation ou d'erreur
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateProduct(@PathVariable(value="id") int id, @RequestBody Product product){
        return productDao.updateProduct(product, id);
        //return productDao.updateProduct(product);
    }

    /**
     * Récupère un produit spécifique de la base de donnée
     * @param id L'id du produit désiré
     * @return Une réponse JSON contenant le produit voulue
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable(value="id") int id){
        Product product =  productDao.getProductById(id);
        return product;
    }

    /**
     * Supprime un produit de la base de donnée
     * @param id L'id du produit à supprimer
     * @return Une réponse JSON contenant un message de validation ou d'erreur
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteById(@PathVariable(value="id") int id){
        String validation = productDao.deleteProductById(id);
        return validation;
    }

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> getItemsWithRange(@RequestParam("range") String itemid){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Range",itemid);
        responseHeaders.set("Accept-Range","product");
        return  ResponseEntity.ok()
                .headers(responseHeaders)
                .body(productDao.count(itemid));
        
    }


}
