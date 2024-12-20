package at.zims2k.tenouthundretpuzzle;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Main {
    private static final String LET_IT_FAIL_ARG = "letitfail";

    public static void main(String[] args) {
        log.debug("Program arguments: {}", Arrays.toString(args));

        final int numberOfHeadCoins;
        final int numberOfTotalCoins;

        boolean letItFail = false;

        if (args.length == 0) {
            numberOfHeadCoins = 1;
            numberOfTotalCoins = 1;
        }
        else if (args.length >= 2){
            numberOfHeadCoins = Integer.parseInt(args[0]);
            numberOfTotalCoins = Integer.parseInt(args[1]);

            if (args.length == 3 && args[2].equals(LET_IT_FAIL_ARG)) {
                log.info("Letting it fail, because arg 3 is \"" + LET_IT_FAIL_ARG + "\"");

                letItFail = true;
            }
            else{
                log.info("Don't letting it fail, because arg 3 is not \"" + LET_IT_FAIL_ARG + "\"");
            }
        }
        else{
            throw new IllegalArgumentException("Number of arguments mismatch!!");
        }

        // letItFail=true: set invalid condition for puzzle: number of head coins DOES NOT match the size of the first group ANYMORE!!!!
        checkIntArgs(letItFail ? numberOfHeadCoins-1 : numberOfHeadCoins, numberOfTotalCoins);

        // letItFail=true: set invalid condition for puzzle: number of head coins DOES NOT match the size of the first group ANYMORE!!!!
        giveItATryWithHeadsAndNumbers(numberOfHeadCoins, numberOfTotalCoins-numberOfHeadCoins, letItFail);
    }

    private static void checkIntArgs(int numberOfHeadCoins, int numberOfTotalCoins) {
        if (numberOfHeadCoins < 1 || numberOfTotalCoins < 1){
            throw new IllegalArgumentException("Number of head coins or number of total coins must be a positive integer value");
        }

        if (numberOfHeadCoins > numberOfTotalCoins){
            throw new IllegalArgumentException("Number of head coins exceeds the total number of coins");
        }
    }

    private static void giveItATryWithHeadsAndNumbers(int numberOfHeadCoins, int numberOfNumberCoins, boolean letItFail) {
        List<Coin> randomCoins = Util.createCoinsWithXHeadsAndYNumbers(numberOfHeadCoins, numberOfNumberCoins);

        log.trace("ALL COINS BEFORE RANDOMIZING:");
        printRandomCoins(randomCoins);

        Collections.shuffle(randomCoins);

        log.trace("ALL COINS AFTER RANDOMIZING AND BEFORE FLIPPING:");
        printRandomCoins(randomCoins);

        // crucial to solve the puzzle!! number of head coins must match the size of the first group!!!!
        List<Coin> group_A = randomCoins.subList(0, letItFail ? numberOfHeadCoins - 1 : numberOfHeadCoins);
        List<Coin> group_B = randomCoins.subList(numberOfHeadCoins, randomCoins.size());

        log.debug("Flipping coins:");
        Util.flip(group_A);

        log.trace("ALL COINS AFTER FLIPPING:");
        printRandomCoins(randomCoins);

        log.trace("ALL COINS AFTER FLIPPING (DERANDOMIZED / natural order):");
        printRandomCoinsWithDerandomize(randomCoins);

        print(group_A, group_B);

        checkAndPrintFulfillmentOfPuzzle(group_A, group_B);
    }

    private static void printRandomCoins(List<Coin> randomCoins) {
        printRandomCoins(randomCoins, false);
    }

    private static void printRandomCoinsWithDerandomize(List<Coin> randomCoins) {
        printRandomCoins(randomCoins, true);
    }

    private static void printRandomCoins(final List<Coin> randomCoins, boolean derandomize) {
        int coinsPerRow = 10;
        StringBuilder rowBuilder = new StringBuilder();
        int fixedWidth = CoinState.getMaxCharactersPerEnumName();

        List<Coin> randomOrDeranzomizedCoins;

        if (derandomize) {
            randomOrDeranzomizedCoins = randomCoins.stream().sorted(Comparator.comparingInt(Coin::getCoinNumber)).toList();
        }
        else{
            randomOrDeranzomizedCoins = randomCoins;
        }

        for (int i = 0; i < randomOrDeranzomizedCoins.size(); i++) {
            String formattedCoin = String.format("  %10d:%-" + fixedWidth + "s", randomOrDeranzomizedCoins.get(i).getCoinNumber(), randomOrDeranzomizedCoins.get(i).getState().getStateString());
            rowBuilder.append(formattedCoin);
            // Add a new line to the StringBuilder after every 'coinsPerRow' coins
            if ((i + 1) % coinsPerRow == 0) {
                log.trace(rowBuilder.toString());
                rowBuilder.setLength(0); // Clear the StringBuilder for the next row
            }
        }

        // Log the remaining coins if the total number of coins is not a multiple of coinsPerRow
        if (randomOrDeranzomizedCoins.size() % coinsPerRow != 0) {
            log.trace(rowBuilder.toString());
        }
    }

    private static void checkAndPrintFulfillmentOfPuzzle(List<Coin> firstTen, List<Coin> others) {
        long headsInFirstTen = Util.countHead(firstTen);
        long headsInOthers = Util.countHead(others);
        if (headsInFirstTen == headsInOthers) {
            log.info("✔ Puzzle solved, both groups contain the same amount of HEADs after flipping");
        } else {
            log.error("❌ Puzzle NOT solved, both groups do NOT contain the same amount of HEADs after flipping");
        }
    }

    private static void print(List<Coin> group_A, List<Coin> others) {
        int fixedWidth = CoinState.getMaxCharactersPerEnumName();
        String formattedHead = String.format("%-" + fixedWidth + "s", CoinState.HEAD.getStateString());
        String formattedNumber = String.format("%-" + fixedWidth + "s", CoinState.NUMBER.getStateString());

        log.info("{} (first {}): {}", group_A.size(), formattedHead, Util.countHead(group_A));
        log.info("{} (first {}): {}", group_A.size(), formattedNumber, Util.countNumber(group_A));
        log.info("{} (others): {}", formattedHead, Util.countHead(others));
        log.info("{} (others): {}", formattedNumber, Util.countNumber(others));
        log.info("----------");
    }

}
