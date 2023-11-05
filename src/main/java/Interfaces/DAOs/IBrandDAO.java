package Interfaces.DAOs;

import Exceptions.BrandNotFoundException;
import Exceptions.OperationEditFailedException;
import Models.Brand;
import java.util.List;

public interface IBrandDAO {

    public int addBrand(Brand brand);

    public List<Brand> getAll();

    public List<Brand> getBrandNameByAlphabet(char a);

    public Brand getBrand(int brandID);

    public Brand getBrand(String BrandName);

    public int updateBrand(Brand brand) throws OperationEditFailedException, BrandNotFoundException;

}
