package org.avv.fjet.core;

import org.avv.fjet.core.action.Action;
import org.avv.fjet.core.action.ActionExecutor;
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
public class Game extends GameEntity {

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
        super();
        this.board = board;
    }

    public Game(JSONObject json){
        super();

        initWithJson(json);
    }

    // endregion - Constructors

    // region - Getters and Setters

    public Board getBoard(){
        return this.board;
    }

    // endregion - Getters and Setters

    // region - Methods for/from SuperClass/Interfaces

    @Override
    protected JSONObject initJSONObject() {
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
        return jsonObject;
    }


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

    public List<Player> getPlayers(){
        return this.players;
    }

    public void addUnit(Unit unit, String playerId, Cell cell){

        if (this.board.getCellWithCoords(cell.getCoords()) != null) {
            List<String> unitIds = this.playerUnitsMap.get(playerId);

            if (unitIds != null) {
                unitIds.add(unit.getId());

            } else {
                List<String> unitIdsArray = new ArrayList<>();
                unitIdsArray.add(unit.getId());
                this.playerUnitsMap.put(playerId, unitIdsArray);
            }
            unit.setPlayerId(playerId);
            this.board.addUnit(unit, cell);
        }
    }

    public void removeUnit(String unitId){
        Unit unit = this.board.getUnits().remove(unitId);

        if (unit != null) {
            unit.getCell().removeUnit();
            List<String> unitIds = this.playerUnitsMap.get(unit.getPlayerId());

            if (unitIds != null){
                unitIds.remove(unitId);
            }
        }
    }

    public void removeAllUnits(){
        this.board.removeAllUnits();

        for (Object unitsList : this.playerUnitsMap.values()) {

            if (unitsList != null && unitsList instanceof List){
                ((List)unitsList).clear();
            }
        }
    }

    @Override
    public void initWithJson(JSONObject json) {
        super.initWithJson(json);

        if (json != null) {
            try {
                this.board = new Board(new Board.BoardData(new JSONObject(json.getString("board"))));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray playersArray = json.getJSONArray("players");

                for (int i = 0; i < playersArray.length(); i++) {
                    Player player = new Player(new JSONObject(playersArray.getString(i)));
                    this.players.add(player);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Initializing playerUnits map
            for (Unit unit : this.board.getUnits().values()){
                addUnit(unit, unit.getPlayerId(), unit.getCell());
            }
        }
    }

    // endregion - Methods

    // region - Inner and Anonymous Classes

    // endregion - Inner and Anonymous Classes

}
