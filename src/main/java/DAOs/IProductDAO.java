/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Models.Brand;
import Models.Product;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface IProductDAO {

    public int addProduct(Product pd);

    public List<Product> getAll();

    public Product getProduct(int productId);

    public Product getActiveProduct(int productId);

    public List<Product> getProductByOrderID(int id);

    public List<Product> filterProduct(
            Integer brandId,
            String gender,
            String price,
            int page,
            String search);

    public List<Product> filterActiveProduct(Integer brandId,
            String gender,
            String price,
            int page,
            String search);

    public List<Product> filterProductByBrand(Brand brand);

    public int updateProduct(Product product);

    public int restoreProduct(Product product);

    public int disableProduct(Product product);
}
