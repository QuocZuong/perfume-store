package Interfaces.DAOs;

import Models.Brand;
import java.util.ArrayList;

public interface IBrandDAO {

    public int addBrand(Brand brand);

    public ArrayList<Brand> getAll();

    public ArrayList<Brand> getBrandNameByAlphabet(char a);

    public Brand getBrand(int brandID);

    public Brand getBrand(String BrandName);

    public int updateBrand(Brand brand);

}
