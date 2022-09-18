package dev.carter.financetracker;

public class Crypto {
    private String cryptoId;
    private float cryptoAmount;
    private float cryptoPrice;
    private double cryptoTotal;

    public Crypto(String cryptoId, float cryptoAmount, float cryptoPrice, double cryptoTotal) {
        this.cryptoId = cryptoId;
        this.cryptoAmount = cryptoAmount;
        this.cryptoPrice = cryptoPrice;
        this.cryptoTotal = cryptoTotal;
    }

    public String getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(String cryptoId) {
        this.cryptoId = cryptoId;
    }

    public float getCryptoAmount() {
        return cryptoAmount;
    }

    public void setCryptoAmount(float cryptoAmount) {
        this.cryptoAmount = cryptoAmount;
    }

    public float getCryptoPrice() {
        return cryptoPrice;
    }

    public void setCryptoPrice(float cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }

    public double getCryptoTotal() {
        return cryptoTotal;
    }

    public void setCryptoTotal(double cryptoTotal) {
        this.cryptoTotal = cryptoTotal;
    }
}
