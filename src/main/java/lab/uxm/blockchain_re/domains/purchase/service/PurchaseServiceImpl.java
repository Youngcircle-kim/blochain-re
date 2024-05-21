package lab.uxm.blockchain_re.domains.purchase.service;

import lab.uxm.blockchain_re.domains.music.repository.MusicRepository;
import lab.uxm.blockchain_re.domains.purchase.repository.PurchaseRepository;
import lab.uxm.blockchain_re.domains.user.repository.UserRepository;
import lab.uxm.blockchain_re.provider.Web3jContractProvider;
import lab.uxm.blockchain_re.utils.IPFSUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService{
  private final IPFSUtil ipfsUtil;
  private final PurchaseRepository purchaseRepository;
  private final MusicRepository musicRepository;
  private final UserRepository userRepository;
  private final Web3jContractProvider web3jContractProvider;
}
