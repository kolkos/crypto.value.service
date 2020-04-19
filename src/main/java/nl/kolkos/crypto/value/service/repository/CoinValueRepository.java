package nl.kolkos.crypto.value.service.repository;

import nl.kolkos.crypto.value.service.entity.Coin;
import nl.kolkos.crypto.value.service.entity.CoinValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoinValueRepository extends JpaRepository<CoinValue, Long> {
    List<CoinValue> findTop25ByOrderByRequestDateDesc();
    List<CoinValue> findByCoinOrderByRequestDateDesc(Coin coin);
}

