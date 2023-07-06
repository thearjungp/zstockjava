package com.stockmarket.instruments;

public class Instrument
{
    int instrumentId;
    String instrumentName;
    float ltp;
    String type;
    float ltpChange;

    public Instrument() {}

    public Instrument(String instrumentName, float ltp, String type)
    {
        this.instrumentName = instrumentName;
        this.ltp = ltp;
        this.type = type;
        this.ltpChange = ltp;
    }

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public float getLtp() {
        return ltp;
    }

    public void setLtp(float ltp) {
        this.ltp = ltp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLtpChange() {
        return ltpChange;
    }

    public void setLtpChange(float ltpChange) {
        this.ltpChange = ltpChange;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "instrumentId=" + instrumentId +
                ", instrumentName='" + instrumentName + '\'' +
                ", ltp=" + ltp +
                ", type='" + type + '\'' +
                ", ltpChange=" + ltpChange +
                '}';
    }
}
