package Interfaces.DAOs;

import Exceptions.InvalidInputException;
import Exceptions.ProductNotFoundException;
import Models.Admin;
import Models.Brand;
import Models.Product;
import java.util.List;

public interface IProductDAO {

    public int addProduct(Product pd, Admin admin) throws InvalidInputException;

    public List<Product> getAll();

    public Product getProduct(int productId) throws ProductNotFoundException;

    public Product getActiveProduct(int productId) throws ProductNotFoundException;

    public List<Product> searchProduct(String search) throws ProductNotFoundException;

    public List<Product> filterProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price)
            throws ProductNotFoundException;

    public List<Product> filterActiveProduct(
            List<Product> productList,
            int brandId,
            String gender,
            String price)
            throws ProductNotFoundException;

    public List<Product> filterProductByBrand(Brand brand) throws ProductNotFoundException;

    public int updateProduct(Product product, Admin admin) throws InvalidInputException;

    public int restoreProduct(Product product, Admin admin) throws ProductNotFoundException, InvalidInputException;

    public int disableProduct(Product product, Admin admin) throws ProductNotFoundException, InvalidInputException;
}
