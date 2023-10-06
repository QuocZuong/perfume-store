package Models;

public class Brand {
    private int Id;
    private String name;
    private String logo;
    private String imgURL;
    private int totalProduct;

    public Brand() {
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
