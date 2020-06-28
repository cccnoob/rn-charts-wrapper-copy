package com.github.wuxudong.rncharts.data;


import com.github.mikephil.charting.data.Entry;

/**
 * @author by benny
 * @date 2020/6/22 10:11
 * @description
 */

public class TradeEntry extends Entry {
    private String TradeRate;
    private String TradeAmount;
    private String TradeAmountUnit;
    private String TradeDateTime;

    public String getTradeRate() {
        return TradeRate;
    }

    public void setTradeRate(String tradeRate) {
        TradeRate = tradeRate;
    }

    public String getTradeAmount() {
        return TradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        TradeAmount = tradeAmount;
    }

    public String getTradeAmountUnit() {
        return TradeAmountUnit;
    }

    public void setTradeAmountUnit(String tradeAmountUnit) {
        TradeAmountUnit = tradeAmountUnit;
    }

    public String getTradeDateTime() {
        return TradeDateTime;
    }

    public void setTradeDateTime(String tradeDateTime) {
        TradeDateTime = tradeDateTime;
    }
}
