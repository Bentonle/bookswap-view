package com.bookswap.ui.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bookswap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    int [] IMAGES = {R.drawable.ic_text_book, R.drawable.ic_text_book};
    String[] TITLE = {"temp title 1", "Temp Title 2"};
    String [] PRICE = {"$15", "$25"};

    View view;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //this list view will load a lost of all of the users current open trades
        ListView listView = (ListView) view.findViewById(R.id.profile_list_view);

        ProfileAdaptor profileAdaptor = new ProfileAdaptor();
        listView.setAdapter(profileAdaptor);

        return view;
    }


    //this function wil will load the list with the items
    class ProfileAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            //need to get this data from api
            //return (number of open trades by user.
            return IMAGES.length;
            //return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_layout,null);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.ad_image);
            TextView titleView = (TextView) convertView.findViewById(R.id.ad_title);
            TextView priceView = (TextView) convertView.findViewById(R.id.ad_price);

            //set the data into the views
            //must grab data from api first
            imageView.setImageResource(IMAGES[position]);
            titleView.setText(TITLE[position]);
            priceView.setText(PRICE[position]);

            return convertView;

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO: FIX ACTION BAR SO PROFILE EDIT ICON WILL REPLACE SEARCH
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu,inflater);


    }

}
