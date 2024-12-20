package at.zims2k.tenouthundretpuzzle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Getter
@Slf4j
public class Coin {
    private int coinNumber;
    private CoinState state;

    public void flipState(){
        switch (state) {
            case HEAD -> {
                state = CoinState.NUMBER;
                debugLogFlipFromTo(coinNumber, CoinState.HEAD, CoinState.NUMBER);
            }
            case NUMBER -> {
                state = CoinState.HEAD;
                debugLogFlipFromTo(coinNumber, CoinState.NUMBER, CoinState.HEAD);
            }
            default -> throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    private void debugLogFlipFromTo(int coinNumber, CoinState stateFrom, CoinState stateTo) {
        String coinNbrStr = String.format("%2d", coinNumber);

        log.debug("  flipped coin {} {}➡{}", coinNbrStr, stateFrom.getStateString(), stateTo.getStateString());
    }
}
