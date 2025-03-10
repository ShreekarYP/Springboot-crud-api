package com.shree.Ecom_Spring_proj.Controller;

import com.shree.Ecom_Spring_proj.Model.Product;
import com.shree.Ecom_Spring_proj.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLOutput;
import java.util.List;


//localhost:8080/api/
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
private ProductService service;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getallproduct(){
        List<Product> prod=service.getallproduct();
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getproduct(@PathVariable int id){
        Product prod= service.getproduct(id);
        if(prod!=null)
        return new ResponseEntity<>(prod,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addproduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){
        try {

            Product product1=service.addproduct(product,imageFile);



            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("product/{productid}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productid){

       Product product= service.getproduct(productid);
       byte[] image=product.getImagedata();
       return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImagetype())).body(image);

    }
@PutMapping("/product/{id}")
    public ResponseEntity<String> updateproduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart MultipartFile imageFile){
    Product prod=null;
        try {
            prod =  service.updateproduct(id,product,imageFile);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
            if(prod!=null){

                return new ResponseEntity<>("Update Sucessfull",HttpStatus.OK);

            }
            else{
                return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);

            }
        }

        @DeleteMapping("/product/{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable int id){
            Product product = service.getproduct(id);
            if(product!=null){
                service.deleteproduct(id);
                return new ResponseEntity<>("Deleted Sucessfully",HttpStatus.OK);

    }
            else {
                return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
            }



        }

        @GetMapping("/products/search")
        public ResponseEntity<List<Product>> searchproduct(String keyword){
        List<Product> product= service.searchproduct(keyword);
        return new ResponseEntity<>(product, HttpStatus.OK);
        }






}
