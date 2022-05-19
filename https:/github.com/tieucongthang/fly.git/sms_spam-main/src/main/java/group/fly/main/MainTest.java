package group.fly.main;


import java.util.concurrent.TimeUnit;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;




public class MainTest {
	public static void main(String[] args) {
		DefaultBlockParameter detaul = null;

		try {
			Web3j web3 = Web3j.build(new HttpService("https://bsc-dataseed1.binance.org:443"));
			int value = 0;
//			detaul = DefaultBlockParameter.valueOf(new BigInteger(value));
			Request<?, EthGetBalance> balance = web3.ethGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1", DefaultBlockParameter.valueOf("latest"));
//			EthBlockNumber result = web3.ethBlockNumber().sendAsync().get();
			long timeout = 10;
			EthGetBalance response = balance.sendAsync().get(timeout, TimeUnit.SECONDS);
			System.out.println(" The Block Number is: " + response.getBalance().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
