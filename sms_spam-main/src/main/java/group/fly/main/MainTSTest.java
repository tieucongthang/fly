package group.fly.main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.w3c.dom.events.Event;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.ens.Contracts;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;

//import org.tensorflow.types.TInt32;
public class MainTSTest {
	public static void main(String[] args) {
//		System.out.println("Hello TensorFlow " + TensorFlow.version());
		DefaultBlockParameter detaul = null;
//		TetherToken token;

		try {
			Web3j web3 = Web3j.build(new HttpService("https://bsc-dataseed1.binance.org:443"));
//			LogCounter logCounter = new LogCounter();
			TypeEncoder.encode(new Utf8String("abac"));
			Contracts ctrAddress;
			int value = 0;
//			Event event = new Event("Notify",Arrays.asList(new TypeReference<Uint>(true) {},new TypeReference<Address>() {}));
//			detaul = DefaultBlockParameter.valueOf(new BigInteger(value));
//			Event event = new Event("NewId", 
//		            Arrays.<TypeReference<?>>asList(),
//		            Arrays.asList(null));
			java.util.List<TypeReference<?>> list1 = Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
			}, new TypeReference<Address>() {
			});
			java.util.List<TypeReference<?>> list2 = Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
			});
			Event event = new Event("Transfer", list1, list2);
			long timeout = 10;
//			EthGetBalance response = balance.sendAsync().get(timeout, TimeUnit.SECONDS);
//			System.out.println(" The Block Number is: " + response.getBalance().toString());
//			
			EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST,
					"0x55d398326f99059fF775485246999027B3197955");
//			web3.ethLogObservable(filter).subscribe((Action1<? super Log>) logCounter);
//			filter.addSingleTopic("0x" + TypeEncoder.encode(new Utf8String("0x179664473f11e064c1fe250e706d35b64d0f6119")));
//			String optTopicAddress = "0x" + TypeEncoder.encode(addresseScribe);
			System.out.println(TypeEncoder.encode(new Utf8String("0x179664473f11e064c1fe250e706d35b64d0f6119")));
//		web3.transactionObservable()
//			filter.addOptionalTopics(null, null,TypeEncoder.encode(new Utf8String("0x179664473f11e064c1fe250e706d35b64d0f6119")));
//			filter.addSingleTopic(EventEncoder.encode(event));
			web3.ethLogObservable(filter).subscribe(log -> {
//				System.out.println("Event received");
				System.out.println(log);
//				java.util.List<Type> argsT = org.web3j.abi.FunctionReturnDecoder.decode(
//			              log.getData(), event.getIndexedParameters());
//				for (Object e : argsT)
//					System.out.println(e.toString());
				String eventHash = log.getTopics().get(0); // Index 0 is the event definition hash

				EventValues eventValues = staticExtractEventParameters(event, log);
				if (eventValues != null) {
					
					String from = (String) eventValues.getIndexedValues().get(0).getValue();
					String to = (String) eventValues.getIndexedValues().get(1).getValue();
					BigInteger _value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
					if (from.equals("0x179664473f11e064c1fe250e706d35b64d0f6119")
							|| to.equals("0x179664473f11e064c1fe250e706d35b64d0f6119"))
					{
						System.out.println("Event received");
						System.out.println(from + ":" + to + ":" + _value);
					}
				}
//			    edValue(log.getTopics().get(3), new TypeReference<Uint8>() {});
//			?    if(eventHash.equals(MY_EVENT_HASH)) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH) 

				// address indexed _arg1

			}, error -> {
				System.out.println("Error: " + error);
			});

//			
//			Event targetEventTopic = new Event("Withdraw", Arrays.asList(
//	                
//	                new TypeReference<Address>() {},
//	                new TypeReference<Uint256>() {},
//	                new TypeReference<Uint256>() {}
//	        ));
//			web3.ethLogObservable(filter).subscribe(new Action1<Log>() {
//			        @Override    
//			        public void call(Log log) {
//			            System.out.println("log.toString(): " +  log.toString());
//			        }
//			    });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	        System.out.println(" The Block Number is: " + result.getBlockNumber().toString());
	}

	public static EventValues staticExtractEventParameters(Event event, Log log) {
		final List<String> topics = log.getTopics();
		String encodedEventSignature = EventEncoder.encode(event);
	
		if (topics == null || topics.size() == 0 || !topics.get(0).equals(encodedEventSignature)) {
			return null;
		}
		String transactionHash = log.getTransactionHash();
//		System.out.println(FunctionReturnDecoder.detransactionHash);
		List indexedValues = new ArrayList<>();
		java.util.List<Type> nonIndexedValues = FunctionReturnDecoder.decode(log.getData(),
				event.getNonIndexedParameters());
		List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
//		for (int j = 0; j < nonIndexedValues.size(); j++) {
//			Type value = nonIndexedValues.get(j);
//			System.out.println("nonIndexedValues " + j + ": " + value.toString());
//		}
		for (int i = 0; i < indexedParameters.size(); i++) {
			Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), indexedParameters.get(i));
			indexedValues.add(value);
//			System.out.println("value " + i + ": " + value.toString());

		}
		return new EventValues(indexedValues, nonIndexedValues);
	}
}
