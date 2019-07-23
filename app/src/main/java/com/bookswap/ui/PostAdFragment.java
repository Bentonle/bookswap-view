package com.bookswap.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bookswap.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostAdFragment extends Fragment {

    public static final int ISBN_DATA = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    private EditText editTitle, editEdition, editRelease, editPrice, editGenre;

    public PostAdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_ad, container, false);

        editTitle = (EditText) view.findViewById(R.id.book_title);
        editEdition = (EditText) view.findViewById(R.id.book_edition);
        editRelease = (EditText) view.findViewById(R.id.book_release);
        editPrice = (EditText) view.findViewById(R.id.book_price);
        editGenre = (EditText) view.findViewById(R.id.book_sujectOrGenre);

        //button control for adding images of product
        final ImageButton addImageButton = view.findViewById(R.id.isbn_camera_button);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //button control for the isbn camera
        final ImageButton isbnButton = view.findViewById(R.id.add_image_button);
        isbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(cameraIntent, ISBN_DATA);
            }
        });

             /*Resources r = GetResources();
        Drawable[] layers = new Drawable[2];
        layers[0] = r.getDrawable(R.mipmap.ic_launcher_round);
        layers[1] = r.getDrawable(R.mipmap.ic_launcher_round);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        testImage.setImageDrawable(layerDrawable); */

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //TODO: FIX ACTION BAR SO PROFILE EDIT ICON WILL REPLACE SEARCH
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_blank, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }


    //handle image capture for product
    public void dispatchTakePictureIntent() {
       Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //editEdition.setText(Integer.toString(ISBN_DATA));
        //editPrice.setText(Integer.toString(requestCode));

        /*if(requestCode == ISBN_DATA)
            editPrice.setText("WORKS");
        */
        switch (requestCode) {
            case ISBN_DATA : {
                if (resultCode == 1) {
                    editTitle.setText("HELLO");
                    String title = data.getStringExtra("title"), publisher = data.getStringExtra("publisher");
                    // editTitle.setText(title);
                    editGenre.setText(publisher);
                }
                break;
            }
            case REQUEST_IMAGE_CAPTURE:
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                break;

        }
    }
}

