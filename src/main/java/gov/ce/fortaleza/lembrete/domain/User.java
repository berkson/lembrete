package gov.ce.fortaleza.lembrete.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.Collator;
import java.util.*;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 18:52
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"cpf_num", "email"}))
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseClass implements UserDetails, Comparable<User> {

    private static final long serialVersionUID = 3073479590052563813L;
    @Column(name = "cpf_num", unique = true, nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(name = "recovery_cod")
    private String recoveryCode;
    @Column(columnDefinition = "boolean NOT NULL DEFAULT true")
    @Getter(AccessLevel.NONE)
    private boolean enabled;
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "permissions", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_cod", referencedColumnName = "authority_cod"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority_cod"})})
    private List<Authority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.cpf;
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
        return this.enabled;
    }

    @Override
    public int compareTo(User user) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        collator.setStrength(Collator.PRIMARY);
        return collator.compare(this.name, user.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId())
                && Objects.equals(cpf, user.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), cpf);
    }
}
