package Models;

public class Brand {
    private int ID;
    private String name;
    private String logo;
    private String imgURL;
    private int totalProduct;

    public Brand() {
    }

    public Brand(int iD, String name, String logo, String imgURL, int totalProduct) {
        ID = iD;
        this.name = name;
        this.logo = logo;
        this.imgURL = imgURL;
        this.totalProduct = totalProduct;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }
}
