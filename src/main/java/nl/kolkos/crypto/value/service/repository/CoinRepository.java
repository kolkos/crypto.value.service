package nl.kolkos.crypto.value.service.repository;

import nl.kolkos.crypto.value.service.entity.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findBySymbol(String symbol);

}
