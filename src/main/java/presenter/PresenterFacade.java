package presenter;

import io.reactivex.subjects.BehaviorSubject;




public interface PresenterFacade {
    public BehaviorSubject<FragmentName> getFragmentControlState();
    public BehaviorSubject<MenuState> getMenuState();
    public BehaviorSubject<FieldState> getFieldState();
    public BehaviorSubject<Integer> getTimerState();
    public BehaviorSubject<PlayerPanelState> getPlayerPanelState();
}
