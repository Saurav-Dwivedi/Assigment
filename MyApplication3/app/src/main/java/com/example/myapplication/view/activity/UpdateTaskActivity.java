package com.example.myapplication.view.activity;

import android.annotation.SuppressLint;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Utils.Utility;
import com.example.myapplication.model.Model;
import com.example.myapplication.offline.AppDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static com.example.myapplication.Utils.Utility.getArrayListFromColorString;

public class UpdateTaskActivity extends AppCompatActivity {
    private EditText editTextName, editTextDescription, editTextRegularPrice,
            editTextSalePrice, editTextColors, editTextStores, editTextAddress;
    TextView textView;

    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        textView = findViewById(R.id.text);
        editTextName = findViewById(R.id.et_name);
        editTextDescription = findViewById(R.id.et_description);
        editTextRegularPrice = findViewById(R.id.et_regular_price);
        editTextSalePrice = findViewById(R.id.et_sale_price);
        editTextColors = findViewById(R.id.et_color);
        editTextStores = findViewById(R.id.et_stores);
        editTextAddress = findViewById(R.id.et_addressUpdate);


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.button_select).setOnClickListener(view -> pickFromGallery());

        final Model model = (Model) getIntent().getSerializableExtra("model");
        loadTask(model);


        findViewById(R.id.button_update).setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), R.string.clicked, Toast.LENGTH_LONG).show();
            updateTask(model);
        });
        findViewById(R.id.button_delete).setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
            builder.setTitle(getString(R.string.dialog_message));
            builder.setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> deleteTask(model));
            builder.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> {
            });

            AlertDialog ad = builder.create();
            ad.show();
        });
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }


    @SuppressLint("SetTextI18n")
    private void loadTask(Model model) {
        Log.e("update screen ", model.toString());
        editTextName.setText(model.getName());
        editTextDescription.setText(model.getDescription());
        editTextRegularPrice.setText(model.getRegular_price() + "");
        editTextSalePrice.setText(model.getSale_price() + "");
        byte[] byteArray = model.getImage();
        if (byteArray != null && byteArray.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            textView.setText(bmp + "");
        } else {
            textView.setText(R.string.no_image_found);
        }
        Log.e("in colors ", Utility.setColorsFromList(model.getColors()));
        editTextColors.setText(Utility.setColorsFromList(model.getColors()));
        editTextStores.setText(Utility.getStoreName(model.getStores()));
        editTextAddress.setText(Utility.getStoreAddress(model.getStores()));
    }

    private void updateTask(final Model model) {
        // final int ID = Integer.parseInt(editTextID.getText().toString().trim());
        final String DECRIPTION = editTextDescription.getText().toString().trim();
        final String NAME = editTextName.getText().toString().trim();
        final String REGULAR_PRICE = editTextRegularPrice.getText().toString().trim();
        final String SALE_PRICE = editTextSalePrice.getText().toString().trim();
        final String COLORS = editTextColors.getText().toString().trim();
        final String STORES = editTextStores.getText().toString().trim();
        final String ADDRESS = editTextAddress.getText().toString().trim();


        if (DECRIPTION.isEmpty()) {
            editTextDescription.setError("Description required");
            editTextDescription.requestFocus();
            return;
        }

        if (NAME.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
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

        int sale_price = 0, regular_price = 0;

        try {

            sale_price = Integer.parseInt(SALE_PRICE);
            regular_price = Integer.parseInt(REGULAR_PRICE);


        } catch (Exception e) {

        }
        int finalRegular_price = regular_price;
        int finalSale_price = sale_price;

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Model product = new Model();
                product.setId(model.getId());
                product.setName(NAME);
                product.setDescription(DECRIPTION);
                product.setRegular_price(finalRegular_price);
                product.setSale_price(finalSale_price);
                Log.e("image",image);
                if (image.equals("")){
                    product.setImage(model.getImage());

                }else {
                    product.setImage(Objects.requireNonNull(getBytesFromBitmap(BitmapFactory.decodeFile(image))));
                }
                product.setColors(getArrayListFromColorString(COLORS));
                product.setStores(Utility.getStoreInformation(STORES, ADDRESS));
                AppDatabase.getInstance(getApplicationContext())
                        .taskDao()
                        .update(product);
                Log.e("model", model.toString());
                Log.e("product", product.toString());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_LONG).show();
                finish();
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    private void deleteTask(final Model model) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase.getInstance(getApplicationContext())
                        .taskDao()
                        .delete(model);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), R.string.deleted, Toast.LENGTH_LONG).show();
                finish();

            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

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
