package hackaton.bayern.vor5prung.PickupFCBayernFans;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import hackaton.bayern.vor5prung.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudiMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudiMap extends Fragment implements OnMapReadyCallback, Response.Listener<String>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GoogleMap mMap;
    private MapView mapView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AudiMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudiMap.
     */
    // TODO: Rename and change types and number of parameters
    public static AudiMap newInstance(String param1, String param2) {
        AudiMap fragment = new AudiMap();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audi_map, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mapa", "mapa listo");



        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String routeToAllianz = "https://maps.googleapis.com/maps/api/directions/json?origin=Boston,MA&destination=Concord,MA&waypoints=Charlestown,MA|Lexington,MA&key=AIzaSyAnaWHcjZrjkA_OUL0U1np-eGoapUB3HEU";

        StringRequest request = new StringRequest(Request.Method.GET, routeToAllianz, this, this);
        requestQueue.add(request);
    }

    @Override
    public void onResponse(String response) {
        Log.d("mappps", response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
