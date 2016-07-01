package org.avv.fjet.core;

import android.content.Context;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionExecutor;
import org.avv.fjet.core.action.ShowPathAction;
import org.avv.fjet.core.board.Board;
import org.avv.fjet.core.board.Cell;
import org.avv.fjet.core.player.Player;
import org.avv.fjet.core.unit.Unit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Vladimirovich Vorobiev
 * At 22/03/2016
 */
public class Game {

    // region - Constants

    // endregion - Constants

    // region - Fields

    protected final ActionExecutor executor = new ActionExecutor();
    protected Board board;
    protected List<Player> players = new ArrayList<>();
    protected Map<String, List<String>> playerUnitsMap = new HashMap<>();

    // endregion - Fields

    // region - Constructors

    public Game(Board board){
        this.board = board;
    }

    public Game(String json, Context c){
        initWithJson(json, c);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Board getBoard(){
        return this.board;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    // endregion - Methods for/from SuperClass/Interfaces

    // region - Methods

    public void processAction(Action a){
        this.executor.executeAction(a);
    }

    public void undoLastAction(){
        this.executor.undoLastAction();
    }

    public void redoLastAction(){
        this.executor.redoLastAction();
    }

    /**
     * Add a player to players array and inits a player units array
     * @param player
     */
    public void addPlayer(Player player){
        this.players.add(player);
        this.playerUnitsMap.put(player.getId(), new ArrayList<String>());
    }

    public void addUnit(Unit unit, String playerId){
        List<String> unitIds = this.playerUnitsMap.get(playerId);

        if (unitIds != null){
            unitIds.add(unit.getId());

        } else {
            List<String> unitIdsArray = new ArrayList<>();
            unitIdsArray.add(unit.getId());
            this.playerUnitsMap.put(playerId, unitIdsArray);
        }
        unit.setPlayerId(playerId);
        this.board.getUnits().put(unit.getId(), unit);
    }

    public void removeUnit(String unitId){
        Unit unit = this.board.getUnits().remove(unitId);

        if (unit != null) {
            List<String> unitIds = this.playerUnitsMap.get(unit.getPlayerId());

            if (unitIds != null){
                unitIds.remove(unitId);
            }
        }
    }

    public String toJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("board", this.board.getBoardData().toJson());

            JSONArray jsonArrayPlayers = new JSONArray();
            for (Player player : this.players) {
                jsonArrayPlayers.put(player.toJson());
            }
            jsonObject.put("players", jsonArrayPlayers);

        } catch (JSONException e) {

        }
        return jsonObject.toString();
    }

    public void initWithJson(String json, Context c) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            try {
                this.board = new Board(new Board.BoardData(jsonObject.getString("board")), c);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray playersArray = jsonObject.getJSONArray("players");

                for (int i = 0; i < playersArray.length(); i++) {
                    Player player = new Player(playersArray.getString(i));
                    this.players.add(player);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initializing playerUnits map
            for (Unit unit : this.board.getUnits().values()){
                addUnit(unit, unit.getPlayerId());
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
