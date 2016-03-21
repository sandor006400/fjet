package com.avv.fjet.core.player;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 27/02/2016
 */
public abstract class Player {

    // region - Constants

    private String DEFAULT_ID = "AAAAA00000";
    private String DEFAULT_NAME = "Unknown";

    // endregion - Constants

    // region - Fields

    private String id;
    private String name;

    // endregion - Fields

    // region - Constructors

    protected Player(){
        this.id = DEFAULT_ID;
        this.name = DEFAULT_NAME;
    }

    protected Player(PlayerState playerState){
        this.id = playerState.playerId;
        this.name = playerState.playerName;
    }

    // endregion - Constructors

    // region - Getters and Setters

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    abstract void beginsTurn();

    protected PlayerState getSavedState() {
        return new PlayerState()
                .setPlayerId(this.id)
                .setPlayerName(this.name);
    }

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    // endregion - Methods

    // region - Inner and Anonymous Classes

    /**
     * Interface used to store Player's data
     */
    protected class PlayerState {

        private String playerId;
        private String playerName;

        public PlayerState setPlayerId(String id){
            this.playerId = id;
            return this;
        }

        public PlayerState setPlayerName(String name){
            this.playerName = name;
            return this;
        }

    }

    // endregion - Inner and Anonymous Classes

}
