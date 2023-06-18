package Models;

public class Product {
    private String code;
    private String name;
    private String brandCode;
    private int price;
    private String gender;
    private String smell;
    private int quantity;
    private int release_Year;
    private int volume;
    private String description;

    public Product() {
    }

    public Product(String code, String name, String brandCode, int price, String gender, String smell, int quantity,
            int release_Year, int volume, String description) {
        this.code = code;
        this.name = name;
        this.brandCode = brandCode;
        this.price = price;
        this.gender = gender;
        this.smell = smell;
        this.quantity = quantity;
        this.release_Year = release_Year;
        this.volume = volume;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmell() {
        return smell;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRelease_Year() {
        return release_Year;
    }

    public void setRelease_Year(int release_Year) {
        this.release_Year = release_Year;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}