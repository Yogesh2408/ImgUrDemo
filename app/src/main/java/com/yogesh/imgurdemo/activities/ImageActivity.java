package com.yogesh.imgurdemo.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yogesh.imgurdemo.R;
import com.yogesh.imgurdemo.Utils;
import com.yogesh.imgurdemo.model.Image;
import com.yogesh.imgurdemo.viewmodel.MainActivityViewModel;

public class ImageActivity extends AppCompatActivity {

    /**
     * Key for the Intent to Pass.
     */
    public static String IMAGE_ID = "imageId";
    /**
     * Holds instance of MainActivityViewModel.
     */
    private MainActivityViewModel viewModel;
    /**
     * Holds instance of ImageView.
     */
    private ImageView detailImageView;
    /**
     * Holds instance of EditText.
     */
    private EditText descEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.image_detail));
        }

        Button submitButton = (Button) findViewById(R.id.submit_button);
        descEditText = (EditText) findViewById(R.id.editTextTextDesc);
        detailImageView = (ImageView) findViewById(R.id.detail_imageView);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        if (getIntent().hasExtra(IMAGE_ID)) {
            getImageObservableData();
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!descEditText.getText().toString().trim().isEmpty()) {
                    viewModel.setDescription(viewModel.getImgId().getValue()
                            , descEditText.getText().toString().trim());
                } else {
                    Utils.showToast(ImageActivity.this, getString(R.string.no_description));
                }
            }
        });
    }

    /**
     * Gets Image Live data object from database.
     */
    private void getImageObservableData() {
        viewModel.getImageDetails(getIntent().getStringExtra(IMAGE_ID))
                .observe(this, new Observer<Image>() {
                    @Override
                    public void onChanged(Image image) {

                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(image.getTitle());
                        }
                        Glide.with(ImageActivity.this)
                                .setDefaultRequestOptions(new RequestOptions().fitCenter())
                                .load(Uri.parse(image.getLink()))
                                .into(detailImageView);
                    }
                });
    }

    /**
     * @param id      Image Id.
     * @param context Activity context.
     * @return Intent
     */
    public static Intent getIntentInstance(String id, Context context) {
        Intent imageIntent = new Intent(context, ImageActivity.class);
        imageIntent.putExtra(IMAGE_ID, id);
        return imageIntent;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous activity.
        }
        return super.onOptionsItemSelected(item);
    }
}

