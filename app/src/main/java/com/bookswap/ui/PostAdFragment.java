package com.bookswap.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bookswap.R;
import com.bookswap.api.config.APIClient;
import com.bookswap.model.StdResponse;
import com.bookswap.ui.user.CreateAccountActivity;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostAdFragment extends Fragment {

    public static final int ISBN_DATA = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Button btnPost;

    private EditText editTitle, editEdition, editRelease, editPrice, editGenre, editDescription;

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
        editDescription = (EditText) view.findViewById(R.id.book_description);

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
                startActivityForResult(cameraIntent, ISBN_DATA);
            }
        });

        btnPost = (Button) view.findViewById(R.id.post_ad_button);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAd(
                        editTitle.getText().toString(),
                        editEdition.getText().toString(),
                        editRelease.getText().toString(),
                        editPrice.getText().toString(),
                        editGenre.getText().toString(),
                        editDescription.getText().toString());
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

    // ----

    private void createNewAd(String title_, String edition_, String release_, String price_, String genre_, String description_){

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyX2RkIiwiZXhwIjoxNTY0NjIxODIxfQ.rtT0jB0Mt_XbOA6IjZNHXfY0eRsASaZSO-7CJJVs7mOnz_cpAEKn2sR6Y_tzrZ4VPifU6fIThvEzE5dCV2UN_Q";
        String username = "user_dd";
        File file = new File("storage/emulated/0/Download/xyPtn4m_d.jpg");

        String description = "description";
        String author = "author";
        String isbn = "isbn";
        String publisher = "publisher";

        // --- //

        HashMap<String, Object> newAd = new HashMap<>();
        HashMap<String, Object> newProduct = new HashMap<>();

        newProduct.put("title",title_);
        newProduct.put("author",author);
        newProduct.put("description", description_);
        newProduct.put("edition",edition_);
        newProduct.put("isbn",isbn);
        newProduct.put("publisher",publisher);

        newAd.put("description",description);
        newAd.put("price",String.valueOf(price_));
        newAd.put("product",newProduct);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        try {
            APIClient api = new APIClient().getInstance();
            Call<HashMap<String, Object>> call = api.getAdService().createNewAd(username,token,newAd,image);

            call.enqueue(new Callback<HashMap<String, Object>>() {
                @Override
                public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response){
                    try{
                        int retStatus = response.code();
                        String responseX ="";
                        responseX = response.errorBody().string();
                        Log.d("TEST1",responseX);
                    }catch (Exception e){
                        Log.e("Booking Presenter", "Exception");
                    }
                }

                @Override
                public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                    Log.d("TEST1",t.toString());
                    //Toast.makeText(CreateAccountActivity.this, "invoking onFailure", Toast.LENGTH_LONG).show();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    // ----

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
                editTitle.setText("HELLO");
                if (resultCode == RESULT_OK) {
                    Bundle isbnExtras = data.getExtras();
                    String title = isbnExtras.getString("title"), publisher = isbnExtras.getString("publisher");
                    editTitle.setText(title);
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

