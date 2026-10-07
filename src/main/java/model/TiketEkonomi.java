package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adbang 18
 */
public class TiketEkonomi extends Tiket {
    private int bagasiKg;
 
    public TiketEkonomi(Penumpang penumpang, Penerbangan penerbangan) {
        super(penumpang, penerbangan);
        this.bagasiKg = 20;
    }
 
    @Override
    public String getNamaKelas() {
        return "Ekonomi";
    }
 
    @Override
    public double hitungTotalHarga() {
        return getPenerbangan().getHarga();
    }
 
    @Override
    public void tampilkanInfo() {
        System.out.println("-----------------------------------");
        System.out.println("[KATEGORI: TIKET EKONOMI]");
        super.tampilkanInfo();
        System.out.println("Bagasi           : " + bagasiKg + " kg");
        System.out.println("-----------------------------------");
    }
}
 