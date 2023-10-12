package Exceptions;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Acer
 */
public class AccountNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public AccountNotFoundException() {
        super("Sai tài khoản hoặc mật khẩu");
        queryString = "AccNF";
    }

    public AccountNotFoundException(String message) {
        super(message);
        queryString = "AccNF";
    }
}
