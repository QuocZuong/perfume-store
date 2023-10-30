/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces.DAOs;

import Exceptions.OperationAddFailedException;
import Models.Voucher;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface IVoucherDAO {

    public int addVoucher(Voucher v) throws OperationAddFailedException;

    public int addApprovedProduct(Voucher v) throws OperationAddFailedException;

    Voucher getVoucher(int vId);

    List<Voucher> getAllVoucher();

    List<Integer> getAllApprovedProductIdByVoucherId(int vId);

    List<Voucher> getValidVoucherOfProduct(int productId);

    List<Integer> getUsedVoucherOfCustomer(int CustomerId);
}
