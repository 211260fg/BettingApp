package floriangoeteyn.com.bettingapp.repo;

import java.util.List;

import floriangoeteyn.com.bettingapp.model.Bet;

/**
 * Created by floriangoeteyn on 22-Apr-16.
 */
public interface LoadedListener {
    void onBetsLoaded();
    void onLoadFailed();
    void onBetAdded(Bet bet, int pos);
    void onBetRemoved(Bet bet, int pos);
}
