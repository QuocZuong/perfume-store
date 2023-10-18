package Interfaces.DAOs;

import Exceptions.ProductNotFoundException;
import Models.Admin;
import Models.Brand;
import Models.Product;
import java.util.List;

public interface IProductDAO {

    public int addProduct(Product pd, Admin admin);

    public List<Product> getAll();

    public Product getProduct(int productId);

    public Product getActiveProduct(int productId);

    public List<Product> getProductByOrderID(int id);

    public List<Product> searchProduct(String search);

    public List<Product> filterProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price);

    public List<Product> filterActiveProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price);

    public List<Product> filterProductByBrand(Brand brand);

    public int updateProduct(Product product, Admin admin);

    public int restoreProduct(Product product, Admin admin) throws ProductNotFoundException;

    public int disableProduct(Product product, Admin admin) throws ProductNotFoundException;
}
