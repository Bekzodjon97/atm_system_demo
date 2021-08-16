package uz.pdp.app_atm_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.app_atm_system.entity.template.CardType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Check(constraints = "length(card_number)=16")
public class Card implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String fullName;


    @Column(nullable = false)
    private String bankType;

    @Column(nullable = false)
    private String CVVcode;

    @Column(nullable = false)
    private Date validityPeriod;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @ManyToMany
    private Set<Role> role;

    private boolean active=true;

    private double balance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getUsername() {
        return this.cardNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

}
