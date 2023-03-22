package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "menu_items_cheques",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "cheques_id"))
    private List<Cheque> cheques = new ArrayList<>();

    @OneToMany(mappedBy = "menuItem", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private Set<SubCategory> subCategories = new LinkedHashSet<>();

    @OneToOne(mappedBy = "menuItem", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true)
    private StopList stopList;
    public void addCheque(Cheque cheque){
        cheques.add(cheque);
    }
    public  void addSubCategory(SubCategory subCategory){
        subCategories.add(subCategory);
    }

}