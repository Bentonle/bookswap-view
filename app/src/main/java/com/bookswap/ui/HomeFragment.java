package com.bookswap.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bookswap.R;
import com.bookswap.ui.profile.ProfileFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    int [] IMAGES = {R.drawable.ic_text_book, R.drawable.ic_text_book};
    String[] TITLE = {"temp title 1", "Temp Title 2"};
    String [] PRICE = {"$15", "$25"};

    View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //this list view will load a lost of all of the users current open trades
        ListView listView = (ListView) view.findViewById(R.id.profile_list_view);

        HomeAdaptor homeAdaptor = new HomeAdaptor();
        listView.setAdapter(homeAdaptor);

        return view;
    }

    //this function wil will load the list with the items
    class HomeAdaptor extends BaseAdapter {

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

}
