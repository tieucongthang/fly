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
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.util.buf.HexUtils;
import org.json.JSONObject;
import org.json.XML;
import com.google.gson.Gson;
import group.fly.utilities.Logs;

public class TestServiceHandle2 {

	final static Logs log4jLogger = new Logs(TestServiceHandle2.class);
	static Gson gson = new Gson();
	static String mUsername = "VIETDA_TST";
	static String token = null;
	static String pssw = "25959746718256";
//	 New Download Softpin Key : b620485f8e5c64ac562375f2
	// Update Datetime : 2020/04/15 13:52:09.947
	static String keyBDTime = "2021/03/30 09:12:36.457";
	static String url = "http://192.168.100.191/ItopupService1.4_IMD/services/TopupInterface?wsdl";
//	static String url = "http://localhost:8080/IMEDIA_ITOPUP_API/services/TopupInterface?wsdl";

	static String xmlReq = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "<Body>"
			+ "<requestHandle xmlns=\"http://interfaces.itopup.vnptepay.vn\">" + "<requestData>[string]</requestData>"
			+ "</requestHandle>" + "</Body>" + "</Envelope>";

	static String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKM9xmhKUjUDA2b7U8ICaRGJdPlOF+N3Zc+BF5QSaUD45vu+2K3ndgaDxqAsu15OnWwuSqRjOvL7GcCI9QKrzz7HzKnAkx0McTOL/1l2r6drubcyw4kB44I6+KVyoQeWcSMg+GjwYI0IZWcSkFih4up/1tEHtqDFZvuL9aqR1N71AgMBAAECgYBAxsSyobtZWebBuL2V4Qr8cIVAYKLOGpUF8H4AvgWSSLKv9x1YdN662xO4f8eiNqP1R7FK5i2AOfAQHMjM1IyaAUleW7VuIu5xTBd5xvGIWXi/2SRZWeGsQ06pk8MLIETF9bA190vyGU/L1Rad5qiR0irfGSqsXbW9NmDCGDjFAQJBAPtoipofggKAQEzz4n9fq0w83S6X0wfZOERo/jNmdB+GAoL6N/b/xjqbS6K8h4Oky7UM2WYlSLy2n5kp8i6KixUCQQCmOQJMCaC+pkWxgHhrOJpN5o/agm47c9pxJdKngkAdAWRbs9qMuN2XBOc8YPzzQndwWzEFRNJ352+7xM4iGHxhAkA7sqVG17h9c4rROxBLgqIgZkt3JuIbeP8db3j9J5Tnb2DlTRlA85dUj0+0guQC5Hzirvxofu34sbzQF9/EIW3JAkBiU/b34/oizVkM12Jkiuc8jdcq+10Z7j6aEDW8wWALyHcFiC8Qb4sletEO9pCrlfXxkQA+jaezJkUCXjlSEgoBAkBBwXN2ga86+vcKFVDBKxDUgwbiRRyWiiR7tead6kkNdD46lTlkISpQYNMNSYVr8byIFPlzBK9r8Kq7+rO1hO/z";
	static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjPcZoSlI1AwNm+1PCAmkRiXT5Thfjd2XPgReUEmlA+Ob7vtit53YGg8agLLteTp1sLkqkYzry+xnAiPUCq88+x8ypwJMdDHEzi/9Zdq+na7m3MsOJAeOCOvilcqEHlnEjIPho8GCNCGVnEpBYoeLqf9bRB7agxWb7i/WqkdTe9QIDAQAB";
	// Key dung de verify chu ky
	static String ImdPubKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgJaWNT+de1XRKXsbhpXl1w+c/2A9cLlu5VsfPvXIVmbevGdThukqmvmhyMSbXAtjjLxI3/EnmgPEbA70BYLWg+Qua8n+xC2hrbGWJ3yDOe4YK6yM7z9Js08MI76wMI0RLlYHl7Cqj4pMvSUDFW/egikp5eQn3vSue8Hb2bAkORwIDAQAB";

	// MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChj0YbDuPLiLhFGsmgTwhadeg5PceK1kfL+tJnMxb/55/YCa0JXeCtMPz4vfzvuZZjtqKLiLXN9JrLfCJVIsGC+YrW1D3sGCsh+fwGB2hMQgh0GpbQo5zU7AaZ9RTzbGQgwJiBNhGzfl3uze5KDyw5Rm618DRpU8rMzTDlD1cxIQIDAQAB
	static Random random = new Random(System.currentTimeMillis());
	static AtomicInteger failCounter = new AtomicInteger(0);
	static AtomicInteger successCounter = new AtomicInteger(0);

	public static void main(String[] args) {

		// String privateKey =
		// "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKGPRhsO48uIuEUayaBPCFp16Dk9x4rWR8v60mczFv/nn9gJrQld4K0w/Pi9/O+5lmO2oouItc30mst8IlUiwYL5itbUPewYKyH5/AYHaExCCHQaltCjnNTsBpn1FPNsZCDAmIE2EbN+Xe7N7koPLDlGbrXwNGlTyszNMOUPVzEhAgMBAAECgYB9CmnFGXIfHnMYMRJqe6DIesdAPluJTi2FZ1yfOQCNEZU6XMrLHBh/kxHH5yuz0pzeEgWeHzIu9Ck2Yp4j7GF+I5LR1l+m33d2JxGhWF1LmMXpkARmtyOqWeWsJpHZwem47Wq4Xok2ycOLDhoTKU1fabv91VHDg9deEGLLhIrjDQJBAOPiQvP/Y20MWb9YLCDc+5IqbI8D9cR95RPOFcWDHzTqQiS5IXpRNM/IzxOSj3c+M0uNuSIWDfOXgzQ/eO77JWcCQQC1fifF2dbqtgSdLd1pzxTreZ9TGQGQeKFLhRMJE3XEmTEylqR2YfuTXL1SrT3yAFH7hyM5/uEncntRHp+8S5g3AkEAvuPf1ehO7x/zZfeEL5stGQl0mnBkxU34QJjXX/Ywg/Dfq0dxJbc0mAHMhMa2atqtuVJkjIsQ8MYwX+nzcazrbQJBAKogpLXBGsYPc0nHaYq6tDh7XI8vnJjJiyDYkFxsonN0ynpKXcYe2Q1s3Id4n92O0Uq/hZTiyDoLBW+oDkIadocCQQCBulAGU5WIguRQ6R/IVB3u3SUl3j24ENaSl6S1D8sNUYPDIdox0l0lRWJZhqoHzaLqIuiAvQp1G0yW+0l9lQza";
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx0uCHe77O49goG1K5bTgnMsKbMaDgEaBMvnC0QtMor93Bcn2teiLHU71bN56us/GYoj4NlSpoG5HDcUs80Pp3VekabO4asVpVHzdHwhY5w+il+F0a82+EZV25kjc1qdOlCZ8FP4uoubQ48dKGjNGwkC//8BBnon2QpmIE/TcPWcb0oOMp3xjox3lAHoCdw1iaCky11nSMUjfmGI+YZjyaPzELtRoaO55CnsKWl2II6hRskzFD8C0mtx/YvhJKSmyFMp9msrMsTDNflmfbMud8o6Y3oFeli7MgbDOej+ASpas9K6EnmBhIjFRZCJMnl2M8u0zsAv1DgVkbBOBmSfADQIDAQAB";
		try {
			PublicKey ptnPKey = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(publicKey)));

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}

		String xmlLoginReq = "<Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "<Body>"
				+ "<signInAsPartner xmlns=\"http://interfaces.itopup.vnptepay.vn\">" + "<username>[username]</username>"
				+ "<password>[password]</password>" + "</signInAsPartner>" + "</Body>" + "</Envelope>";

		String loginReq = xmlLoginReq.replace("[username]", mUsername).replace("[password]", pssw);

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
		// RSA rsa = new RSA();
		Integer totalThread = 1;
		Integer totalSimutaneusTransaction = 1;
		try {
			for (int i = 0; i < totalThread; i++) {
				for (int j = 0; j < totalSimutaneusTransaction; j++) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Long beforeTopup = System.currentTimeMillis();
							System.out.println("\r\r\r\r\rSTART TOPUP API LINUX:");
//							String reqId = topupRequest(token);
							Long afTopup = System.currentTimeMillis();
							calculateTotal(beforeTopup, "");

							Long beforeTopup_ = System.currentTimeMillis();
							System.out.println("\r\r\r\r\rSTART DOWNLOAD API LINUX:");
							String reqId_ = downloadRequest(token, 0);
							calculateTotal(beforeTopup_, reqId_);
						}
					}).start();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

//		try {
//			for (int j = 0; j < totalThread; j++) {
//				for (int i = 0; i < totalSimutaneusTransaction; i++) {
//					final int thId = i;
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							Long beforeTopup = System.currentTimeMillis();
//							System.out.println("\r\r\r\r\rSTART DOWNLOAD API LINUX:");
//							downloadRequest(token, thId);
//							calculateTotal(beforeTopup, thId + "");
//						}
//					}).start();
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}

		while (true) {
			System.out.println("---------------------------------------------------------Ket thuc");
			System.out.println("Total sucess:" + successCounter.get());
			System.out.println("Total fail:" + failCounter.get());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void calculateTotal(Long beforeTrans, String reqId) {
		System.out.println("------------------------Total is:" + (System.currentTimeMillis() - beforeTrans));
		log4jLogger.info("reqId: " + reqId + "=" + (System.currentTimeMillis() - beforeTrans));
	}

	private static String topupRequest(String token) {
		// String target = "0962345678";
		// String provider = "Viettel";

		String target = "0962345678";
		String provider = "Viettel";
		int accountType = 1;// 0: tra truoc,1: tra sau
		int amount = 50000;

		Random random = new Random(System.currentTimeMillis());
		Long threadId = Thread.currentThread().getId();
		MerchantReqObj merchantReqObj = new MerchantReqObj(1200, mUsername, null,
				mUsername + "_TP_" + System.currentTimeMillis() + random.nextInt(99999) + "_" + threadId, null, target,
				provider, amount, "sig", token, "reqtime");
		merchantReqObj.setAccountType(accountType);
		String data = merchantReqObj.getUsername() + "|" + merchantReqObj.getRequestID() + "|"
				+ merchantReqObj.getToken() + "|" + merchantReqObj.getOperation();
		String dataLogin = merchantReqObj.getUsername() + "|" + merchantReqObj.getMerchantPass();

		String maGd = merchantReqObj.getRequestID();
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
			if (merchantRes.getErrorCode() == -4) {
				log4jLogger.info("Co loi:" + merchantRes.getErrorCode());
			}
			// errorCode + "|" + requestID + "|" + sysTransId
			// + "|" + token
			String signData = merchantRes.getErrorCode() + "|" + merchantRes.getRequestID() + "|"
					+ merchantRes.getSysTransId() + "|" + token;

			PublicKey publicKey_ = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(java.util.Base64.getDecoder().decode(ImdPubKeyStr)));
			System.out.println("SIGN DATA:" + signData);
			System.out.println("VERIFY RS=" + verifySha2Hex(signData, merchantRes.getSignature(), publicKey_));
			successCounter.getAndIncrement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			failCounter.getAndIncrement();
			log4jLogger.fatal("co mot loi j do:" + maGd, e);
		}
		return maGd;
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
			httpConn.setReadTimeout(60000);
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

	private static String downloadRequest(String token, int reqId) {

		Long startAt = System.currentTimeMillis();
		BuyItem buyItem = new BuyItem();
		buyItem.setProductId(17);
		buyItem.setQuantity(3);

		// BuyItem buyItem2 = new BuyItem();
		// buyItem2.setProductId(6);
		// buyItem2.setQuantity(3);

		BuyItem[] buyItems = new BuyItem[1];
		buyItems[0] = buyItem;
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
			successCounter.getAndIncrement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log4jLogger.fatal("Co loi khi download", e);
			failCounter.getAndIncrement();
		}
		return merchantReqObj.getRequestID();
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

	/// Hàm verify chữ ký bằng public key
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

	/// Hàm verify chữ ký bằng public key
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
