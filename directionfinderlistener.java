package mapparts;

import java.util.List;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import mapparts.Route;

public interface directionfinderlistener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
