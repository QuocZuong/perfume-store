/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Models.Voucher;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public interface IVoucherDAO {
    
    int addVoucher(Voucher v);

    int addApprovedProduct(Voucher v);

    Voucher getVoucher(int vId);

    ArrayList<Voucher> getAllVoucher();

    ArrayList<Integer> getAllApprovedProductIdByVoucherId(int vId);
    
    ArrayList<Voucher> getValidVoucherOfProduct(int productId);
    
    ArrayList<Integer>getUsedVoucherOfCustomer(int CustomerId);
}
