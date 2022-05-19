package group.fly.entities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import org.apache.tomcat.util.buf.HexUtils;
import org.json.JSONObject;
import org.json.XML;
import com.google.gson.Gson;
import group.fly.utilities.Logs;

public class TestServiceHandle {

	final static Logs log4jLogger = new Logs(TestServiceHandle.class);
	static Gson gson = new Gson();
	static String mUsername = "GOJEK_API";
	static String token = null;
	static String pssw = "44579130607852";
	// New Download Softpin Key : 865d4e8aafdda730321ca566
	// Update Datetime : 2020/04/15 13:52:09.947
	static String keyBDTime = "2021/03/16 10:30:04.630";
	static String url = "http://103.68.241.71:8080/ItopupService1.4/services/TopupInterface";

	static String xmlReq = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "<Body>"
			+ "<requestHandle xmlns=\"http://interfaces.itopup.vnptepay.vn\">" + "<requestData>[string]</requestData>"
			+ "</requestHandle>" + "</Body>" + "</Envelope>";

	static String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPPdHB0nNCVciNQaab1NQ+ztJnSEAyv0etO+MwKztBhJD41yXiRmIsbJvK3jpL426eguzTeete3UBsX0b0gpTwjgYiXdaA+vg64dTeTBBiVkxmwfG21W51tscQsXqWkKpKJJnteAN4OHHWZ1KmMuaT2fSqk6cfToobGHrve+uJgPAgMBAAECgYBpuo273glvwWTZgPIjTuKKUG+ByNKPNhoiv63iGAOXG+YQT2udoGKniUOyqKX+ilRzhAcZfEgoaurc3JN6194ZhHNQv2UuCUzcOOhstZnrJuDqKICqLdBGhLbE/jJfDqk+xnL9AXKaW3lgFWcUu/0vV+XTiSi8I17O6BmeED7ZoQJBAPyfgVBZ2uBtiq/Y1ZhnsPjc70+4MTPhYVJYi7c+Ea5DXTFjo4csDinnzmsqRwUhQsqwefYmlaFyhn3hstQfKRcCQQD3H6EoLyaRwj6xXbpJn+hVGVr+P5YsxIcl4wA96wha4bLMf5K23QrjL2Y1bzc3ce9J00G+5SxRjgmDpL4LFHPJAkATumkg20OKj0NrZCWaF07swGQ394xwHsO46I1QrBo5X61hdASLQ9d/Ukj+eXi+X9CVf56mqHC7APSb1AphImXVAkB4TkSljHbJZPgiXs/PAfMYYrSirBAPEUn+DsnSWvaBT/k8hyrErU2cljm6777A1k248gjCx0zRE0XcebovKLDJAkEAzs46kLgSaddR8qQF5hWWtn991JH3NYpSpxkMlBY2OGS4xkVWvI23lO8y4l/mRVn77hbBmGI0szNMhaKIFYN1YA==";
	static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyZPJ7SPxi4TokoQeosHZPnHMTndiozlrPCN1+XlhdKyAmI3YA09rnq+ops0E+Ao4YpFnqeSAnbDmaB70s1QAAFbo0kFfW7YmEePpmi6+03RVpHuT2sr3BQ8QLCFTkzlXP7vAU2QtLm+L1P5odEsQ1RL8oKYbpyWXvJ7IeL5cUkwIDAQAB";
	// Key dung de verify chu ky
	static String ImdPubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgJaWNT+de1XRKXsbhpXl1w+c/2A9cLlu5VsfPvXIVmbevGdThukqmvmhyMSbXAtjjLxI3/EnmgPEbA70BYLWg+Qua8n+xC2hrbGWJ3yDOe4YK6yM7z9Js08MI76wMI0RLlYHl7Cqj4pMvSUDFW/egikp5eQn3vSue8Hb2bAkORwIDAQAB";

	// MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChj0YbDuPLiLhFGsmgTwhadeg5PceK1kfL+tJnMxb/55/YCa0JXeCtMPz4vfzvuZZjtqKLiLXN9JrLfCJVIsGC+YrW1D3sGCsh+fwGB2hMQgh0GpbQo5zU7AaZ9RTzbGQgwJiBNhGzfl3uze5KDyw5Rm618DRpU8rMzTDlD1cxIQIDAQAB
	static Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {

		String xmlLoginReq = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "<Body>"
				+ "<signInAsPartner xmlns=\"http://interfaces.itopup.vnptepay.vn\">" + "<username>[username]</username>"
				+ "<password>[password]</password>" + "</signInAsPartner>" + "</Body>" + "</Envelope>";

		String loginReq = xmlLoginReq.replace("[username]", mUsername).replace("[password]", pssw);

		token = "f47462c319561227554348cd1911d6442fdab1ee72fefb3a";
		if (token == null) {
			try {
				String soapRp = sendSoapRequest(url, loginReq);
				System.out.println("KET QUA LOGIN=" + soapRp);
				JSONObject jsonObject = XML.toJSONObject(soapRp);
				JSONObject jsonEnvelope = jsonObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
						.getJSONObject("ns1:signInAsPartnerResponse").getJSONObject("signInAsPartnerReturn");
				Object token_ = jsonEnvelope.getJSONObject("token").get("content");
				token = token_.toString();
				System.out.println("LOGIN SUCCESS TOKEN:" + token);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}


//		0938004451	MobiFone - 8GB - 84,000VND - 30 days	Mobifone	DataQT84	84,000
//		0387224216	Viettel - 5GB - 90,000VND - 30 days	Viettel	VT90	90,000
//		0938156257	MobiFone - 8GB - 84,000VND - 30 days	Mobifone	DataQT84	84,000
//		0938106106	MobiFone - 8GB - 84,000VND - 30 days	Mobifone	DataQT84	84,000
//		0938518587	MobiFone - 8GB - 84,000VND - 30 days	Mobifone	DataQT84	84,000
//		0906709731	MobiFone - 8GB - 84,000VND - 30 days	Mobifone	DataQT84	84,000


		topup("0938004451"
				, 84000, token, "DataVMS");
//		reDownloadRequest(token, "MERCHANT_DEV99_161776820027076701");
//
//		downloadRequest(token, 12312312);

	}

	private static String topup(String targer, Integer amount, String token, String provider) {
		String xmlLoginReq = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" + "    <Body>\n"
				+ "        <partnerDirectTopupGame xmlns=\"http://interfaces.itopup.vnptepay.vn\">\n"
				+ "            <username>[string1]</username>\n"
				+ " 			<providerCode>[string5]</providerCode>\n"
				+ "            <targetAccount>[string2]</targetAccount>\n" + "            <amount>[int3]</amount>\n"
				+ "            <requestID>[string3]</requestID>\n" + "            <token>[string4]</token>\n"
				+ "        </partnerDirectTopupGame>\n" + "    </Body>\n" + "</Envelope>";
		
		

		String loginReq = xmlLoginReq.replace("[string1]", mUsername).replace("[string2]", targer)
				.replace("[int3]", amount + "").replace("[string3]", mUsername + "_" + System.currentTimeMillis())
				.replace("[string4]", token)
				.replace("[string5]", provider);

		System.out.println(loginReq);
		if (token != null) {
			try {
				String soapRp = sendSoapRequest(url, loginReq);
				System.out.println("KET QUA LOGIN=" + soapRp);
//				JSONObject jsonObject = XML.toJSONObject(soapRp);
//				JSONObject jsonEnvelope = jsonObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
//						.getJSONObject("ns1:signInAsPartnerResponse").getJSONObject("signInAsPartnerReturn");
//				Object token_ = jsonEnvelope.getJSONObject("token").get("content");
//				token = token_.toString();
				System.out.println("Topup RS:" + soapRp);
				return soapRp;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "EXception in calling";
			}
		}
		return "Token null";
	}

	private static void testverifySign() {
		// TODO Auto-generated method stub
		String data = "439LM.HNI122003.HNI115005.160421.4z2433162702779835510516270277983552021-04-16 10:50:48500399000";
		String sign = "FWtBjfqS4Kzz98RuEeRlF7C1nVvgeBHyM5V8/yDEa30q63xoQxXK+hKepr3g4zgEBw8+b34Q1f5nLyUyefiqUo+aHpajfLV8Qm/13k9m2eEcxfFnkAwmQczY0jbivbPQHozNiX+H8/ViP8XDIMOrSvv9qDGoZfzWWbOclqncY+k=";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnNwcoKwmYI0GwCCfoDCQ9aZ4U0G4Po1Y4CDgN3fddwVo0JFG7zUu6jsPq1A1X2Pls0uhPDo5wdeQgwB8rKBN3uBTfuYxBqWEc0FZiQYftmOgcoZlUlUDlsf/hZVYfN5aUjTkZ1VT2m2RhAhBxvqDOoLS8CePZsxxvOlYzJKTQIwIDAQAB";

		try {
			PublicKey ptnPKey = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKey)));

			System.out.println(verifySha(data, sign, ptnPKey));
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void calculateTotal(Long beforeTrans) {
		String msg = "Total is:" + (System.currentTimeMillis() - beforeTrans);
		System.out.println(msg);
		log4jLogger.info(msg);
	}

	private static void topupRequest(String token) {
		// String target = "0962345678";
		// String provider = "Viettel";

//		String provider = "Viettel";
//		String target = "0962345678";
		String target = "0932345678";
		String provider = "Mobifone";

		int accountType = 1;// 0: tra truoc,1: tra sau
		int amount = 123456;

		Random random = new Random(System.currentTimeMillis());
		MerchantReqObj merchantReqObj = new MerchantReqObj(1200, mUsername, null,
				mUsername + "_" + System.currentTimeMillis() + "=>" + random.nextInt(), null, target, provider, amount,
				"sig", token, "reqtime");
		merchantReqObj.setAccountType(accountType);
		String data = merchantReqObj.getUsername() + "|" + merchantReqObj.getRequestID() + "|"
				+ merchantReqObj.getToken() + "|" + merchantReqObj.getOperation();
		String dataLogin = merchantReqObj.getUsername() + "|" + merchantReqObj.getMerchantPass();

		try {
			PrivateKey privateKey = KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKeyStr)));
			String signature = signSha2(data, privateKey);

			merchantReqObj.setSignature(signature);
			PublicKey ptnPKey = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKeyStr)));
			System.out.println("Verify rs:" + verifySha(data, signature, ptnPKey));

			String jsonString = gson.toJson(merchantReqObj);

			String finalXmlReq = xmlReq.replace("[string]", jsonString);
			System.out.println("xml req=" + finalXmlReq);
			String soap2Rp = sendSoapRequest(url, finalXmlReq);
			System.out.println("SOAP RESPONSE" + soap2Rp);

			JSONObject jsonObject = XML.toJSONObject(soap2Rp);
			JSONObject jsonEnvelope = jsonObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
					.getJSONObject("ns1:requestHandleResponse").getJSONObject("requestHandleReturn");
			String jsonRp = jsonEnvelope.getString("content");
			MerchantRes merchantRes = (MerchantRes) gson.fromJson(jsonRp, MerchantRes.class);
			System.out.println("Message sau khi parse:" + gson.toJson(merchantRes));

			// errorCode + "|" + requestID + "|" + sysTransId
			// + "|" + token
			String signData = merchantRes.getErrorCode() + "|" + merchantRes.getRequestID() + "|"
					+ merchantRes.getSysTransId() + "|" + token;

			PublicKey publicKey_ = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(ImdPubKeyStr)));
			System.out.println("SIGN DATA:" + signData);
			System.out.println("VERIFY RS=" + verifySha2Hex(signData, merchantRes.getSignature(), publicKey_));
			log4jLogger.info("merchantReqObj.getRequestID()=" + merchantReqObj.getRequestID());
			log4jLogger.info("RS:" + merchantRes.getErrorCode());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Post request to soap service
	 * 
	 * @param url_
	 * @param xmlData
	 * @return
	 */
	public static String sendSoapRequest(String url_, String xmlData) {

		StringBuffer result = new StringBuffer();
		try {
			// =
			// buffer.getText(0,buffer.getLength())'
			java.net.URL url = new java.net.URL(url_);
			java.net.URLConnection conn = url.openConnection();

			HttpURLConnection httpConn = (HttpURLConnection) conn;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[xmlData.length()];
			buffer = xmlData.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			String SOAPAction = "http://tempuri.org/RequestTopup";
			// Set the appropriate HTTP parameters.
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", SOAPAction);
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			// Write the content of the request to the outputstream of the HTTP
			// Connection.
			out.write(b);
			out.close();
			// Ready with sending the request.

			// Read the response.
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			String responseString = null;
			String outputString = null;
			// Write the SOAP message response to a String.
			while ((responseString = in.readLine()) != null) {
				result.append(responseString);
			}

			log4jLogger.info("SOAP XML=" + xmlData);
			log4jLogger.info("CHARGE RESPONSE FROM PROVIDER=" + result.toString() + "");
		} catch (Exception e) {
			// TODO: handle exception
			log4jLogger.fatal("EXCEPTION,", e);
			// AlertBussiness.getInstance().composeAlertAndSend(e, 0);
		}
		System.out.println(result.toString());
		return result.toString();
	}

	private static void downloadRequest(String token, int reqId) {

		Long startAt = System.currentTimeMillis();
		BuyItem buyItem = new BuyItem();
		buyItem.setProductId(5);
		buyItem.setQuantity(3);

		BuyItem buyItem1 = new BuyItem();
		buyItem1.setProductId(6);
		buyItem1.setQuantity(2);

		// BuyItem buyItem2 = new BuyItem();
		// buyItem2.setProductId(6);
		// buyItem2.setQuantity(3);

		BuyItem[] buyItems = new BuyItem[2];
		buyItems[0] = buyItem;
		buyItems[1] = buyItem1;
		// Topup

		MerchantReqObj merchantReqObj = new MerchantReqObj(1000, mUsername, buyItems,
				mUsername + "_" + System.currentTimeMillis() + random.nextInt(99999), keyBDTime, "0912323233",
				"Vinaphone", 10000, "sig", token, "reqtime");
		merchantReqObj.setMerchantPass(pssw);

		System.out.println(merchantReqObj.toString());

		String data = merchantReqObj.getUsername() + "|" + merchantReqObj.getRequestID() + "|"
				+ merchantReqObj.getToken() + "|" + merchantReqObj.getOperation();
		String dataLogin = merchantReqObj.getUsername() + "|" + merchantReqObj.getMerchantPass();

		try {

			// <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
			// <Body>
			// <requestHandle xmlns="http://interfaces.itopup.vnptepay.vn">
			// <requestData>[string]</requestData>
			// </requestHandle>
			// </Body>
			// </Envelope>
			PrivateKey privateKey = KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKeyStr)));
			merchantReqObj.setSignature(signSha2(data, privateKey));

			String jsonString = gson.toJson(merchantReqObj);

			String finalXmlReq = xmlReq.replace("[string]", jsonString);
			System.out.println("xml req=" + finalXmlReq);
			String soap2Rp = sendSoapRequest(url, finalXmlReq);

			JSONObject jsonObject = XML.toJSONObject(soap2Rp);
			JSONObject jsonEnvelope = jsonObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
					.getJSONObject("ns1:requestHandleResponse").getJSONObject("requestHandleReturn");

			System.out.println("-------------------------------------------------------------------");
			System.out.println("KET QUA DOWNLOAD[" + reqId + "]:" + jsonEnvelope.getString("content").toString());
			log4jLogger.info("KET QUA DOWNLOAD[" + reqId + "]:" + jsonEnvelope.getString("content").toString()
					+ " TOTAL SPAN=" + (System.currentTimeMillis() - startAt));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log4jLogger.fatal("Co loi khi download", e);
		}

	}

	private static void reDownloadRequest(String token, String transactionId) {

		Long startAt = System.currentTimeMillis();
		BuyItem buyItem = new BuyItem();
		buyItem.setProductId(5);
		buyItem.setQuantity(1);

		BuyItem buyItem1 = new BuyItem();
		buyItem1.setProductId(6);
		buyItem1.setQuantity(1);

		// BuyItem buyItem2 = new BuyItem();
		// buyItem2.setProductId(6);
		// buyItem2.setQuantity(3);

		BuyItem[] buyItems = new BuyItem[2];
		buyItems[0] = buyItem;
		buyItems[1] = buyItem1;
		// Topup

		MerchantReqObj merchantReqObj = new MerchantReqObj(1100, mUsername, buyItems, transactionId, keyBDTime,
				"0912323233", "Vinaphone", 10000, "sig", token, "reqtime");
		merchantReqObj.setMerchantPass(pssw);

		System.out.println(merchantReqObj.toString());

		String data = merchantReqObj.getUsername() + "|" + merchantReqObj.getRequestID() + "|"
				+ merchantReqObj.getToken() + "|" + merchantReqObj.getOperation();
		String dataLogin = merchantReqObj.getUsername() + "|" + merchantReqObj.getMerchantPass();

		try {

			// <Envelope xmlns="http://schemas.xmlsoap.org/soap/envelope/">
			// <Body>
			// <requestHandle xmlns="http://interfaces.itopup.vnptepay.vn">
			// <requestData>[string]</requestData>
			// </requestHandle>
			// </Body>
			// </Envelope>
			PrivateKey privateKey = KeyFactory.getInstance("RSA")
					.generatePrivate(new PKCS8EncodedKeySpec(java.util.Base64.getDecoder().decode(privateKeyStr)));
			merchantReqObj.setSignature(signSha2(data, privateKey));

			String jsonString = gson.toJson(merchantReqObj);

			String finalXmlReq = xmlReq.replace("[string]", jsonString);
			System.out.println("xml req=" + finalXmlReq);
			String soap2Rp = sendSoapRequest(url, finalXmlReq);

			JSONObject jsonObject = XML.toJSONObject(soap2Rp);
			JSONObject jsonEnvelope = jsonObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body")
					.getJSONObject("ns1:requestHandleResponse").getJSONObject("requestHandleReturn");

			System.out.println("-------------------------------------------------------------------");
			System.out.println(
					"KET QUA REDOWNLOAD[" + transactionId + "]:" + jsonEnvelope.getString("content").toString());
			log4jLogger.info("KET QUA REDOWNLOAD[" + transactionId + "]:" + jsonEnvelope.getString("content").toString()
					+ " TOTAL SPAN=" + (System.currentTimeMillis() - startAt));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log4jLogger.fatal("Co loi khi download", e);
		}
	}

	public static String signSha2(String data, PrivateKey privateKey) {
		try {
			Signature rsa = Signature.getInstance("SHA256withRSA");
			rsa.initSign(privateKey);
			rsa.update(data.getBytes());
			// BASE64Encoder encoder = new BASE64Encoder();
			return java.util.Base64.getEncoder().encodeToString(rsa.sign());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Loi khi ky");
		}
		return "";
	}

	/// HÃ m verify chá»¯ kÃ½ báº±ng public key
	public static boolean verifySha2Hex(String data, String sign, PublicKey publicKey) {
		try {
			Signature rsa = Signature.getInstance("SHA256withRSA");
			rsa.initVerify(publicKey);
			rsa.update(data.getBytes());
			// BASE64Decoder decode = new BASE64Decoder();

			byte[] signByte = HexUtils.fromHexString(sign);
			// decode.decodeBuffer(sign);
			return (rsa.verify(signByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/// HÃ m verify chá»¯ kÃ½ báº±ng public key
	public static boolean verifySha(String data, String sign, PublicKey publicKey) {
		try {
			Signature rsa = Signature.getInstance("SHA256withRSA");
			rsa.initVerify(publicKey);
			rsa.update(data.getBytes());
			// BASE64Decoder decode = new BASE64Decoder();

			byte[] signByte = java.util.Base64.getDecoder().decode(sign);
			// decode.decodeBuffer(sign);
			return (rsa.verify(signByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
