package group.fly;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.annotation.Configuration;

import group.fly.cache.RedisCaching;
import group.fly.transport.entities.TblMoneyTransferTransaction;
import group.fly.utilities.Classloader;
import group.fly.utilities.Logs;
import redis.clients.jedis.Jedis;

/**
 * @author T490
 *
 */
@Configuration
public class MainApplication {

	final static String pathBinaryFile = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	final static String PATH_WEB_DRIVER_WINDOW = "./config/webdriver/geckodriver.exe";
	final static String pathWebdriverLinux = "./config/webdriver/geckodriver.exe";
	final static String PROFILE_NAME = "default-release";

	static Timer timer = new Timer("timerFactory");
	static final Logs LOGS = new Logs(MainApplication.class);
	final static String QUEUE_NAME = "";
	final static Boolean RUN_HEADLESS = false;
	
	public static DecimalFormat df2 = new DecimalFormat("#.##");
	final static Integer KEEP_TRENDING_COUNTER = 3;

	
	/**
	 * Hàm main đây nhé
	 * @param args
	 */
	public static void main(String[] args) {
		Classloader.loadLib();
		setTimeZone();
	}

	
	
	private static void setTimeZone() {
		String defaultTimeZone = "Asia/Bangkok";
		// checking default time zone
		String timezoneId = defaultTimeZone;
		// Calendar.getInstance().getTimeZone().getID();
		// create time zone object

		TimeZone tzone = TimeZone.getTimeZone(timezoneId);

		System.out.println("Current TZ:" + tzone);
		// set time zone to default
		TimeZone.setDefault(tzone);

		// checking default time zone
		System.out.println("Default time zone:" + tzone);
	}

	
	/**
	 * Jan 9, 2021-10:35:28 PM Vietda Tinh toan thay Ä‘á»•i % giá»¯a hai bÆ°á»›c giÃ¡ gáº§n
	 * nhau
	 * 
	 * @param current
	 * @param previousPrice
	 * @return
	 */
	static Double calculatePercentCurrentandPrevious(Double current, Double previousPrice) {
		Double changeAmount = current - previousPrice;
		Double changePercent = (changeAmount / previousPrice) * 100;
		return changePercent;
	}

	

	/**
	 * Dec 25, 2020-2:09:49 PM Vietda Khá»Ÿi táº¡o webdriver
	 * 
	 * @return
	 * @throws Exception
	 */
	private static WebDriver innitDriver() throws Exception {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.gecko.driver", PATH_WEB_DRIVER_WINDOW);
		ProfilesIni profileIni = new ProfilesIni();

		FirefoxProfile firefoxProfile = profileIni.getProfile(PROFILE_NAME);
		FirefoxOptions ffO = new FirefoxOptions();
		ffO.setProfile(firefoxProfile);
		ffO.setHeadless(RUN_HEADLESS);
		WebDriver driver = new FirefoxDriver(ffO);
		return driver;
	}

	/**
	 * Dec 10, 2020-2:46:54 PM Vietda
	 * 
	 * @param tblMoneyTransferTransaction
	 */
	static void siteScripRemitano(TblMoneyTransferTransaction tblMoneyTransferTransaction) {
		System.out.println("Hello world of selenium");
		String innitUrl = "https://remitano.com/btc/vn/dashboard/wallets/vnd/withdrawal";

		WebDriver driver = null;
		try {
			driver = innitDriver();
		} catch (Exception e) {
			// TODO: handle exception
			LOGS.fatal("loi khi innit driver ket thuc xu ly", e);
			return;
		}

		try {
			driver.get(innitUrl);
			Thread.sleep(500);
			if (driver.getCurrentUrl().equalsIgnoreCase("https://remitano.com/btc/vn/login")) {
				// Retry login
				System.out.println("Login de lay lai session moi");
				String loginGoogleCssSl = "#scrollable-body > div.main > section > div > div > div > div.login-panel > div > div.vertical-layout.item-spacing-lg > div:nth-child(1) > button";
				WebElement loginGgElem = driver.findElement(By.cssSelector(loginGoogleCssSl));
				loginGgElem.click();
				Thread.sleep(2000);
				driver.get(innitUrl);
			}
			String url = "https://remitano.com/btc/vn/dashboard/wallets/vnd/deposit";//Táº¡o yÃªu cáº§u náº¡p tiá»�n;
			WebElement selectBank = driver.findElement(By.id("from_bank_name"));
			Select select = new Select(selectBank);
			select.selectByVisibleText("Techcombank");
			
			WebElement inputAmount = driver.findElement(By.id("fiat_amount"));
			inputAmount.sendKeys(tblMoneyTransferTransaction.getRequestAmount().intValue()+"");
			
			String ccsSubmitDebit = "#scrollable-body > div.main > div > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div.row > div.outline-none.d-none.d-md-block.d-block.col-md-7 > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div > div > div.css-1dbjc4n.r-1ifxtd0.r-1x4r79x > div > div.card-body > form > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > button";
			WebElement submitButton = driver.findElement(By.cssSelector(ccsSubmitDebit));
			submitButton.click();
			
			String creditAmount = "#scrollable-body > div.main > div > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div.row > div.outline-none.d-none.d-md-block.d-block.col-md-7 > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div > div > div > div > div.card-body > div:nth-child(2) > div.css-1dbjc4n.r-1peese0.r-1x4r79x > div > table > tbody > tr:nth-child(1) > td.text-success.font-weight-bold > div > div > div.content > span > span.safe-copy-fiat-amount";
			WebElement amount = driver.findElement(By.cssSelector(creditAmount));
			
			String cssContent = "#scrollable-body > div.main > div > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div.row > div.outline-none.d-none.d-md-block.d-block.col-md-7 > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div > div > div > div > div.card-body > div:nth-child(2) > div.css-1dbjc4n.r-1peese0.r-1x4r79x > div > table > tbody > tr:nth-child(2) > td.font-weight-bold > div > div > div.content";
			WebElement content = driver.findElement(By.cssSelector(cssContent));
			
			String cssReceiver = "#scrollable-body > div.main > div > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div.row > div.outline-none.d-none.d-md-block.d-block.col-md-7 > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div > div > div > div > div.card-body > div:nth-child(2) > div.css-1dbjc4n.r-1peese0.r-1x4r79x > div > table > tbody > tr:nth-child(3) > td.fiat-deposit-field.bank_account_name > div > div > div.content";
			WebElement receiver = driver.findElement(By.cssSelector(cssReceiver));
			
			String cssReceiverAcc = "#scrollable-body > div.main > div > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div.row > div.outline-none.d-none.d-md-block.d-block.col-md-7 > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div.css-1dbjc4n.r-p1pxzi.r-1x4r79x > div > div > div > div > div > div > div.card-body > div:nth-child(2) > div.css-1dbjc4n.r-1peese0.r-1x4r79x > div > table > tbody > tr:nth-child(4) > td.fiat-deposit-field.bank_account_number > div > div > div.content";
			WebElement receiverAcc = driver.findElement(By.cssSelector(cssReceiverAcc));
			
			System.out.println("So tien:"+ amount.getText());
			System.out.println("Noi dung:"+ content.getText());
			System.out.println("ten tk nhan:"+ receiver.getText());
			System.out.println("so tk nhan:"+ receiverAcc.getText());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * Dec 10, 2020-2:46:54 PM Vietda
	 * 
	 * @param tblMoneyTransferTransaction
	 */
	static void siteFiaHub(TblMoneyTransferTransaction tblMoneyTransferTransaction) {
		try {
			String innitUrl = "https://www.fiahub.com/login/";
			System.out.println("Hello Fia Hub");
			// System.setProperty("webdriver.chrome.driver",
			// "E:\\chromedriver_win32\\chromedriver.exe");

//			Map<String, Object> prefs = new HashMap<String, Object>();
//			prefs.put("binary", pathBinaryFile);

			System.setProperty("webdriver.gecko.driver", pathWebdriverLinux);
//			ProfilesIni profileIni = new ProfilesIni();
//
//			FirefoxProfile firefoxProfile = profileIni.getProfile(profileName);
			FirefoxOptions ffO = new FirefoxOptions();
//			ffO.setProfile(firefoxProfile);
			ffO.setHeadless(RUN_HEADLESS);
			WebDriver driver = new FirefoxDriver(ffO);

//			driver.get("https://remitano.com/btc/vn/dashboard/wallets");
			driver.get(innitUrl);

			WebElement webElementUsername = driver.findElement(By.name("email"));
			webElementUsername.sendKeys("dovietanh258@gmail.com");

			WebElement webElementPass = driver.findElement(By.name("password"));
			webElementPass.sendKeys("YouOnlyLiveOnce");

			WebElement loginButton = driver
					.findElement(By.xpath("//*[@id=\"root\"]/div[4]/div/div[2]/div/form/div[3]/button"));
			loginButton.click();

			driver.get("https://www.fiahub.com/withdraw/vnt");

			Thread.sleep(500);
			WebElement selElem = driver
					.findElement(By.xpath("//*[@id=\"root\"]/div[6]/div/div/div[2]/form/div[3]/div/div[1]/input"));
			selElem.sendKeys("100000");

			LOGS.info("Nhap so tien:" + 10000);
			System.out.println("Nhap so tien can ban:" + 10000);

			LOGS.info("Login succes");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LOGS.info("Co loi khi login", e);
		}
	}

	static void processTransfer() {
		timer.schedule(new TimerTask() {
			/**
			 *
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LOGS.info("----------------Start tien trinh xu ly--------------------------");
				LOGS.info("current=" + new Date());
				Jedis jedis = null;
				try {
					jedis = RedisCaching.getInstance().getRedisConnection();

					byte[] data = jedis.lpop(QUEUE_NAME.getBytes());
					while (data != null) {
						data = jedis.lpop(QUEUE_NAME.getBytes());
					}
					System.out.println("queue empty........");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (jedis != null)
						jedis.close();
					System.out.println("----------------Ket thuc tien trinh xu ly--------------------------");
				}
			}
		}, new Date(), 30000);
	}

	/**
	 * Gui message canh bao qua tele
	 * 
	 * @param message
	 */
	public static void composeAlertToTele(String message) {
		try {
			System.out.println("Du lieu gui di:" + message);
			String url = "https://api.telegram.org/bot973875366:AAGaP3YMEQepGDpqT0IlSwRvE6LS_Iga9x0/sendMessage";
			HttpPost post = new HttpPost(url);
			post.addHeader("Content-type", "application/x-www-form-urlencoded");
			post.addHeader("charset", "UTF-8");// add request parameter, form parameters
			List<NameValuePair> urlParameters = new ArrayList<>();
			urlParameters.add(new BasicNameValuePair("chat_id", "-419853704"));
			urlParameters.add(new BasicNameValuePair("text", message));
			// urlParameters.add(new BasicNameValuePair("custom", "secret"));

			post.setEntity(new UrlEncodedFormEntity(urlParameters, "utf-8"));
			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse response = httpClient.execute(post)) {

				System.out.println(EntityUtils.toString(response.getEntity()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void composeAlertToTele2(String message_) {
		try {

			URL url = new URL("https://api.telegram.org/bot973875366:AAGaP3YMEQepGDpqT0IlSwRvE6LS_Iga9x0/sendMessage");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
//			    conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
//			    conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
			conn.setRequestProperty("Content-Type", "application/json");

			String message = "{\"chat_id\":\"-419853704\",\"text\":" + message_ + "}";

			OutputStream os = conn.getOutputStream();
			os.write(message.getBytes());
			os.flush();
			os.close();

			int statusCode = conn.getResponseCode();
			System.out.println("Response from Telegram Gateway: \n");
			System.out.println("Status Code: " + statusCode);
			BufferedReader br = new BufferedReader(
					new InputStreamReader((statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
