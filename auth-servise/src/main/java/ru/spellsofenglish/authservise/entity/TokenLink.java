package ru.spellsofenglish.authservise.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "token_links")
public class TokenLink {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(name = "token")
    private String token;

    @Column(name = "is_active")
    private boolean isActive;

    @Override
    public String toString() {
        return "TokenLink{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenLink tokenLink = (TokenLink) o;
        return isActive == tokenLink.isActive && Objects.equals(id, tokenLink.id) && Objects.equals(token, tokenLink.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, isActive);
    }
}
