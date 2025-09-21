package com.example.belajardatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FormLaptopActivity extends AppCompatActivity {
    private EditText edtMerk, edtModel, edtProcessor, edtRam, edtStorage, edtHarga;
    private ImageView imgGambar1, imgGambar2, imgGambar3;
    private Button btnPilih1, btnPilih2, btnPilih3, btnSimpan, btnUpdate, btnDelete;
    private DatabaseHelper db;
    private int laptopId = -1;
    private byte[] gambar1, gambar2, gambar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_laptop);

        initViews();
        db = new DatabaseHelper(this);

        if (getIntent().hasExtra("id")) {
            loadData();
        } else {
            btnUpdate.setVisibility(android.view.View.GONE);
            btnDelete.setVisibility(android.view.View.GONE);
        }

        btnPilih1.setOnClickListener(v -> pilihGambar(1));
        btnPilih2.setOnClickListener(v -> pilihGambar(2));
        btnPilih3.setOnClickListener(v -> pilihGambar(3));
        btnSimpan.setOnClickListener(v -> simpanData());
        btnUpdate.setOnClickListener(v -> updateData());
        btnDelete.setOnClickListener(v -> deleteData());
    }

    private void initViews() {
        edtMerk = findViewById(R.id.edtMerk);
        edtModel = findViewById(R.id.edtModel);
        edtProcessor = findViewById(R.id.edtProcessor);
        edtRam = findViewById(R.id.edtRam);
        edtStorage = findViewById(R.id.edtStorage);
        edtHarga = findViewById(R.id.edtHarga);
        imgGambar1 = findViewById(R.id.imgGambar1);
        imgGambar2 = findViewById(R.id.imgGambar2);
        imgGambar3 = findViewById(R.id.imgGambar3);
        btnPilih1 = findViewById(R.id.btnPilih1);
        btnPilih2 = findViewById(R.id.btnPilih2);
        btnPilih3 = findViewById(R.id.btnPilih3);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void loadData() {
        laptopId = getIntent().getIntExtra("id", -1);
        edtMerk.setText(getIntent().getStringExtra("merk"));
        edtModel.setText(getIntent().getStringExtra("model"));
        edtProcessor.setText(getIntent().getStringExtra("processor"));
        edtRam.setText(getIntent().getStringExtra("ram"));
        edtStorage.setText(getIntent().getStringExtra("storage"));
        edtHarga.setText(String.valueOf(getIntent().getDoubleExtra("harga", 0)));

        gambar1 = getIntent().getByteArrayExtra("gambar1");
        gambar2 = getIntent().getByteArrayExtra("gambar2");
        gambar3 = getIntent().getByteArrayExtra("gambar3");

        if (gambar1 != null) {
            Bitmap b1 = BitmapFactory.decodeByteArray(gambar1, 0, gambar1.length);
            imgGambar1.setImageBitmap(b1);
        }
        if (gambar2 != null) {
            Bitmap b2 = BitmapFactory.decodeByteArray(gambar2, 0, gambar2.length);
            imgGambar2.setImageBitmap(b2);
        }
        if (gambar3 != null) {
            Bitmap b3 = BitmapFactory.decodeByteArray(gambar3, 0, gambar3.length);
            imgGambar3.setImageBitmap(b3);
        }

        btnSimpan.setVisibility(android.view.View.GONE);
        btnUpdate.setVisibility(android.view.View.VISIBLE);
        btnDelete.setVisibility(android.view.View.VISIBLE);
    }

    private void pilihGambar(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                byte[] bytes = convertBitmapToByteArray(bitmap);

                switch (requestCode) {
                    case 1:
                        imgGambar1.setImageBitmap(bitmap);
                        gambar1 = bytes;
                        break;
                    case 2:
                        imgGambar2.setImageBitmap(bitmap);
                        gambar2 = bytes;
                        break;
                    case 3:
                        imgGambar3.setImageBitmap(bitmap);
                        gambar3 = bytes;
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Gagal memuat gambar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void simpanData() {
        if (validateInput()) {
            Laptop l = new Laptop(
                    0,
                    edtMerk.getText().toString(),
                    edtModel.getText().toString(),
                    edtProcessor.getText().toString(),
                    edtRam.getText().toString(),
                    edtStorage.getText().toString(),
                    Double.parseDouble(edtHarga.getText().toString()),
                    gambar1, gambar2, gambar3
            );
            db.insertLaptop(l);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void updateData() {
        if (validateInput()) {
            Laptop l = new Laptop(
                    laptopId,
                    edtMerk.getText().toString(),
                    edtModel.getText().toString(),
                    edtProcessor.getText().toString(),
                    edtRam.getText().toString(),
                    edtStorage.getText().toString(),
                    Double.parseDouble(edtHarga.getText().toString()),
                    gambar1, gambar2, gambar3
            );
            db.updateLaptop(l);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void deleteData() {
        db.deleteLaptop(laptopId);
        setResult(RESULT_OK);
        finish();
    }

    private boolean validateInput() {
        if (edtMerk.getText().toString().isEmpty() || gambar1 == null) {
            Toast.makeText(this, "Isi merk dan pilih minimal 1 gambar", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}