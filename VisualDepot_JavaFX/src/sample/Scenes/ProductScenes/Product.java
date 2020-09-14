package sample.Scenes.ProductScenes;

import java.time.LocalDate;

public class Product{
    private String irsaliyeNo;
    private LocalDate SKTTarihi;
    private String depoSorumlusu;
    private LocalDate girisTarihi;
    private String urunAdi;
    private int urunMiktari;
    private int isExpiredCheck;
    private String isExpired;
    //USER INPUT CONSTRUCTOR
    public Product(String irsaliyeNo,String urunAdi,int urunMiktari,LocalDate girisTarihi,String depoSorumlusu){
        this.irsaliyeNo = irsaliyeNo;
        this.urunAdi = urunAdi;
        this.urunMiktari = urunMiktari;
        this.girisTarihi = girisTarihi;
        this.SKTTarihi = girisTarihi.plusMonths(1); // SKTTarihi translates into expiration date, which is 1 month.
        this.depoSorumlusu = depoSorumlusu;
        this.isExpiredCheck = LocalDate.now().compareTo(SKTTarihi);
        if (this.isExpiredCheck < 0){
            this.isExpired = "Son Kullanım Tarihi Geçmemiş";
        }
        else{
            this.isExpired = "Son Kullanım Tarihi Geçmiş";
        }
    }
    //DATABASE CONSTRUCTOR

    public Product(String irsaliyeNo,String urunAdi,int urunMiktari,LocalDate girisTarihi,LocalDate SKTTarihi){
        this.urunAdi=urunAdi;
        this.urunMiktari=urunMiktari;
        this.SKTTarihi=SKTTarihi;
        this.girisTarihi=girisTarihi;
        this.irsaliyeNo=irsaliyeNo;
        this.isExpiredCheck = LocalDate.now().compareTo(SKTTarihi);
        if (this.isExpiredCheck < 0){
            this.isExpired = "Son Kullanım Tarihi Geçmemiş";
        }
        else{
            this.isExpired = "SON KULLANIM TARİHİ GEÇMİŞ!";
        }
    }

    public Product(){
        this.irsaliyeNo = null;
        this.SKTTarihi = null;
        this.depoSorumlusu = null;
        this.girisTarihi = null;
        this.urunAdi = null;
        this.isExpired = null;
    }

    @Override
    public String toString() {
        return "Product{" +
                "irsaliyeNo='" + irsaliyeNo + '\'' +
                ", SKTTarihi=" + SKTTarihi +
                ", depoSorumlusu='" + depoSorumlusu + '\'' +
                ", girisTarihi=" + girisTarihi +
                ", urunAdi='" + urunAdi + '\'' +
                ", urunMiktari=" + urunMiktari +
                ", isExpired=" + isExpired +
                '}';
    }

    public String getIrsaliyeNo() {
        return irsaliyeNo;
    }

    public void setIrsaliyeNo(String irsaliyeNo) {
        this.irsaliyeNo = irsaliyeNo;
    }

    public LocalDate getSKTTarihi() {
        return SKTTarihi;
    }

    public void setSKTTarihi(LocalDate SKTTarihi) {
        this.SKTTarihi = SKTTarihi;
    }

    public String getDepoSorumlusu() {
        return depoSorumlusu;
    }

    public void setDepoSorumlusu(String depoSorumlusu) {
        this.depoSorumlusu = depoSorumlusu;
    }

    public LocalDate getGirisTarihi() {
        return girisTarihi;
    }

    public void setGirisTarihi(LocalDate girisTarihi) {
        this.girisTarihi = girisTarihi;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getUrunMiktari() {
        return urunMiktari;
    }

    public void setUrunMiktari(int urunMiktari) {
        this.urunMiktari = urunMiktari;
    }

    public String getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(String isExpired) {
        this.isExpired = isExpired;
    }
}
