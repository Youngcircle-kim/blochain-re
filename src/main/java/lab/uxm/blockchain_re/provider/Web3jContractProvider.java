package lab.uxm.blockchain_re.provider;

import java.io.IOException;
import lab.uxm.blockchain_re.config.Web3jConfig;
import lab.uxm.blockchain_re.contract.NFT1155;
import lab.uxm.blockchain_re.contract.SettlementContractExtra;
import lab.uxm.blockchain_re.contract.message.Web3jExceptionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.webjars.NotFoundException;

@Slf4j
@RequiredArgsConstructor
@Component
public class Web3jContractProvider {
  private final Web3j web3j;
  private  final Web3jConfig web3jConfig;
  public SettlementContractExtra settlementContractExtra(String contractAddress){
    return SettlementContractExtra.load(contractAddress, this.web3j, this.web3jConfig.credentials(), this.web3jConfig.gasProvider());
  }

  public NFT1155 nft1155(String contractAddress){
    return NFT1155.load(contractAddress, this.web3j, this.web3jConfig.credentials(), this.web3jConfig.gasProvider());
  }

  public TransactionReceipt getTransactionReceiptByHash(String hash) throws IOException {
    return web3j
        .ethGetTransactionReceipt(hash)
        .send()
        .getTransactionReceipt()
        .orElseThrow(
            () -> new NotFoundException(Web3jExceptionMessage.NOT_FOUND_RECEIPT)
        );
  }
}
