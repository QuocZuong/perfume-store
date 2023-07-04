package Models;

public class Cart {
    private int clientID;
    private int productID;
    private int quantity;
    private int price;
    private int sum;

    public Cart() {
    }

    public Cart(int clientID, int productID, int quantity, int price, int sum) {
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.sum = sum;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
