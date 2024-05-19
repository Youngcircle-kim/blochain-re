package lab.uxm.blockchain_re.provider;

import lab.uxm.blockchain_re.config.Web3jConfig;
import lab.uxm.blockchain_re.contract.NFT1155;
import lab.uxm.blockchain_re.contract.SettlementContractExtra;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;

@Component
public class Web3jContractProvider {
  public SettlementContractExtra settlementContractExtra(String contractAddress, Web3j web3j, Web3jConfig web3jConfig){
    return SettlementContractExtra.load(contractAddress, web3j, web3jConfig.credentials(), web3jConfig.gasProvider());
  }

  public NFT1155 nft1155(String contractAddress, Web3j web3j, Web3jConfig web3jConfig){
    return NFT1155.load(contractAddress, web3j, web3jConfig.credentials(), web3jConfig.gasProvider());
  }
}
