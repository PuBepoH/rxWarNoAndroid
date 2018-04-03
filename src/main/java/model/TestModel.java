package model;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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

    private BehaviorSubject<Boolean> gameActive = BehaviorSubject.create();
    private BehaviorSubject<Integer> turnOfPlayer = BehaviorSubject.create();
    private BehaviorSubject<boolean[][]> blockedColors = BehaviorSubject.create();
    private PublishSubject<Integer> timerUpdater = PublishSubject.create();
    private Observable clock;

    //NON-streams
    private ModelConfiguration modelConfiguration;
    //SUBSCRIPTIONS
    private CompositeDisposable commandSubscription = new CompositeDisposable();
    private CompositeDisposable internalSubscriptions = new CompositeDisposable();
    private Disposable clockSubscription;


    ///////////////////////////////////////////////////////////////////////////////////////////////

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
                this.modelConfiguration=o.getModelConfiguration();
                fieldState.onNext(createNewField(o));
                turnOfPlayer.onNext(0);
            })
        );
        //SET
        //TURN
        internalSubscriptions.add(
            modelCommandTurn
                .subscribe(o->fieldState.onNext(makeTurn(fieldState.getValue(),o)))
        );
        //PAUSE

        //update states
        //player panels
        internalSubscriptions.add(
            Observable
                .combineLatest(turnOfPlayer,blockedColors, Pair::new)
                .map(o-> new PlayerPanelState(modelConfiguration.getNumberOfColors(),modelConfiguration.isTwoPlayers(), o.getValue() ,o.getKey()))
                .subscribe(playerPanelState::onNext)
        );
        //blockedColors
        internalSubscriptions.add(
            fieldState
                .subscribe(o->blockedColors.onNext(defineBlockedColors(o)))
        );

    }

    private FieldState makeTurn(FieldState fs, ModelCommandTurn mct) {
        int X = fs.getXSize();
        int Y = fs.getYSize();
        int[][] field = new int[X][Y];
        boolean[][] visible = new boolean[X][Y];

        for (int i=0;i<X;i++) {
            for (int j=0;j<Y;j++) {
                field[i][j]=fs.getColor(i,j);
                visible[i][j]=fs.isVisible(i,j);
            }
        }

        int aX,aY;

        if (mct.getPlayer()==0) {
            aX=0;
            aY=0;
        } else {
            aX=X-1;
            aY=Y-1;
        }

        allSameColorAs(aX,aY,new FieldState(X,Y,field,visible))
            .subscribe(o->field[o.getKey()][o.getValue()]=mct.getColor());
        System.out.println("-----------------");
        return updateVisible(new FieldState(X,Y,field,visible), modelConfiguration);
    }

    private boolean[][] defineBlockedColors(FieldState fs){
        ModelConfiguration mc = modelConfiguration;

        boolean[][] bc = new boolean[mc.isTwoPlayers()?2:1][mc.getNumberOfColors()];

        for (int i=0;i<mc.getNumberOfColors();i++){
            bc[0][i]=(i+1!=fs.getColor(0,0));
        }
        if (mc.isTwoPlayers()) {
            for (int i=0;i<mc.getNumberOfColors();i++){
                bc[1][i]=(i+1!=fs.getColor(fs.getXSize()-1,fs.getYSize()-1));
            }
        }
        return bc;
    }

    private FieldState createNewField(ModelCommandNew command){
        Random random = new Random();
        int X = command.getXSize();
        int Y = command.getYSize();
        int[][] field = new int[X][Y];
        boolean[][] visible = new boolean[X][Y];
        for (int i = 0;i<X;i++){
            for (int j = 0;j<Y;j++){
                field[i][j]= random.nextInt(command.getModelConfiguration().getNumberOfColors())+1;
            }
        }

        return updateVisible(new FieldState(X,Y,field,visible),command.getModelConfiguration());
    }

    private FieldState updateVisible(FieldState fsIn, ModelConfiguration mc){
        int X = fsIn.getXSize();
        int Y = fsIn.getYSize();
        int[][] field = new int[X][Y];
        boolean[][] visible = new boolean[X][Y];

        for (int i=0;i<X;i++) {
            for (int j=0;j<Y;j++) {
                field[i][j]=fsIn.getColor(i,j);
            }
        }

        FieldState fs = new FieldState(X,Y,field,visible);

        allSameColorAs(0,0, fs)
            .flatMap(o->allNearCells(o.getKey(),o.getValue(),fs))
            .distinct()
            .flatMap(o-> allSameColorAs(o.getKey(),o.getValue(),fs))
            .distinct()
            .subscribe(o->{
                visible[o.getKey()][o.getValue()]=true;
            });

        if(mc.isTwoPlayers()) {
            allSameColorAs(X - 1, Y - 1, fs)
                .flatMap(o -> allNearCells(o.getKey(), o.getValue(), fs))
                .distinct()
                .flatMap(o-> allSameColorAs(o.getKey(),o.getValue(),fs))
                .distinct()
                .subscribe(o -> {
                    visible[o.getKey()][o.getValue()] = true;
                });
        };

        return new FieldState(X,Y,field,visible);
    }

    private Observable<Pair<Integer,Integer>> allNearCells(int x, int y, FieldState fs){
        return Observable.create(s->{
            if ((x>=0)&(y>=0)&(x<fs.getXSize())&(y<fs.getYSize())){
                s.onNext(new Pair<>(x,y));
                if(x-1>=0) s.onNext(new Pair<>(x-1,y));
                if(x+1<fs.getXSize()) s.onNext(new Pair<>(x+1,y));
                if(y-1>=0) s.onNext(new Pair<>(x,y-1));
                if(y+1<fs.getYSize()) s.onNext(new Pair<>(x,y+1));
            }
            s.onComplete();
        });
    }

    private Observable<Pair<Integer,Integer>> allSameColorAs(int x, int y, FieldState fs){
        return Observable.create(s->{

            int startColor=fs.getColor(x,y);
            PublishSubject<Pair<Integer,Integer>> ps = PublishSubject.create();
            Observable<Pair<Integer,Integer>> obs = ps.distinct();
            obs.subscribe(s::onNext);
            obs.subscribe(o->{
                if (fs.getColor(o.getKey(),o.getValue())==startColor){
                    allNearCells(o.getKey(),o.getValue(),fs)
                        .filter(oo->fs.getColor(oo.getKey(),oo.getValue())==startColor)
                        .subscribe(ps::onNext);
                }
            });

            ps.onNext(new Pair<>(x,y));
            ps.onComplete();

            s.onComplete();
        });
    }

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
