package com.mbtabuddy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import DataManagement.DataStorageManager;
import DataManagement.FavoritesDataContainer;
import DataManagement.LoadingDialogManager;
import DataManagement.StationFavContainer;
import mbta.mbtabuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoritesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DataStorageManager dataManager;
    private List<FavoritesDataContainer> favs;
    private FavoritesItemsAdapter favAdapter;
    ListView favList;


    private OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Maybe we'll use this later
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Set up our data manager
        dataManager = DataStorageManager.getInstance();
        dataManager.SetContext(getActivity());

        setHasOptionsMenu(true);

        //Get our favorites from the database
        favs = (List<FavoritesDataContainer>)
                dataManager.LoadUserData(DataStorageManager.UserDataTypes.FAVORITES_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        favList =  (ListView) view.findViewById(R.id.favoritesList);

        if(favs.size() > 0) {
            //Finally set up adapter and ListView
            favAdapter = new FavoritesItemsAdapter(getActivity(), android.R.layout.simple_list_item_1, favs);
            favList.setAdapter(favAdapter);

            //Set up on click listeners
            favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FavoritesDataContainer fav = (FavoritesDataContainer) parent.getItemAtPosition(position);
                    if(fav.favType.toLowerCase().equals("station".toLowerCase()))
                    {
                        Intent intent = new Intent(getActivity(), StationActivity.class);
                        intent.putExtra("ID", ((StationFavContainer)fav).StationId);
                        startActivity(intent);
                    }
                }
            });
        }

        //Floating action button for adding a favorite
        FloatingActionButton addFav = (FloatingActionButton) view.findViewById(R.id.addFavButton);
        addFav.setBackgroundTintList(getResources().getColorStateList(R.color.primary_UI_blue));
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialogManager.getInstance().ShowLoading(getContext());
                //Start search activity for adding favorites
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                LoadingDialogManager.getInstance().DismissLoading();
                startActivity(searchIntent);
            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        final MenuItem removeItem = menu.findItem(R.id.action_remove);
        final Button doneEditButton = (Button) getView().findViewById(R.id.done_edit_button);

        //When user clicks done editing
        doneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem.setTitle(getString(R.string.remove_menu_favorites));
                doneEditButton.setVisibility(View.GONE);

                favAdapter.setIsRemoving(false);

                ShowRemoveButtons(false);
            }
        });

        //Remove item mode toggle for removing favorites
        removeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //If no favorites do nothing
                if (favList.getCount() <= 0)
                    return false;

                try {
                    //if there are favorites
                    switch (favList.getChildAt(0).findViewById(R.id.UnfavoriteBtn).getVisibility()) {
                        case View.VISIBLE:
                            //Set our menu button title
                            item.setTitle(getString(R.string.remove_menu_favorites));
                            doneEditButton.setVisibility(View.GONE);

                            favAdapter.setIsRemoving(false);

                            ShowRemoveButtons(false);
                            break;

                        case View.INVISIBLE:
                            //Set title to be "done removing"
                            item.setTitle(getString(R.string.done_remove_menu_favorites));
                            doneEditButton.setVisibility(View.VISIBLE);

                            favAdapter.setIsRemoving(true);

                            ShowRemoveButtons(true);
                            break;

                        default:
                            break;
                    }
                } catch (Exception e) {
                    Log.e("FavoritesFragment", "Error in processing menu Item click: " + e.getMessage());
                }

                return false;
            }
        });

        //Add item menu button
        MenuItem addItem = menu.findItem(R.id.action_add);
        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                LoadingDialogManager.getInstance().ShowLoading(getContext());
                //Start search activity for adding favorites
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                LoadingDialogManager.getInstance().DismissLoading();

                return false;
            }
        });
    }

    private void ShowRemoveButtons(boolean show)
    {
        if(show)
        {
            //Enable the Remove button and the End Remove Button
            for (int i = 0; i < favList.getChildCount(); i++) {
                favList.getChildAt(i).findViewById(R.id.UnfavoriteBtn).setVisibility(View.VISIBLE);
            }
        }
        else
        {
            //Disable the Remove button
            for (int i = 0; i < favList.getChildCount(); i++) {
                favList.getChildAt(i).findViewById(R.id.UnfavoriteBtn).setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
