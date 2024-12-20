package at.zims2k.tenouthundretpuzzle;

import java.util.ArrayList;
import java.util.List;

public class Util {
    static List<Coin> createCoinsWithXHeadsAndYNumbers(int numberOfHeadCoins, int numberOfNumberCoins) {
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < numberOfHeadCoins; i++) {
            coins.add(new Coin(i+1, CoinState.HEAD));
        }

        for (int i = numberOfHeadCoins; i < numberOfHeadCoins+numberOfNumberCoins; i++) {
            coins.add(new Coin(i+1, CoinState.NUMBER));
        }

        return coins;
    }

    static long countHead(List<Coin> coins) {
        return getCount(coins, CoinState.HEAD);
    }

    static long countNumber(List<Coin> coins) {
        return getCount(coins, CoinState.NUMBER);
    }

    static long getCount(List<Coin> coins, CoinState state) {
        return coins.stream().filter(coin -> coin.getState() == state).count();
    }

    static void flip(List<Coin> coins) {
        coins.forEach(Coin::flipState);
    }
}