/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.Stock;

/**
 *
 * @author Acer
 */
public interface IStockDAO {

    public int addStock(Stock stock);

    public Stock getStock(int productID);

    public int updateStock(Stock stock);
}
