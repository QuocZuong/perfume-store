/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.Brand;
import java.util.ArrayList;

/**
 *
 * @author Acer
 */
public interface IBrandDAO {

    public int addBrand(Brand brand);

    public ArrayList<Brand> getAll();

    public ArrayList<Brand> getBrandNameByAlphabet(char a);

    public Brand getBrand(int brandID);

    public Brand getBrand(String BrandName);

    public int updateBrand(Brand brand);

}
