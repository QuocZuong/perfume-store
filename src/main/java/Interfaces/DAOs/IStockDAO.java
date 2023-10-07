package Interfaces.DAOs;

import Models.Stock;

public interface IStockDAO {

    public int addStock(Stock stock);

    public Stock getStock(int productID);

    public int updateStock(Stock stock);
}
