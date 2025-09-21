package com.example.belajardatabase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;

    private DatabaseHelper db;
    private RecyclerView recyclerView;
    private Button btnTambah, btnLogout;
    private EditText edtSearch;
    private FloatingActionButton fabTambah;
    private LaptopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        initViews();
        db = new DatabaseHelper(this);
        loadLaptops();

        btnTambah.setOnClickListener(v -> openForm(null));
        fabTambah.setOnClickListener(v -> openForm(null));

        // 🔹 Logika tombol Logout
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        btnTambah = findViewById(R.id.btnTambah);
        edtSearch = findViewById(R.id.edtSearch);
        fabTambah = findViewById(R.id.fabTambah);
        btnLogout = findViewById(R.id.btnLogout); // 🔹 ambil dari XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadLaptops() {
        List<Laptop> laptops = db.getAllLaptops();
        adapter = new LaptopAdapter(laptops, this, this::openForm);
        recyclerView.setAdapter(adapter);
    }

    private void openForm(Laptop laptop) {
        Intent intent = new Intent(this, FormLaptopActivity.class);
        if (laptop != null) {
            intent.putExtra("id", laptop.getId());
            intent.putExtra("merk", laptop.getMerk());
            intent.putExtra("model", laptop.getModel());
            intent.putExtra("processor", laptop.getProcessor());
            intent.putExtra("ram", laptop.getRam());
            intent.putExtra("storage", laptop.getStorage());
            intent.putExtra("harga", laptop.getHarga());
            intent.putExtra("gambar1", laptop.getGambar1());
            intent.putExtra("gambar2", laptop.getGambar2());
            intent.putExtra("gambar3", laptop.getGambar3());
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadLaptops();
            Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
        }
    }
}
