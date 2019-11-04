package com.example.myapplication.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Utils.Utility;
import com.example.myapplication.model.Model;
import com.example.myapplication.offline.AppDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class CreateProduct extends AppCompatActivity {
    TextView textView;

    String image = "";

    private EditText editTextID, editTextName, editTextDescription,
            editTextRegularPrice, editTextSalePrice, editTextColors, editTextStores, editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product);
        textView = findViewById(R.id.text);

        editTextName = findViewById(R.id.et_name);
        editTextDescription = findViewById(R.id.et_description);
        editTextRegularPrice = findViewById(R.id.et_regular_price);
        editTextSalePrice = findViewById(R.id.et_sale_price);
        editTextColors = findViewById(R.id.et_color);
        editTextStores = findViewById(R.id.et_stores);
        editTextAddress = findViewById(R.id.et_address);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.button_save).setOnClickListener(view -> saveProduct());

        findViewById(R.id.button_select).setOnClickListener(view -> pickFromGallery());


    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 0:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    //Gets the String value in the column
                    image = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    // imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    textView.setText(image);

                    break;

            }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveProduct() {
//        final int ID = Integer.parseInt(editTextID.getText().toString().trim());
        final String DECRIPTION = editTextDescription.getText().toString().trim();
        final String NAME = editTextName.getText().toString().trim();
        final String REGULAR_PRICE = (editTextRegularPrice.getText().toString().trim());
        final String SALE_PRICE = (editTextSalePrice.getText().toString().trim());
        final String COLORS = editTextColors.getText().toString().trim();

        final String STORES = editTextStores.getText().toString().trim();
        final String ADDRESS = editTextAddress.getText().toString().trim();
        final String IMAGE = image;
        Log.e("IMAge", image);


        if (NAME.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (DECRIPTION.isEmpty()) {
            editTextDescription.setError("Description required");
            editTextDescription.requestFocus();
            return;
        }


        if (REGULAR_PRICE.isEmpty()) {
            editTextRegularPrice.setError("Regular Price required");
            editTextRegularPrice.requestFocus();
            return;
        }
        if (SALE_PRICE.isEmpty()) {
            editTextSalePrice.setError("Sale Price required");
            editTextSalePrice.requestFocus();
            return;
        }
        if (IMAGE.isEmpty()) {
            textView.setError("Store required");
            textView.requestFocus();
            return;


        }
        if (COLORS.isEmpty()) {
            editTextColors.setError("Color required");
            editTextColors.requestFocus();
            return;
        }
        if (STORES.isEmpty()) {
            editTextStores.setError("Store required");
            editTextStores.requestFocus();
            return;
        }
        if (ADDRESS.isEmpty()) {
            editTextAddress.setError("Store Address is Required");
            editTextAddress.requestFocus();
            return;
        }


        int sale_price = 0, regular_price = 0;

        try {

            sale_price = Integer.parseInt(SALE_PRICE);
            regular_price = Integer.parseInt(REGULAR_PRICE);


        } catch (Exception e) {

        }
        int finalRegular_price = regular_price;
        int finalSale_price = sale_price;
        class SaveProduct extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Model product = new Model();
                // product.setId(finalId);
                product.setName(NAME);
                product.setDescription(DECRIPTION);
                product.setRegular_price(finalRegular_price);
                product.setSale_price(finalSale_price);

                product.setColors(Utility.getArrayListFromColorString(COLORS));
                product.setStores(Utility.getStoreInformation(STORES, ADDRESS));
                product.setImage(Objects.requireNonNull(getBytesFromBitmap(BitmapFactory.decodeFile(image))));

                //adding to database
                AppDatabase.getInstance(getApplicationContext())
                        .taskDao()
                        .insert(product);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                finish();
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveProduct sd = new SaveProduct();
        sd.execute();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
