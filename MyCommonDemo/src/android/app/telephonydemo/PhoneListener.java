package android.app.telephonydemo;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

import wd.android.util.util.MyLog;

public class PhoneListener extends PhoneStateListener {
    private IMessageControl iMessageControl = null;

    /**
     * @param iMessageControl
     */
    public PhoneListener(Context context, IMessageControl iMessageControl) {
        this.iMessageControl = iMessageControl;
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        MyLog.i("serviceState = " + serviceState.getState());
        super.onServiceStateChanged(serviceState);
    }

    @Override
    public void onSignalStrengthChanged(int asu) {
        super.onSignalStrengthChanged(asu);
    }

    @Override
    public void onMessageWaitingIndicatorChanged(boolean mwi) {
        super.onMessageWaitingIndicatorChanged(mwi);
    }

    @Override
    public void onCallForwardingIndicatorChanged(boolean cfi) {
        super.onCallForwardingIndicatorChanged(cfi);
    }

    @Override
    public void onCellLocationChanged(CellLocation location) {
        super.onCellLocationChanged(location);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
    }

    @Override
    public void onDataConnectionStateChanged(int state) {
        super.onDataConnectionStateChanged(state);
    }

    @Override
    public void onDataConnectionStateChanged(int state, int networkType) {
        MyLog.i("state = " + state + ",networkType = " + networkType);
        super.onDataConnectionStateChanged(state, networkType);
        iMessageControl.sendMessage(
                IMessageControl.ON_DATA_CONNECTION_STATE_CHANGED, state,
                networkType);
    }

    @Override
    public void onDataActivity(int direction) {
        super.onDataActivity(direction);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
    }
}