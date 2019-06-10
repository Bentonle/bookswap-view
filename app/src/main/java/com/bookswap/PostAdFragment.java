package com.bookswap;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostAdFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    public PostAdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_ad, container, false);

        //button control for adding images of product
        final ImageButton addImageButton = view.findViewById(R.id.add_image_button);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        //button control for the isbn camera
        final ImageButton isbnButton = view.findViewById(R.id.isbn_camera_button);
        isbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(getActivity(), CameraActivity.class);
                startActivity(cameraIntent);
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

    //handle image capture for product
    public void dispatchTakePictureIntent() {
       Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //retrieve image capture from camera
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}

