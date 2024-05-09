package lab.uxm.blockchain_re.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPFSConfig {
  @Value("${IPFS.URI}")
  private String ipfsURI;
  @Bean
  public IPFS IPFS(){
    return new IPFS(ipfsURI);
  }
}
