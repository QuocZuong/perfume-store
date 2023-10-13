package Interfaces.DAOs;

import java.util.List;

import Models.DeliveryAddress;

public interface IDeliveryAddressDAO {
    public int addDeliveryAddress(DeliveryAddress deliveryAddress);

    public List<DeliveryAddress> getAll(int customerId);

    public boolean updateDeliveryAddress(DeliveryAddress deliveryAddress) throws NullPointerException;

    public boolean updateDeliveryAddress(List<DeliveryAddress> dList) throws NullPointerException;

    public boolean deleteDeliveryAddress(DeliveryAddress deliveryAddress) throws NullPointerException;

    public boolean deleteDeliveryAddress(List<DeliveryAddress> dList) throws NullPointerException;
}
