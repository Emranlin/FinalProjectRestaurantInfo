package peaksoft.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "cheques")

public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "cheques", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<MenuItem> menuItems = new ArrayList<>();
    public void addMenuItem(MenuItem menuItem){
        menuItems.add(menuItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cheque cheque = (Cheque) o;
        return priceAverage == cheque.priceAverage && Objects.equals(id, cheque.id) && Objects.equals(createdAt, cheque.createdAt) && Objects.equals(user, cheque.user) && Objects.equals(menuItems, cheque.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priceAverage, createdAt, user, menuItems);
    }
}