/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Adbang 18
 */
public class TiketBisnis extends Tiket {
    private int bagasiKg;
 
    public TiketBisnis(Penumpang penumpang, Penerbangan penerbangan) {
        super(penumpang, penerbangan);
        this.bagasiKg = 30;
    }
 
    @Override
    public String getNamaKelas() {
        return "Bisnis";
    }
 
    @Override
    public double hitungTotalHarga() {
        return getPenerbangan().getHarga() * 1.5;
    }
 
    @Override
    public void tampilkanInfo() {
        System.out.println("-----------------------------------");
        System.out.println("[KATEGORI: TIKET BISNIS]");
        super.tampilkanInfo();
        System.out.println("Bagasi           : " + bagasiKg + " kg");
        System.out.println("-----------------------------------");
    }
}
 