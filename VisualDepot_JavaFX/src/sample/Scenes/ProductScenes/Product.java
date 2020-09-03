package sample.Scenes.ProductScenes;

import java.time.LocalDate;

public class Product{
    private String irsaliyeNo;
    private LocalDate SKTTarihi;
    private String depoSorumlusu;
    private LocalDate girisTarihi;
    private String urunAdi;
    private int urunMiktari;
    private int isExpired;
    public Product(String irsaliyeNo,String urunAdi,int urunMiktari,LocalDate girisTarihi,String depoSorumlusu){
        this.irsaliyeNo = irsaliyeNo;
        this.urunAdi = urunAdi;
        this.urunMiktari = urunMiktari;
        this.girisTarihi = girisTarihi;
        this.SKTTarihi = girisTarihi.plusMonths(1); // SKTTarihi translates into expiration date, which is 1 month.
        this.depoSorumlusu = depoSorumlusu;
        this.isExpired = LocalDate.now().compareTo(SKTTarihi);
    }
    //TODO If the expiration date is close, change the row colors according how close they are to the expiration date.
    public Product(String urunAdi,int urunMiktari,LocalDate SKTTarihi){
        this.urunAdi=urunAdi;
        this.urunMiktari=urunMiktari;
        this.SKTTarihi=SKTTarihi;
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
}
