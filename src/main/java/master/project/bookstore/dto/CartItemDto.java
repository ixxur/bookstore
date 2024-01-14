package master.project.bookstore.dto;

public class CartItemDto {
    private String title;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "CartItemDto{" +
                "title='" + title + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
