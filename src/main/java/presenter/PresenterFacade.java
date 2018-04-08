package presenter;

import io.reactivex.Observable;
import model.WinEvent;


public interface PresenterFacade {
    public Observable<FragmentName> getFragmentControlState();
    public Observable<MenuState> getMenuState();
    public Observable<FieldState> getFieldState();
    public Observable<Integer> getTimerState();
    public Observable<PlayerPanelState> getPlayerPanelState();
    public Observable<WinEvent> getWinState();
}
