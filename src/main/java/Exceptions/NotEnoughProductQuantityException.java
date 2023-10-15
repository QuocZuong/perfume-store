/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Acer
 */
public class NotEnoughProductQuantityException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public NotEnoughProductQuantityException() {
        super("Số lượng sản phẩm trong kho không đủ để thanh toán");
        queryString = "NEPQE";
    }

    public NotEnoughProductQuantityException(String message) {
        super(message);
        queryString = "NEPQE";
    }
}
