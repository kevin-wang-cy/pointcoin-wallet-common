package com.upbchain.pointcoin.wallet.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PointcoinTransactionUnxCoinTest {

    @Before
    public void setup() {
    }

    @Test
    public void unx101NoTimeReceivedTest() {
        ObjectMapper mapper = new ObjectMapper();


        // unx101-1-in-2-out.confirmed-notimereceived.json
        PointcoinTransaction onein2outTx1ConfirmedNoTimreReceived = null;
        try {
            onein2outTx1ConfirmedNoTimreReceived = mapper.readValue(this.getClass().getResourceAsStream("unx101-1-in-2-out.confirmed-notimereceived.json"),
                    new TypeReference<PointcoinTransaction>() {
                    });

            assertEquals(1, onein2outTx1ConfirmedNoTimreReceived.getTxInputs().size());

            PointcoinTransactionInput input = onein2outTx1ConfirmedNoTimreReceived.getTxInputs().get(0);
            Arrays.asList(input).forEach(
                    it -> assertTrue(it.getTxId().equals("5b8a370244aa4cadf4009644c42a649bca19b0a48d7512169e9d0f965c5c91eb") && it.getVout() == 1
                            && it.getAddresses().get(0).equals("UPfNxCV8UCMMQh81iWxHHG5jDd7rbcv2JRK") && it.getValue().equals(BigDecimal.valueOf(9.354))));

            assertEquals(2, onein2outTx1ConfirmedNoTimreReceived.getTxOutputs().size());

            PointcoinTransactionOutput output = onein2outTx1ConfirmedNoTimreReceived.getTxOutputs().get(0);
            Arrays.asList(output).forEach(
                    it -> assertTrue(it.getCurrency().equals("UNH") && it.getIndex() == 0
                            && it.getAddresses().get(0).equals("UPfNxCV8UCMMQh81iWxHHG5jDd7rbcv2JRK") && it.getValue().equals(BigDecimal.valueOf(9.2039))));

            output = onein2outTx1ConfirmedNoTimreReceived.getTxOutputs().get(1);
            Arrays.asList(output).forEach(
                    it -> assertTrue(
                            it.getCurrency().equals("UNH")
                            && it.getIndex() == 1
                            && it.getAddresses().get(0).equals("UPfh34dkYsN5naoHxpawcTDf6K5oU5rdNbH")
                            && it.getValue().equals(new BigDecimal("0.0001"))));

            assertEquals("Check confirmation", Long.valueOf(106).longValue(), onein2outTx1ConfirmedNoTimreReceived.getConfirmations());
            assertEquals("Check time", Long.valueOf(1490343513).longValue(), onein2outTx1ConfirmedNoTimreReceived.getTime());

            // Sometime Unxcoid Tx missing timereceived, then we would like to use the time as timereceived when it happens.
            assertEquals("Check timereceived", onein2outTx1ConfirmedNoTimreReceived.getTime(), onein2outTx1ConfirmedNoTimreReceived.getTimereceived());

            assertTrue(!onein2outTx1ConfirmedNoTimreReceived.isCoinbased());
            assertTrue(!onein2outTx1ConfirmedNoTimreReceived.isGenerated());


        } catch (IOException ex) {
            fail("load unx101-1-in-2-out.confirmed-notimereceived.json with exception: " + ex.getMessage());
        }
        onein2outTx1ConfirmedNoTimreReceived = null;
    }

    @Test
    public void other2meGeneralTest() {
        ObjectMapper mapper = new ObjectMapper();

        // unx101-1-in-2-out.confirmed-other-tome.json
        PointcoinTransaction onein2outTx1ConfirmedOthertoMe = null;
        try {
            onein2outTx1ConfirmedOthertoMe = mapper.readValue(this.getClass().getResourceAsStream("unx101-1-in-2-out.confirmed-other-tome.json"),
                    new TypeReference<PointcoinTransaction>() {
                    });

            assertEquals(1, onein2outTx1ConfirmedOthertoMe.getTxInputs().size());

            PointcoinTransactionInput input = onein2outTx1ConfirmedOthertoMe.getTxInputs().get(0);
            Arrays.asList(input).forEach(
                    it -> assertTrue(
                            it.getTxId().equals("dc349b8e5d388d6f1307c18cb90dadb6fed63aa0b0fd501c652aca8889288ac0")
                                    && it.getVout() == 1
                                    && it.getAddresses().get(0).equals("UPjEBC2zKNyCy5MJt6wQ6FJRJRMGBiz8JYZ")
                                    && it.getValue().equals(new BigDecimal("97.85000000"))));

            assertEquals(2, onein2outTx1ConfirmedOthertoMe.getTxOutputs().size());

            PointcoinTransactionOutput output = onein2outTx1ConfirmedOthertoMe.getTxOutputs().get(0);
            Arrays.asList(output).forEach(
                    it -> assertTrue(it.getCurrency().equals("UNH") && it.getIndex() == 0
                            && it.getAddresses().get(0).equals("UPjAnLzqr2vi7x15kbLnDudeF32tPPui4bH")
                            && it.getValue().equals(new BigDecimal("20.00000000"))));

            output = onein2outTx1ConfirmedOthertoMe.getTxOutputs().get(1);
            Arrays.asList(output).forEach(
                    it -> assertTrue(it.getCurrency().equals("UNH") && it.getIndex() == 1
                            && it.getAddresses().get(0).equals("UPjEBC2zKNyCy5MJt6wQ6FJRJRMGBiz8JYZ")
                            && it.getValue().equals(new BigDecimal("77.70000000"))));

            assertEquals("Check confirmation", Long.valueOf(329).longValue(), onein2outTx1ConfirmedOthertoMe.getConfirmations());
            assertEquals("Check time", Long.valueOf(1490334549).longValue(), onein2outTx1ConfirmedOthertoMe.getTime());

            assertEquals("Check timereceived", Long.valueOf(1490334549).longValue(), onein2outTx1ConfirmedOthertoMe.getTimereceived());

            assertTrue(!onein2outTx1ConfirmedOthertoMe.isCoinbased());
            assertTrue(!onein2outTx1ConfirmedOthertoMe.isGenerated());


        } catch (IOException ex) {
            fail("load unx101-1-in-2-out.confirmed-other-tome.json with exception: " + ex.getMessage());
        }
        onein2outTx1ConfirmedOthertoMe = null;

    }


    @Test
    public void unx101SelfPayInSameAddressUnconfirm() {
        ObjectMapper mapper = new ObjectMapper();

        // unx101-1-in-2-out.confirmed-paytoself1.json
        PointcoinTransaction onein2outTx1ConfirmedToSelf = null;
        try {
            onein2outTx1ConfirmedToSelf = mapper.readValue(this.getClass().getResourceAsStream("unx101-1-in-2-out.confirmed-paytoself1.json"),
                    new TypeReference<PointcoinTransaction>() {
                    });

            assertEquals(1, onein2outTx1ConfirmedToSelf.getTxInputs().size());

            PointcoinTransactionInput input = onein2outTx1ConfirmedToSelf.getTxInputs().get(0);
            Arrays.asList(input).forEach(
                    it -> assertTrue(it.getTxId().equals("8fcd5c21283ec19bd18ec052fc7b6aede7b30d850b969247a44afe1efc9020a9")
                            && it.getVout() == 0
                            && it.getAddresses().get(0).equals("UPjAnLzqr2vi7x15kbLnDudeF32tPPui4bH")
                            && it.getValue().equals(new BigDecimal("20.00000000"))));

            assertEquals(2, onein2outTx1ConfirmedToSelf.getTxOutputs().size());

            PointcoinTransactionOutput output = onein2outTx1ConfirmedToSelf.getTxOutputs().get(0);
            Arrays.asList(output).forEach(
                    it -> assertTrue(it.getCurrency().equals("UNH") && it.getIndex() == 0
                            && it.getAddresses().get(0).equals("UPmAV6jnEAqe7oEBVe4hFqzmMA8xeS8m7Dj")
                            && it.getValue().equals(new BigDecimal("9.85000000"))));

            output = onein2outTx1ConfirmedToSelf.getTxOutputs().get(1);
            Arrays.asList(output).forEach(
                    it -> assertTrue(it.getCurrency().equals("UNH") && it.getIndex() == 1
                            && it.getAddresses().get(0).equals("UPjAnLzqr2vi7x15kbLnDudeF32tPPui4bH")
                            && it.getValue().equals(new BigDecimal("10.00000000"))));

            assertEquals("Check confirmation", Long.valueOf(23).longValue(), onein2outTx1ConfirmedToSelf.getConfirmations());
            assertEquals("Check time", Long.valueOf(1490351470).longValue(), onein2outTx1ConfirmedToSelf.getTime());

            assertEquals("Check timereceived", Long.valueOf(1490351470).longValue(), onein2outTx1ConfirmedToSelf.getTimereceived());

            assertTrue(!onein2outTx1ConfirmedToSelf.isCoinbased());
            assertTrue(!onein2outTx1ConfirmedToSelf.isGenerated());


        } catch (IOException ex) {
            fail("load unx101-1-in-2-out.confirmed-paytoself1.json with exception: " + ex.getMessage());
        }
        onein2outTx1ConfirmedToSelf = null;
    }



    @Test
    public void unconfirmMatchConfirmTest() {
        ObjectMapper mapper = new ObjectMapper();

        //unx101-1-in-2-out.unconfirmed-paytoself1.json & unx101-1-in-2-out.confirmed-paytoself1.json
        PointcoinTransaction onein2outTx1Unconfirmed2self1 = null;
        PointcoinTransaction onein2outTx1Confirmed2self1 = null;
        try {
            onein2outTx1Unconfirmed2self1 = mapper.readValue(this.getClass().getResourceAsStream("unx101-1-in-2-out.unconfirmed-paytoself1.json"),
                    new TypeReference<PointcoinTransaction>() {
                    });
        } catch (IOException ex) {
            fail("load unx101-1-in-2-out.unconfirmed-paytoself1.json with exception: " + ex.getMessage());
        }

        try {
            onein2outTx1Confirmed2self1 = mapper.readValue(this.getClass().getResourceAsStream("unx101-1-in-2-out.confirmed-paytoself1.json"),
                    new TypeReference<PointcoinTransaction>() {
                    });

            assertTrue(!onein2outTx1Confirmed2self1.isCoinbased());
            assertTrue(!onein2outTx1Confirmed2self1.isGenerated());
        } catch (IOException ex) {
            fail("load unx101-1-in-2-out.unconfirmed-paytoself1.json with exception: " + ex.getMessage());
        }

        assertTrue("Confirmed should match with Unconfirmed for same txid", shouldMatch(onein2outTx1Confirmed2self1, onein2outTx1Unconfirmed2self1));



    }

    private boolean shouldMatch(PointcoinTransaction one, PointcoinTransaction another) {

        assertEquals(one.getTime(), another.getTime());
        assertEquals(one.getTimereceived(), another.getTimereceived());
        assertEquals(one.getTxId(), another.getTxId());
        assertEquals(one.getTxInputs(), another.getTxInputs());
        assertEquals(one.getTxOutputs(), another.getTxOutputs());
        assertEquals(one.isCoinbased(), another.isCoinbased());
        assertEquals(one.isGenerated(), another.isGenerated());

        return true;
    }

}