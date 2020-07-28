package com.itrack.Utility;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

import com.itrack.itracksales.TruckApplication;

public class 	NetworkStrengthListener extends PhoneStateListener {
	int mSignalStrength = 0;

	@Override
	public void onSignalStrengthsChanged(SignalStrength signalStrength) {
		super.onSignalStrengthsChanged(signalStrength);
		// mSignalStrength = signalStrength.getGsmSignalStrength();
		// Log.d("sushil", "Network strength is" + mSignalStrength);

		String bin;
		if (!signalStrength.isGsm()) {
			int dBm = signalStrength.getCdmaDbm();
			if (dBm >= -75)
				bin = "GREAT ";
			else if (dBm >= -85)
				bin = "GOOD";
			else if (dBm >= -95)
				bin = "MODERATE";
			else if (dBm >= -100)
				bin = "POOR";
			else
				bin = "NONE_OR_UNKNOWN";
			bin += "from CDMA device";
		} else {
			int asu = signalStrength.getGsmSignalStrength();
			if (asu < 0 || asu >= 99)
				bin = "NONE_OR_UNKNOWN";
			else if (asu >= 16)
				bin = "GREAT";
			else if (asu >= 8)
				bin = "GOOD";
			else if (asu >= 4)
				bin = "MODERATE";
			else
				bin = "POOR";
		}

		TruckApplication.SignalStrength = bin;
		mSignalStrength = (2 * mSignalStrength) - 113;
		// Log.d("sushil", "Network strength is" + mSignalStrength);
	}
}
