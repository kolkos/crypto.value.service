package nl.kolkos.crypto.value.service.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class Coin {
    @Id
    @Column(unique=true)
    @NonNull
    private String symbol;

    @NonNull
    private String name;
}
