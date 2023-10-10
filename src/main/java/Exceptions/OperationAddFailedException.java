/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 *
 * @author Acer
 */
public class OperationAddFailedException extends Exception {

    public OperationAddFailedException() {
        super("Add failed.");
    }

    public OperationAddFailedException(String message) {
        super(message);
    }
}
