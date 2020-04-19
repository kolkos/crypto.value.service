package nl.kolkos.crypto.value.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.kolkos.crypto.value.service.entity.Coin;
import nl.kolkos.crypto.value.service.repository.CoinRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CoinService {
    private final CoinRepository coinRepository;

    public Coin createCoin(String symbol, String name) {
        Coin coin = Coin.builder()
                .symbol(symbol)
                .name(name)
                .build();
        return this.save(coin);
    }

    public List<Coin> listAll() {
        log.info("Generating coin list");
        return coinRepository.findAll();
    }

    public Coin save(Coin coin) {
        log.info("Saving coin: {}", coin);
        return coinRepository.save(coin);
    }

    public Coin findBySymbol(String symbol) {
        return coinRepository.findBySymbol(symbol)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Cannot find coin with symbol '%s'", symbol)));
    }


}
