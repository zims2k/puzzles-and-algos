package at.zims2k.tenouthundretpuzzle;

import lombok.Getter;

@Getter
public enum CoinState {
    HEAD("😀"), NUMBER("💯");

    private final String stateString;

    CoinState(String stateString){
        this.stateString = stateString;
    }

    public static int getMaxCharactersPerEnumName(){
        int maxChars = 0;

        for (CoinState value : CoinState.values()) {
            maxChars = Math.max(maxChars, value.name().length());
        }

        return maxChars;
    }
}
