package com.example.belajardatabase;

public class Laptop {
    private int id;
    private String merk, model, processor, ram, storage;
    private double harga;
    private byte[] gambar1, gambar2, gambar3;

    public Laptop(int id, String merk, String model, String processor, String ram,
                  String storage, double harga, byte[] gambar1, byte[] gambar2, byte[] gambar3) {
        this.id = id;
        this.merk = merk;
        this.model = model;
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
        this.harga = harga;
        this.gambar1 = gambar1;
        this.gambar2 = gambar2;
        this.gambar3 = gambar3;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMerk() { return merk; }
    public void setMerk(String merk) { this.merk = merk; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getProcessor() { return processor; }
    public void setProcessor(String processor) { this.processor = processor; }
    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }
    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public byte[] getGambar1() { return gambar1; }
    public void setGambar1(byte[] gambar1) { this.gambar1 = gambar1; }
    public byte[] getGambar2() { return gambar2; }
    public void setGambar2(byte[] gambar2) { this.gambar2 = gambar2; }
    public byte[] getGambar3() { return gambar3; }
    public void setGambar3(byte[] gambar3) { this.gambar3 = gambar3; }
}