package nl.kolkos.crypto.value.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.kolkos.crypto.value.service.entity.CoinValue;
import nl.kolkos.crypto.value.service.repository.CoinValueRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CoinValueService {
    private final CommandRunner commandRunner;
    private final CoinValueRepository coinValueRepository;
    private final ApplicationSettings applicationSettings;
    private final CoinService coinService;

    public List<CoinValue> getLatestCoinValuesForAllCoins() {
        return coinService.listAll().stream()
                .map(this::getCoinValueForCoin)
                .collect(Collectors.toList());
    }

    public CoinValue getCoinValueForCoin(Coin coin) {
        log.info("Requesting coin value for coin: {}", coin);

        GetCoinValueCommand getCoinValueCommand = createCoinValueCommand(coin);

        String response = runCommand(getCoinValueCommand);

        CoinValueResult coinValueResult = createCoinValueResultFromString(response);

        CoinValue coinValueFromResponse = createCoinValueFromResponse(coinValueResult, coin);
        log.info("CoinValue: {}", coinValueFromResponse);

        return save(coinValueFromResponse);
    }

    public CoinValue save(CoinValue coinValue) {
        return coinValueRepository.save(coinValue);
    }

    public List<CoinValue> listCoinValues() {
        return coinValueRepository.findTop25ByOrderByRequestDateDesc();
    }

    public List<CoinValue> findAllByCoin(Coin coin) {
        return coinValueRepository.findByCoinOrderByRequestDateDesc(coin);
    }


    private GetCoinValueCommand createCoinValueCommand(Coin coin) {
        // create command
        return new GetCoinValueCommand(coin, applicationSettings);
    }

    private CoinValueResult createCoinValueResultFromString(String response) {
        log.info("Parsing CoinValue response:\n\t{}", response);

        Gson gson = new Gson();
        return gson.fromJson(response, CoinValueResult.class);
    }

    private String runCommand(GetCoinValueCommand getCoinValueCommand) {
        // run command
        commandRunner.setCommand(getCoinValueCommand);
        return commandRunner.run();
    }

    private CoinValue createCoinValueFromResponse(CoinValueResult coinValueResult, Coin coin) {
        return CoinValue.builder()
                .requestDate(new Date())
                .high(coinValueResult.getHigh())
                .low(coinValueResult.getLow())
                .last(coinValueResult.getLast())
                .coin(coin)
                .build();
    }


}
