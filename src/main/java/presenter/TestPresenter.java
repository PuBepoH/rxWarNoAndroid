package presenter;

import com.sun.org.apache.bcel.internal.generic.NEW;
import data.DataFacade;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import javafx.util.Pair;
import model.*;
import view.*;

import java.util.concurrent.TimeUnit;

public class TestPresenter implements PresenterFacade {
    //OUT Streams
    private BehaviorSubject<FragmentName> fragmentControlState = BehaviorSubject.createDefault(FragmentName.MENU);
    private BehaviorSubject<MenuState> menuState = BehaviorSubject.createDefault(MenuState.UNRESUMABLE);
    private BehaviorSubject<FieldState> fieldState = BehaviorSubject.create();
    private BehaviorSubject<Integer> timerState = BehaviorSubject.create();
    private BehaviorSubject<PlayerPanelState> playerPanelState = BehaviorSubject.create();
    private PublishSubject<ModelCommand> modelCommand = PublishSubject.create();
    //IN Streams
    private PublishSubject<MenuAction> menuActions = PublishSubject.create();
    private PublishSubject<GameAction> gameActions = PublishSubject.create();
    private PublishSubject<NewAction> newActions = PublishSubject.create();
    private PublishSubject<WinEvent> winEvent = PublishSubject.create();
    //Disposable Containers
    private CompositeDisposable internalDisposables = new CompositeDisposable();
    private CompositeDisposable externalDisposables = new CompositeDisposable();
    //Internal state



    public TestPresenter(){

        createLogic();



    }

    public void initialize(ViewFacade viewFacade, ModelFacade modelFacade, DataFacade dataFacade) {
        externalDisposables.dispose();
        externalDisposables = new CompositeDisposable();

        //Streams binding
        modelFacade.setCommand(modelCommand);

        externalDisposables.addAll(
            modelFacade.getFieldState().subscribe(fieldState::onNext)
            , modelFacade.getPlayerPanelState().subscribe(playerPanelState::onNext)
            , modelFacade.getTimerState().subscribe(timerState::onNext)
            , modelFacade.getWinEvent().subscribe(winEvent::onNext)
            , viewFacade.getMenuActions().subscribe(menuActions::onNext)
            , viewFacade.getGameActions().subscribe(gameActions::onNext)
            , viewFacade.getNewActions().subscribe(newActions::onNext)
        );

    }

    private void createLogic(){
        //MENU->NEW
        internalDisposables.add(
            menuActions
                .filter(MenuAction.NEW::equals)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.MENU)
                .map(Pair::getKey)
                .subscribe(o->fragmentControlState.onNext(FragmentName.NEW))
        );
        //MENU->RESUME
        internalDisposables.add(
            menuActions
                .filter(MenuAction.RESUME::equals)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.MENU)
                .map(Pair::getKey)
                .withLatestFrom(menuState, Pair::new)
                .filter(o->o.getValue()==MenuState.RESUMABLE)
                .map(Pair::getKey)
                .subscribe(o->fragmentControlState.onNext(FragmentName.GAME))
        );
        //MENU->EXIT

        //NEW->BACK
        internalDisposables.add(
            newActions
                .filter(o->o.getType()== NewActionType.BACK)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.NEW)
                .map(Pair::getKey)
                .subscribe(o->fragmentControlState.onNext(FragmentName.MENU))
        );
        //NEW->NEW
        internalDisposables.add(
            newActions
                .filter(o->o.getType()== NewActionType.NEW)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.NEW)
                .map(Pair::getKey)
                .subscribe(o->{
                    fragmentControlState.onNext(FragmentName.GAME);
                    modelCommand.onNext(new ModelCommandNew(15,15,o.getPlayers()==2,o.getColors(),10));
                })
        );
        //GAME->BACK
        internalDisposables.add(
            gameActions
                .filter(o->o.getType()== GameActionType.BACK)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.GAME)
                .map(Pair::getKey)
                .subscribe(o->fragmentControlState.onNext(FragmentName.MENU))
        );
        //GAME->TURN
        internalDisposables.add(
            gameActions
                .filter(o->o.getType()== GameActionType.TURN)
                .withLatestFrom(fragmentControlState, Pair::new)
                .filter(o->o.getValue()==FragmentName.GAME)
                .map(Pair::getKey)
                .subscribe(o->modelCommand.onNext(new ModelCommandTurn(o.getPlayer(),o.getColor())))
        );
        //GAME PAUSE/RESUME
        internalDisposables.add(
            fragmentControlState
                .subscribe(o->modelCommand.onNext(new ModelCommandPauseResume(o==FragmentName.GAME)))
        );
        //MENU STATE
        internalDisposables.add(
            fragmentControlState
                .filter(FragmentName.GAME::equals)
                .map(o->(Object)o)
                .mergeWith(winEvent)
                .subscribe(o->{
                    if (o instanceof WinEvent){
                        menuState.onNext(MenuState.UNRESUMABLE);
                    } else {
                        menuState.onNext(MenuState.RESUMABLE);
                    }
                })
        );
    }


    @Override
    public BehaviorSubject<FragmentName> getFragmentControlState() {
        return fragmentControlState;
    }

    @Override
    public BehaviorSubject<MenuState> getMenuState() {
        return menuState;
    }

    @Override
    public BehaviorSubject<FieldState> getFieldState() {
        return fieldState;
    }

    @Override
    public BehaviorSubject<Integer> getTimerState() {
        return timerState;
    }

    @Override
    public BehaviorSubject<PlayerPanelState> getPlayerPanelState() {
        return playerPanelState;
    }

    @Override
    protected void finalize() throws Throwable {
        internalDisposables.dispose();
        internalDisposables.clear();
        externalDisposables.dispose();
        externalDisposables.clear();
        super.finalize();
    }
}
