package Models;

public class Product {
    private int ID;
    private String name;
    private int BrandID;
    private int price;
    private String gender;
    private String smell;
    private int quantity;
    private int releaseYear;
    private int volume;
    private String ImgURL;
    private String description;

    public Product() {
    }

    public Product(String name, int brandID, int price, String gender, String smell, int quantity,
            int releaseYear, int volume, String imgURL, String description) {
        this.name = name;
        BrandID = brandID;
        this.price = price;
        this.gender = gender;
        this.smell = smell;
        this.quantity = quantity;
        this.releaseYear = releaseYear;
        this.volume = volume;
        ImgURL = imgURL;
        this.description = description;
    }

    public Product(int iD, String name, int brandID, int price, String gender, String smell, int quantity,
            int releaseYear, int volume, String imgURL, String description) {
        ID = iD;
        this.name = name;
        BrandID = brandID;
        this.price = price;
        this.gender = gender;
        this.smell = smell;
        this.quantity = quantity;
        this.releaseYear = releaseYear;
        this.volume = volume;
        ImgURL = imgURL;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandID() {
        return BrandID;
    }

    public void setBrandID(int brandID) {
        BrandID = brandID;
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

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String imgURL) {
        ImgURL = imgURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}