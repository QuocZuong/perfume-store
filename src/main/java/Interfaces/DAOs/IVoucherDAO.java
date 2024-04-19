package Interfaces.DAOs;

import Exceptions.OperationAddFailedException;
import Exceptions.VoucherCodeDuplication;
import Models.Voucher;
import java.util.List;

public interface IVoucherDAO {

    public int addVoucher(Voucher v) throws OperationAddFailedException, VoucherCodeDuplication;

    public int addApprovedProduct(Voucher v) throws OperationAddFailedException;

    Voucher getVoucher(int vId);

    List<Voucher> getAllVoucher();

    List<Integer> getAllApprovedProductIdByVoucherId(int vId);

    List<Voucher> getValidVoucherOfProduct(int productId);

    List<Integer> getUsedVoucherOfCustomer(int CustomerId);
}
