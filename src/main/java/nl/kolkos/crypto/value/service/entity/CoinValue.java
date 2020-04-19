package nl.kolkos.crypto.value.service.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
public class CoinValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Date requestDate;

    @NonNull
    private double last;
    private double high;
    private double low;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "coin_symbol")
    private Coin coin;
}
