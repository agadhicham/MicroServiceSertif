package com.ecommerce.microcommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MicrocommerceApplication implements CommandLineRunner{

	
	@Autowired
	private ProductController productController;
	
	public static void main(String[] args)  {
		SpringApplication.run(MicrocommerceApplication.class, args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {

		//productController.ajouterProduit(new Product(0, "produitA", 200, 180));
		//productController.ajouterProduit(new Product(0, "produitB", 300, 120));
		//productController.ajouterProduit(new Product(0, "produitC", 400, 150));
//		System.out.println("aaaaaaaaaa");
//		if (this.productController== null) {
//			System.out.println("la liste des produits est vide");
//		}
//		System.out.println(productController.listeProduits().toString());

	}
}
