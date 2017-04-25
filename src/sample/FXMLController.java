package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;

import java.util.ArrayList;

public class FXMLController implements MapComponentInitializedListener
{

    boolean initialized = false;
    GoogleMapView mapView;
    GoogleMap map;
    ArrayList<Marker> MarkerList = new ArrayList<>();

    Lock lock;

    public FXMLController(Lock lock)
    {
        //Create the JavaFX component and set this as a listener so we know when
        //the map has been initialized, at which point we can then begin manipulating it.
        mapView = new GoogleMapView();
        mapView.addMapInializedListener(this);
        this.lock = lock;

    }

    public GoogleMapView getMapView() {
        return mapView;
    }


    public void mapInitialized() {
        initialized = true;
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(53.090483, 8.830528))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        System.out.println("map Initialized");
        synchronized (lock) {
            lock.notify();
        }


//addMarker("Marker 1", "53.090483,8.830528","Label1");
  //      addMarker("Marker 2", "53.190483,8.830528","Label2");
    //    addMarker("Marker 3", "53.290483,8.830528","Label3");
        //Add a marker to the map
        //MarkerOptions markerOptions = new MarkerOptions();

        //markerOptions.position( new LatLong(47.6, -122.3) )
        //        .visible(Boolean.TRUE)
        //        .title("My Marker");

        //Marker marker = new Marker( markerOptions );

        //map.addMarker(marker);

    }

    public void addMarker(String title,String latlong,String label)
    {
        System.out.println("Marker added");
        MarkerOptions markerOptions = new MarkerOptions();

        double latitude = Double.parseDouble(latlong.split(",")[0]);
        double longitude = Double.parseDouble(latlong.split(",")[1]);

        markerOptions.position(new LatLong(latitude,longitude));
        markerOptions.title(title);
        markerOptions.label(label);
        markerOptions.visible(true);
        markerOptions.icon("http://files.softicons.com/download/web-icons/awt-travel-blue-icons-by-awt-media/png/24x24/AWT-Bus.png");

        Marker marker = new Marker(markerOptions);
        MarkerList.add(marker);
        //map.addMarker(marker);
    }

    public void removeMarker(int index)
    {
        map.removeMarker(MarkerList.get(index));
    }


    public void refresh()
    {

        map.addMarkers(MarkerList);
    }

    public void clear()
    {
        map.clearMarkers();
        MarkerList.clear();
    }
}