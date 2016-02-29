package DataManagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cruzj6 on 2/26/2016.
 */
public class DataStorageManager {

    private static DataStorageManager instance;
    private final static String FAVORITES_DATA_FILE = "favorites";
    private Context context;

    public enum UserDataTypes
    {
        FAVORITES_DATA
    }

    public static DataStorageManager getInstance()
    {
        if(instance == null)
            instance = new DataStorageManager();
        return instance;
    }

    public void SetContext(Context cont){
        context = cont;
    }

    public Object LoadUserData(UserDataTypes dataType)
    {
        switch(dataType)
        {
            case FAVORITES_DATA:
                return LoadUserFavoritesData();

            default:
                return null;
        }
    }

    private List<FavoritesDataContainer> LoadUserFavoritesData()
    {
        List<FavoritesDataContainer> favs = new ArrayList<FavoritesDataContainer>();
        try {
            SQLiteDatabase userData = context.openOrCreateDatabase("user_data", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            userData.execSQL("CREATE TABLE IF NOT EXISTS Favorites(Name VARCHAR," +
                    " Type VARCHAR, StationID VARCHAR);");
            Cursor results = userData.rawQuery("Select * from Favorites", null);

            //Go through all of the favorites in the table
            while(results.moveToNext()) {
                //Our base new favorite item
                FavoritesDataContainer newFav = null;

                String FavName = results.getString(0);
                String Type = results.getString(1);
                String StationID = "";

                //If this is a station
                if (Type.equals("Station")) {
                    StationID = results.getString(2);
                    newFav = new StationFavContainer(FavName, Type, StationID);
                }
                //TODO: if (Type.equals("location") etc....

                favs.add(newFav);
                Log.v("DataStorageManager", "Loaded: " + FavName + "|" + Type + "|" + StationID);
            }
            userData.close();
        }
        catch(Exception e)
        {
            Log.e("DataStorageManager", "Error in LoadUserFavoritesData: " +
                    ((e.getMessage() != null)? e.getMessage() : ""));
        }
        return favs;
    }

    public void removeFavorite(String favName)
    {
        SQLiteDatabase userData = context.openOrCreateDatabase("user_data", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        userData.execSQL("CREATE TABLE IF NOT EXISTS Favorites(Name VARCHAR," +
                " Type VARCHAR, StationID VARCHAR);");
        userData.execSQL("DELETE FROM Favorites WHERE Name='" + favName + "'");

        userData.close();
    }

    public boolean checkStationIsInDB(String stationID)
    {
        SQLiteDatabase userData = context.openOrCreateDatabase("user_data", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        userData.execSQL("CREATE TABLE IF NOT EXISTS Favorites(Name VARCHAR," +
                " Type VARCHAR, StationID VARCHAR);");
        Cursor dbCheck = userData.rawQuery("SELECT StationId FROM Favorites", null);

        //Check if the user already has it as a favorite
        while(dbCheck.moveToNext())
        {
            if(dbCheck.getString(0).equals(stationID))
                return true;
        }

        return false;
    }

    public void SaveStationFavorite(String StationId, String stationName)
    {
        SQLiteDatabase userData = context.openOrCreateDatabase("user_data", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        userData.execSQL("CREATE TABLE IF NOT EXISTS Favorites(Name VARCHAR," +
                " Type VARCHAR, StationID VARCHAR);");
        Cursor dbCheck = userData.rawQuery("SELECT StationId FROM Favorites", null);

        boolean isInAlready = checkStationIsInDB(StationId);

        //Add if it's not already there
        if(!isInAlready) {
            userData.execSQL("INSERT INTO Favorites VALUES(\"" + stationName + "\",\"Station\", \"" + StationId + "\");");
            Log.v("DataStorageManager", "Inserted: " + stationName + "|" + StationId);
        }

        userData.close();
    }
}
