package model;

import presenter.FieldState;
import view.GameAction;
import view.NewAction;

public class ModelCommand {
    public ModelCommandType type;
    public FieldState fieldState;
    public NewAction newAction;
    public GameAction gameAction;
    public Integer timerState;

    public static ModelCommand pause(){
        ModelCommand mc = new ModelCommand();
        mc.type=ModelCommandType.PAUSE;
        return mc;
    }

    public static ModelCommand resume(){
        ModelCommand mc = new ModelCommand();
        mc.type=ModelCommandType.RESUME;
        return mc;
    }

    public static ModelCommand set(FieldState fieldState){
        ModelCommand mc = new ModelCommand();
        mc.type=ModelCommandType.SET;
        mc.fieldState = fieldState;
        return mc;
    }

    public static ModelCommand newG(NewAction newAction){
        ModelCommand mc = new ModelCommand();
        mc.type=ModelCommandType.NEW;
        mc.newAction = newAction;
        return mc;
    }

    public static ModelCommand turn(GameAction gameAction){
        ModelCommand mc = new ModelCommand();
        mc.type=ModelCommandType.TURN;
        mc.gameAction = gameAction;
        return mc;
    }

}
