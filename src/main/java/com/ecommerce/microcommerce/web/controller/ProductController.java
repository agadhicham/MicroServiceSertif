package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.OrderColumn;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Api( description="API pour es opérations CRUD sur les produits.")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;
    private int resultat;


    //Récupérer la liste des produits

    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {

    	
        Iterable<Product> produits = productDao.findAll();
        
        produits.forEach(pr->{
        	System.out.println(pr.toString());
        	//System.out.println(calculerMargeProduit(pr.getId(), pr));
        	
        });

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")

    public Product afficherUnProduit(@PathVariable int id) {

        Product produit = productDao.findById(id);

        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        return produit;
    }




    //ajouter un produit
    @PostMapping(value = "/Produits")

    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.delete(id);
    }

    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(400);
    }
	@GetMapping(value = "/AdminProduits")
    public List<String> calculerMargeProduit()  {    	
 
    	List<String> ListMargesProduits = new ArrayList<String>();
    	String temps ="";
    	
    	for(Product produits : productDao.findAll() ) {
    		
    		temps = produits.toString() +":"+ (produits.getPrix()-produits.getPrixAchat());  
    		ListMargesProduits.add(temps);
    	}
    	
    	return  ListMargesProduits; 	
    }
	
    //method qui retourn la liste des produits par order croissant des noms
@GetMapping(value="/prodtrier")
public List<Product> trierProduitsParOrdreAlphabetique(){
	   return productDao.findAllByOrderByNom();
}


    

}
