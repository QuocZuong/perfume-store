package Interfaces.DAOs;

import Models.Brand;
import Models.Product;
import java.util.List;

public interface IProductDAO {

    public int addProduct(Product pd);

    public List<Product> getAll();

    public Product getProduct(int productId);

    public Product getActiveProduct(int productId);

    public List<Product> getProductByOrderID(int id);

    public List<Product> searchProduct(String search);

    public List<Product> pagingProduct(List<Product> productList, int page);

    public List<Product> filterProduct(
            List<Product> productList,
            Integer brandId,
            String gender,
            String price);

    public List<Product> filterActiveProduct(
            List<Product> productList,
            Integer brandId,
            String gender,
            String price);

    public List<Product> filterProductByBrand(Brand brand);

    public int updateProduct(Product product);

    public int restoreProduct(Product product);

    public int disableProduct(Product product);
}
