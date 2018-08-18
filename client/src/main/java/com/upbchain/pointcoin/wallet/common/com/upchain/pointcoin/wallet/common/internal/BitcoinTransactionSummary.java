package com.upbchain.pointcoin.wallet.common.com.upchain.pointcoin.wallet.common.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upbchain.pointcoin.wallet.common.PointcoinTransactionInput;
import com.upbchain.pointcoin.wallet.common.PointcoinTransactionOutput;

import java.util.List;

/**
 * 
 * @author kevin.wang.cy@gmail.com
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinTransactionSummary {
    private long confirmations;
    private long time;
    private long timereceived;

    private String txId;
    private String hex;

    @JsonProperty("txid")
    public void setTxId(String txId) {
        this.txId = txId;
    }

    public long getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(long confirmations) {
        this.confirmations = confirmations;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimereceived() {

        // as unxcoin missing timereceived some time
        if (this.timereceived <= 0) {
            return this.time;
        }
        return timereceived;
    }

    public void setTimereceived(long timereceived) {
        this.timereceived = timereceived;
    }

    public String getTxId() {
        return txId;
    }

    public String getHex() {
        return this.hex;
    }

}
