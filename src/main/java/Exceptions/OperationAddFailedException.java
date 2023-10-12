/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Acer
 */
public class OperationAddFailedException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public OperationAddFailedException() {
        super("Thêm thất bại");
        queryString = "OAFE";
    }

    public OperationAddFailedException(String message) {
        super(message);
        queryString = "OAFE";
    }
}
