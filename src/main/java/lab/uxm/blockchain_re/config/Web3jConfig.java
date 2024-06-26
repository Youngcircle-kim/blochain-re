package lab.uxm.blockchain_re.config;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration
public class Web3jConfig {
  @Value("${infura.API_URL}")
  private String INFURA_API_URL;

  @Value("${metamask.PRIVATE_KEY}")
  private String PRIVATE_KEY;

  @Bean
  public Web3j Web3j() {
    return Web3j.build(new HttpService(INFURA_API_URL));
  }

  @Bean
  public Credentials credentials() {
    BigInteger privateKeyInBT = new BigInteger(PRIVATE_KEY, 16);
    return Credentials.create(ECKeyPair.create(privateKeyInBT));
  }
  @Bean
  public StaticGasProvider gasProvider(){
    BigInteger gasPrice = Contract.GAS_PRICE;
    BigInteger gasLimit = Contract.GAS_LIMIT;
    return new StaticGasProvider(gasPrice, gasLimit);
  }
}
