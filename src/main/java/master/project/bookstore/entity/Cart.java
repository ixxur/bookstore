package master.project.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
    }

    public Cart(User user, List<CartItem> items) {
        this.user = user;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (sameAsFormer(user)) {
            return;
        }
        User oldUser = this.user;
        this.user = user;
        if (oldUser != null) {
            oldUser.setCart(null);
        }
        if (user != null) {
            user.setCart(this);
        }
    }

    private boolean sameAsFormer(User newUser) {
        return Objects.equals(user, newUser);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
