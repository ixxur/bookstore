package master.project.bookstore.dto;

public class CartItemDto {
    private String title;
    private int quantity;

    // Default constructor
    public CartItemDto() {
    }

    // Constructor with parameters
    public CartItemDto(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for quantity
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
