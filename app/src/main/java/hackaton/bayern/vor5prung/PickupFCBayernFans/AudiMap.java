package hackaton.bayern.vor5prung.PickupFCBayernFans;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hackaton.bayern.vor5prung.Model.FCBayernMunichFan;
import hackaton.bayern.vor5prung.Model.Utils;
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
    double lastLat;
    double lastLon;

    private String mParam1;
    private String mParam2;
    private LatLng initialLocation;
    TextView textViewJourneyDuration;
    Polyline ultimatePolyline;

    public AudiMap() {
        // Required empty public constructor
    }


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
        textViewJourneyDuration = (TextView) view.findViewById(R.id.textview_journey_time);
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

        ArrayList<FCBayernMunichFan> fans = Utils.getBayernMunichFans();

        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.fcbayern);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        for(FCBayernMunichFan fan: fans){
            Marker marker = mMap.addMarker(new MarkerOptions().position(fan.getPosition()).title(fan.getName()));
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            marker.setSnippet(fan.toString());
        }

        ArrayList<LatLng> defaultLocations = new ArrayList<>();
        defaultLocations.add(new LatLng(48.1312525,11.570367));
        defaultLocations.add(new LatLng(48.1302956,11.5110017));
        defaultLocations.add(new LatLng(48.1557529,11.5849357));
        initialLocation = defaultLocations.get((new Random()).nextInt(3));

        mMap.addMarker(new MarkerOptions().title("Me").
                position(initialLocation));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(initialLocation)      // Sets the center of the map to Mountain View
                .zoom(14)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        setupInfoWindow();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String routeToAllianz = "https://maps.googleapis.com/maps/api/directions/json?origin="+initialLocation.latitude+","+initialLocation.longitude+"&destination=48.216966,11.625721&key=AIzaSyAnaWHcjZrjkA_OUL0U1np-eGoapUB3HEU";
        //String routeToAllianz = "https://maps.googleapis.com/maps/api/directions/json?origin=Toronto&destination=Montreal&key=AIzaSyAnaWHcjZrjkA_OUL0U1np-eGoapUB3HEU";
        Log.d("routee", routeToAllianz);

        StringRequest request = new StringRequest(Request.Method.GET, routeToAllianz, this, this);
        requestQueue.add(request);
    }


    private void setupInfoWindow() {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.marker_layout, null);
                String info[] = marker.getSnippet().split("#");
                Log.d("URL", info[2]);
                TextView textViewName = (TextView) view.findViewById(R.id.textview_username_marker);
                ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageview_userface_marker);
                Glide.with(getContext())
                        .load(info[2])
                        .into(imageViewPhoto);

                textViewName.setText(info[0]);

                String urlRoute = "https://maps.googleapis.com/maps/api/directions/json?origin="+initialLocation.latitude+","+initialLocation.longitude+"&destination=48.216966,11.625721&key=AIzaSyAnaWHcjZrjkA_OUL0U1np-eGoapUB3HEU";
                urlRoute  += "&waypoints="+info[3];

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                StringRequest request = new StringRequest(Request.Method.GET, urlRoute, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Tranform the string into a json object
                            final JSONObject json = new JSONObject(response);
                            JSONArray routeArray = json.getJSONArray("routes");
                            JSONObject routes = routeArray.getJSONObject(0);
                            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
                            String encodedString = overviewPolylines.getString("points");
                            JSONArray legs = routes.getJSONArray("legs");
                            int totalDistance = 0;
                            int partialDistance = 0;
                            for(int i=0; i<legs.length(); i++){
                                JSONObject jsonLeg = legs.getJSONObject(i);
                                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                                partialDistance = jsonDuration.getInt("value");
                                totalDistance += partialDistance;
                            }

                            List<LatLng> list = decodePoly(encodedString);

                            PolylineOptions options = new PolylineOptions().width(15).color(Color.RED).geodesic(true);
                            for (int z = 0; z < list.size(); z++) {
                                LatLng point = list.get(z);
                                options.add(point);
                            }
                            if(ultimatePolyline != null)
                                ultimatePolyline.remove();

                            ultimatePolyline = mMap.addPolyline(options);
                            Log.d("tiempo", totalDistance+"");
                            textViewJourneyDuration.setText("Time to Allianz Arena: "+totalDistance/60+"min");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request);

                Log.d("rerute", urlRoute);

                return view;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }

    @Override
    public void onResponse(String response) {
        try {
            // Tranform the string into a json object
            final JSONObject json = new JSONObject(response);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            JSONArray legs = routes.getJSONArray("legs");
            int totalDistance = 0;
            int partialDistance = 0;
            for(int i=0; i<legs.length(); i++){
                JSONObject jsonLeg = legs.getJSONObject(i);
                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                partialDistance = jsonDuration.getInt("value");
                totalDistance += partialDistance;
            }

            List<LatLng> list = decodePoly(encodedString);

            PolylineOptions options = new PolylineOptions().width(15).color(Color.RED).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            if(ultimatePolyline != null) ultimatePolyline.remove();
            ultimatePolyline = mMap.addPolyline(options);
            Log.d("tiempo", totalDistance+"");
            textViewJourneyDuration.setText("Time to Allianz Arena: "+totalDistance/60+"min");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
