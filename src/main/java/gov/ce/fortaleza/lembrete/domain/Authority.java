package gov.ce.fortaleza.lembrete.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 19:08
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@AttributeOverride(name = "authority", column = @Column(name = "authority_cod"))
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @Getter(AccessLevel.NONE)
    @Column(length = 100)
    private String authority;
    @Column(nullable = false)
    private String description;
    @ManyToMany(mappedBy = "authorities")
    private List<User> users = new ArrayList<>();

    public Authority(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
