package com.upbchain.pointcoin.wallet.common;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upbchain.pointcoin.wallet.common.util.PointcoinWalletUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointcoinWalletClientTest {

    @Before
    public void setup() {
        System.out.println("setup...");
    }

    @After
    public void cleanup() {
        System.out.println("cleanup...");
    }

    @Test
    public void testPointcoinWalletClient() throws Exception {
//        PointcoinWalletClient client = newWalletClient("pointcoin");
//        testWalletClient(client);
    }

    @Test
    public void testUxcoinWalletClient() throws Exception {
//        PointcoinWalletClient client = newWalletClient("uxcoin");
//        testWalletClient(client);
    }

    @Test
    public void testBitcoinWalletClient() throws Exception {
        PointcoinWalletClient client = newWalletClient("bitcoin");
        assertEquals("bitcoin", client.getAlias());

        testWalletClient(client);
    }

    private void testWalletClient(PointcoinWalletClient client) throws Exception {

        // 1.--
        String memId = "memId-001";
        String memAddress = "3KYRNJ5FkctTPFfHV87XMz5TmjPMk2s9YS";
        assertEquals(memAddress, client.generateMemberMortgageAccountAddress(memId));

        // 2.--
        assertTrue(client.getRecievedByAddress(memAddress).equals(BigDecimal.valueOf(0)));
        assertTrue(client.getRecievedByAddress("38SUMFCsSPGt8VZmmZ1539aVDswxMC78Zg").equals(BigDecimal.valueOf(0.05008)));

        // 3. --
        PointcoinValidateAddressResult addValidResult = client.verifyPointcoinAddress(memAddress);
        assertTrue(addValidResult.isValid());
        assertTrue(addValidResult.isMine());
        assertEquals(PointcoinWalletUtil.buildMortgageDepositAccountName(memId).get(), addValidResult.getAccount());
        assertEquals("", addValidResult.getTicker());

        addValidResult = client.verifyPointcoinAddress("38SUMFCsSPGt8VZmmZ1539aVDswxMC78Zg");
        assertTrue(addValidResult.isValid());
        assertTrue(addValidResult.isMine());
        assertEquals("(2))Bitcoinaddress", addValidResult.getAccount());
        assertEquals("", addValidResult.getTicker());


        // 4. --
        assertTrue(client.isMemberMortgageAccountAddress(memId, memAddress));
        assertFalse(client.isMemberMortgageAccountAddress(anyString(), memAddress));
        assertFalse(client.isMemberMortgageAccountAddress(memId, "38SUMFCsSPGt8VZmmZ1539aVDswxMC78Zg"));

        // 5. --
        List<PointcoinAccountTransaction> accountTxList = client.retrieveLatestMortgageDepositTransactions(memId, 24, 10);
        assertEquals(1, accountTxList.size());

        PointcoinAccountTransaction accountTx = accountTxList.get(0);

        assertEquals(PointcoinWalletUtil.buildMortgageDepositAccountName(memId).get(), accountTx.getAccount());
        assertEquals("3KYRNJ5FkctTPFfHV87XMz5TmjPMk2s9YS", accountTx.getAddress());
        assertTrue(accountTx.getAmount().equals(BigDecimal.valueOf(0.003)));
        assertEquals("receive", accountTx.getCategory());
        assertEquals(24, accountTx.getConfirmations());
        assertEquals(1534312967, accountTx.getTime());
        assertEquals(1534312967, accountTx.getTimereceived());
        assertNull(accountTx.getCurrency());
        assertEquals("de111feab26d23ebbdcfb9f048dce24ebc58712c183c12d7856bc1c7d0fca2ca", accountTx.getTxid());


        // 6. --
        accountTxList = client.retrieveLatestMortgageDepositTransactions(memId, 24, 6);
        assertEquals(2, accountTxList.size());

        accountTxList = client.retrieveLatestMortgageDepositTransactions(memId, 24, 0);
        assertEquals(3, accountTxList.size());

        accountTx = accountTxList.get(2);

        assertEquals(PointcoinWalletUtil.buildMortgageDepositAccountName(memId).get(), accountTx.getAccount());
        assertEquals("fakeaddress", accountTx.getAddress());
        assertTrue(accountTx.getAmount().equals(BigDecimal.valueOf(9.001)));
        assertEquals("receive", accountTx.getCategory());
        assertEquals(0, accountTx.getConfirmations());
        assertEquals(1534312967, accountTx.getTime());
        assertEquals(1534312967, accountTx.getTimereceived());
        assertNull(accountTx.getCurrency());
        assertEquals("faketxid", accountTx.getTxid());


        // 7. --
        PointcoinTransaction tx = client.retrievePoincoinTransactionByTxId("de111feab26d23ebbdcfb9f048dce24ebc58712c183c12d7856bc1c7d0fca2ca");

        assertNotNull(tx);

        assertEquals(1, tx.getConfirmations());
        assertEquals(false, tx.isCoinbased());
        assertEquals(false, tx.isGenerated());
        assertEquals(1534312967L, tx.getTime());
        assertEquals(1534312967L, tx.getTimereceived());
        assertEquals("de111feab26d23ebbdcfb9f048dce24ebc58712c183c12d7856bc1c7d0fca2ca", tx.getTxId());
        assertEquals(1, tx.getTxInputs().size());
        assertEquals(2, tx.getTxOutputs().size());

        PointcoinTransactionInput txInput = tx.getTxInputs().get(0);

        assertEquals("2155a6cb8384bb66e0cc58a2e707844854c0ca5539557a584a5c2682218de4c1", txInput.getTxId());
        assertEquals(1, txInput.getVout());
        assertNull(txInput.getAddresses());
        assertNull(txInput.getValue());

        PointcoinTransactionOutput txOutput = tx.getTxOutputs().get(0);

        assertEquals(0, txOutput.getIndex());
        assertEquals(1, txOutput.getAddresses().size());
        assertEquals("3LDhQseXKBfAhJ3Gkj57CMGUUmfxtLNwTJ", txOutput.getAddresses().get(0));
        assertTrue(txOutput.getValue().equals(BigDecimal.valueOf(0.04397751)));
        assertNull(txOutput.getCurrency());

        txOutput = tx.getTxOutputs().get(1);
        assertEquals(1, txOutput.getIndex());
        assertEquals(1, txOutput.getAddresses().size());
        assertEquals("3KYRNJ5FkctTPFfHV87XMz5TmjPMk2s9YS", txOutput.getAddresses().get(0));
        assertTrue(txOutput.getValue().equals(BigDecimal.valueOf(0.003)));
        assertNull(txOutput.getCurrency());

        tx = client.retrievePoincoinTransactionByTxId("2155a6cb8384bb66e0cc58a2e707844854c0ca5539557a584a5c2682218de4c1");

        assertNotNull(tx);

        assertEquals(120, tx.getConfirmations());
        assertEquals(false, tx.isCoinbased());
        assertEquals(false, tx.isGenerated());
        assertEquals(1534312951L, tx.getTime());
        assertEquals(1534312951L, tx.getTimereceived());
        assertEquals("2155a6cb8384bb66e0cc58a2e707844854c0ca5539557a584a5c2682218de4c1", tx.getTxId());
        assertEquals(1, tx.getTxInputs().size());
        assertEquals(2, tx.getTxOutputs().size());

        txInput = tx.getTxInputs().get(0);

        assertEquals("6e8281ba0b13dd0d8088f44668eb1b959016a7f4b0914f0aae09323e489fe5cb", txInput.getTxId());
        assertEquals(1, txInput.getVout());
        assertNull(txInput.getAddresses());
        assertNull(txInput.getValue());

        txOutput = tx.getTxOutputs().get(0);

        assertEquals(0, txOutput.getIndex());
        assertEquals(1, txOutput.getAddresses().size());
        assertEquals("3KYRNJ5FkctTPFfHV87XMz5TmjPMk2s9YS", txOutput.getAddresses().get(0));
        assertTrue(txOutput.getValue().equals(BigDecimal.valueOf(0.002)));
        assertNull(txOutput.getCurrency());

        txOutput = tx.getTxOutputs().get(1);
        assertEquals(1, txOutput.getIndex());
        assertEquals(1, txOutput.getAddresses().size());
        assertEquals("37YrLNGnmK1aywZLLJT3PdJRDsJY6mEap7", txOutput.getAddresses().get(0));
        assertTrue(txOutput.getValue().equals(BigDecimal.valueOf(0.04698042)));
        assertNull(txOutput.getCurrency());
    }

    private PointcoinWalletClient newWalletClient(String type) throws Exception {
        switch (type) {
            case "pointcoin":
            case "uxcoin":
                return new PointcoinWalletClient(type, type, mockPointcoinRPCURL());
            case "bitcoin":
                return new PointcoinWalletClient(type, type, mockBitcoinRPCURL());
            default:
                throw new InvalidParameterException(type);
        }
    }

    private URL mockBitcoinRPCURL() throws Exception {
        URL mockURL = mock(URL.class);

        HttpURLConnection mockHttpConn = mock(HttpURLConnection.class);
        final ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

        when(mockHttpConn.getOutputStream()).thenAnswer(it -> {
            byteArrayOut.reset();

            return byteArrayOut;
        });

        when(mockHttpConn.getInputStream()).thenAnswer(it -> {

            String requestJson = byteArrayOut.toString();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode request = mapper.readTree(requestJson);

           return getBitcoinRPCResponse(request);
        });

        when(mockURL.openConnection(any(Proxy.class))).thenReturn(mockHttpConn);

        return mockURL;
    }

    private URL mockPointcoinRPCURL() {
        URL mockURL = mock(URL.class);

        // TODO

        return mockURL;
    }


    private InputStream getBitcoinRPCResponse(JsonNode request) throws Exception {
        String id = request.path("id").asText();
        String method = request.path("method").asText();
        JsonNode paramJsonNode = request.path("params");

        String jsonFileName = "";
        String memId = "";
        String address = "";
        String txId ="";
        String txHex = "";

        switch (method) {
            case "getaccountaddress":
                memId = PointcoinWalletUtil.extraMemberIdFromMortgageAccountName(paramJsonNode.get(0).textValue()).get();
                jsonFileName = "bitcoin-" + method + "-" + memId + ".json";
                break;
            case "getreceivedbyaddress":
                address = paramJsonNode.get(0).textValue();
                jsonFileName = "bitcoin-" + method + "-" + address + ".json";
                break;
            case "validateaddress":
                address = paramJsonNode.get(0).textValue();
                jsonFileName = "bitcoin-" + method + "-" + address + ".json";
                break;
            case "listtransactions":
                memId = PointcoinWalletUtil.extraMemberIdFromMortgageAccountName(paramJsonNode.get(0).textValue()).get();
                jsonFileName = "bitcoin-" + method + "-" + memId + ".json";
                break;
            case "gettransaction":
                txId = paramJsonNode.get(0).textValue();
                jsonFileName = "bitcoin-" + method + "-" + txId + ".json";
                break;
            case "decoderawtransaction":
                txHex = paramJsonNode.get(0).textValue();
                txHex = txHex.substring(0, 30);
                jsonFileName = "bitcoin-" + method + "-" + txHex + ".json";
                break;
            default:
                break;
        }

        return this.getClass().getResourceAsStream(jsonFileName);
    }
}
