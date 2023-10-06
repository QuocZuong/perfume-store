package Models;


public class Product {
    private int Id;
    private String name;
    private int brandId;
    private String gender;
    private String smell;
    private int releaseYear;
    private int volume;
    private String imgURL;
    private String description;
    private boolean active;

    public Product() {
    }

    public Product(int Id, String name, int brandId, String gender, String smell, int releaseYear, int volume, String imgURL, String description, boolean active) {
        this.Id = Id;
        this.name = name;
        this.brandId = brandId;
        this.gender = gender;
        this.smell = smell;
        this.releaseYear = releaseYear;
        this.volume = volume;
        this.imgURL = imgURL;
        this.description = description;
        this.active = active;
    }

    public Product(int Id, String name, int brandId, String gender, String smell, int volume, String imgURL, String description) {
        this.Id = Id;
        this.name = name;
        this.brandId = brandId;
        this.gender = gender;
        this.smell = smell;
        this.volume = volume;
        this.imgURL = imgURL;
        this.description = description;
        this.active = true;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
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
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }




}