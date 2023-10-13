/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.CartItem;
import Models.Product;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface ICartItemDAO {

    int addCartItem(CartItem cartItem);

    int updateCartItem(CartItem cartItem);

    int deleteCartItem(CartItem cartItem);

    int deleteAllCartItemOfCustomer(int customerId);

    int deleteAllDeletedProduct();

    CartItem getCartItem(int customerId, int productId);

    ArrayList<CartItem> getAllCartItemOfCustomer(int customerId);

    ArrayList<Product> getAllOutOfStockProductFromCart(int customerId);
}
