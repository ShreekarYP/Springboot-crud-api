package com.shree.Ecom_Spring_proj.Service;

import com.shree.Ecom_Spring_proj.Model.Product;
import com.shree.Ecom_Spring_proj.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepo repo;

    public List<Product> getallproduct() {

        return repo.findAll();
    }

    public Product getproduct(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addproduct(Product product, MultipartFile imagefile) throws IOException {

      product.setImagename(imagefile.getOriginalFilename());
      product.setImagetype(imagefile.getContentType());
      product.setImagedata(imagefile.getBytes());
      return repo.save(product);

    }


    public Product updateproduct(int id, Product product, MultipartFile imageFile) throws IOException {

        product.setImagename(imageFile.getOriginalFilename());
        product.setImagetype(imageFile.getContentType());
        product.setImagedata(imageFile.getBytes());

        return repo.save(product);
    }

    public void deleteproduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchproduct(String keyword) {
        return repo.searchproduct(keyword);
    }
}
