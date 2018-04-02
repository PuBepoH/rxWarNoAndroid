package model;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import javafx.util.Pair;
import presenter.FieldState;
import presenter.PlayerPanelState;

import java.util.Random;

public class TestModel implements ModelFacade{
    //IN
    private PublishSubject<ModelCommand> inCommand = PublishSubject.create();
    //OUT
    private BehaviorSubject<Integer> outTimerState = BehaviorSubject.create();
    private BehaviorSubject<FieldState> outFieldState = BehaviorSubject.create();
    private BehaviorSubject<PlayerPanelState> outPlayerPanelState = BehaviorSubject.create();
    private PublishSubject<WinEvent> outWinEvent = PublishSubject.create();
    //INTERNAL
    private PublishSubject<ModelCommandNew> modelCommandNew = PublishSubject.create();
    private PublishSubject<ModelCommandSet> modelCommandSet = PublishSubject.create();
    private PublishSubject<ModelCommandPauseResume> modelCommandPauseResume = PublishSubject.create();
    private PublishSubject<ModelCommandTurn> modelCommandTurn = PublishSubject.create();

    private BehaviorSubject<Integer> timerState = BehaviorSubject.create();
    private BehaviorSubject<FieldState> fieldState = BehaviorSubject.create();
    private BehaviorSubject<PlayerPanelState> playerPanelState = BehaviorSubject.create();
    private PublishSubject<WinEvent> winEvent = PublishSubject.create();

    private BehaviorSubject<Integer> turnOfPlayer = BehaviorSubject.create();
    private BehaviorSubject<boolean[][]> blockedColors = BehaviorSubject.create();
    private PublishSubject<Integer> timerUpdater = PublishSubject.create();
    private Observable clock;

    //NON-streams
    private byte X=10;
    private byte Y=10;
    private int numberOfPlayers;
    private int numberOfColors;
    //SUBSCRIPTIONS
    private CompositeDisposable commandSubscription = new CompositeDisposable();
    private CompositeDisposable internalSubscriptions = new CompositeDisposable();
    private Disposable clockSubscription;

    public TestModel(){
        createLogic();
        //defaults
        timerState.onNext(0);
    }

    @Override
    public void setCommand(PublishSubject<ModelCommand> command) {
        commandSubscription.dispose();
        commandSubscription = new CompositeDisposable();
        commandSubscription.add(
            command.subscribe(this.inCommand::onNext)
        );
    }

    private void createLogic(){
        //Parse inCommand in different streams
        //TODO: FILTERS!!!!
        internalSubscriptions.addAll(
            inCommand
                .filter(o->o instanceof ModelCommandNew)
                .subscribe(o->modelCommandNew.onNext((ModelCommandNew)o))
            , inCommand
                .filter(o->o instanceof ModelCommandSet)
                .subscribe(o->modelCommandSet.onNext((ModelCommandSet) o))
            , inCommand
                .filter(o->o instanceof ModelCommandPauseResume)
                .subscribe(o->modelCommandPauseResume.onNext((ModelCommandPauseResume)o))
            , inCommand
                .filter(o->o instanceof ModelCommandTurn)
                .subscribe(o->modelCommandTurn.onNext((ModelCommandTurn) o))
        );

        //Bind outs
        internalSubscriptions.addAll(
            fieldState.subscribe(outFieldState::onNext)
            , timerState.subscribe(outTimerState::onNext)
            , playerPanelState.subscribe(outPlayerPanelState::onNext)
            , winEvent.subscribe(outWinEvent::onNext)
        );

        //Execute commands
        //NEW
        internalSubscriptions.add(
            modelCommandNew.subscribe(o->{
                this.numberOfPlayers=o.getNumberOfPlayers();
                this.numberOfColors=o.getNumberOfColors();
                fieldState.onNext(createNewField(o));
                turnOfPlayer.onNext(1);
            })
        );
        //SET
        //TURN
        //PAUSE

        //update states
        //player panels
        internalSubscriptions.add(
            Observable
                .combineLatest(turnOfPlayer,fieldState, Pair::new)
                .map(o-> new PlayerPanelState(numberOfColors,numberOfPlayers, blockedColors.getValue() ,1 ))
                .subscribe(playerPanelState::onNext)
        );
        //blockedColors
        internalSubscriptions.add(
            fieldState
                .subscribe(o->blockedColors.onNext(defineBlockedColors(o)))
        );

    }

    private boolean[][] defineBlockedColors(FieldState fs){
        boolean[][] bc = new boolean[numberOfPlayers][numberOfColors];
        if (numberOfPlayers==1) {
            for (int i=0;i<numberOfColors;i++){
                bc[0][i]=(i+1!=fs.getField()[0][0]);
            }
        }
        return bc;
    }

    private FieldState createNewField(ModelCommandNew command){
        //TODO: createNewField
        Random random = new Random();
        int[][] field = new int[X][Y];
        for (int i = 0;i<X;i++){
            for (int j = 0;j<Y;j++){
                field[i][j]= -(random.nextInt(command.getNumberOfColors())+1);
            }
        }
        return new FieldState(X,Y,field);
    }
/*
    private void a(){
        PublishSubject<Pair<Integer,Integer>> ps1= PublishSubject.create();

        Observable<Pair<Integer,Integer>> obs = Observable.create(s->{
            ps1.onNext();
        });

        ps1.distinct().subscribe(o->{
            ps1.onNext();
        })
    }
*/

    @Override
    public BehaviorSubject<Integer> getTimerState() {
        return outTimerState;
    }

    @Override
    public BehaviorSubject<FieldState> getFieldState() {
        return outFieldState;
    }

    @Override
    public BehaviorSubject<PlayerPanelState> getPlayerPanelState() {
        return outPlayerPanelState;
    }

    @Override
    public PublishSubject<WinEvent> getWinEvent() {
        return outWinEvent;
    }

    @Override
    protected void finalize() throws Throwable {
        commandSubscription.dispose();
        commandSubscription.clear();
        internalSubscriptions.dispose();
        internalSubscriptions.clear();
        if (clockSubscription!=null) clockSubscription.dispose();
        super.finalize();
    }
}
