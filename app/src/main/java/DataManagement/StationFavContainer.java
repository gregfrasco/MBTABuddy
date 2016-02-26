package DataManagement;

public class StationFavContainer extends FavoritesDataContainer{


    public String StationId;

    StationFavContainer(String name, String type, String stationId) {
        super(name, type);
        StationId = stationId;
    }
}
