package com.example.daolab.items;

import com.example.daolab.R;

public enum Difficulty {
    VERY_EASY(-2), EASY(-1), MEDIUM(0), HARD(1), VERY_HARD(2);

    private final int level;

    Difficulty(int level) {
        this.level = level;
    }

    public static Difficulty getByLevel(int level) {
        switch (level) {
            case -2:
                return Difficulty.VERY_EASY;
            case -1:
                return Difficulty.EASY;
            case 0:
                return Difficulty.MEDIUM;
            case 1:
                return Difficulty.HARD;
            case 2:
                return Difficulty.VERY_HARD;
            default:
                return null;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getNameFromResources() {
        switch (this) {
            case VERY_EASY:
                return R.string.card_difficulty_very_easy;
            case EASY:
                return R.string.card_difficulty_easy;
            case MEDIUM:
                return R.string.card_difficulty_medium;
            case HARD:
                return R.string.card_difficulty_hard;
            case VERY_HARD:
                return R.string.card_difficulty_very_hard;
            default:
                return R.string.card_difficulty_placeholder;
        }
    }
}