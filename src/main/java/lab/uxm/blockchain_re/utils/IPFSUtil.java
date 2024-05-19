package lab.uxm.blockchain_re.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multihash.Multihash;
import java.io.IOException;
import lab.uxm.blockchain_re.domains.music.model.MusicMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * [This class provide method about IPFS]
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class IPFSUtil {
  @Value("${IPFS.ENC_KEY}")
  private String key;

  @Value("${IPFS.ENC_IV}")
  private String iv;
  private final ObjectMapper objectMapper;
  private final IPFS ipfs;
  /**
   * Retrieve file content on IPFS
   * @Param cid1
   * @Return fileContent byte[]
   * */
  public byte[] retrieveFile(String cid) throws IOException {
    Multihash multihash = Multihash.fromBase58(cid);
    return ipfs.cat(multihash);
  }

  /**
   * Add Image file on IPFS
   * @Param originalName
   * @Param imgBuffer
   * @Return cid String
   * */
  public String addImageFileOnIPFS(
      String originalName,
      byte[] imgBuffer
  ) throws IOException {
    NamedStreamable.ByteArrayWrapper file = new ByteArrayWrapper(originalName, imgBuffer);
    MerkleNode merkleNode = ipfs.add(file).get(0);
    return merkleNode.hash.toBase58();
  }

  /**
   * Add metadata file on IPFS network
   * @Param MusicMetadata
   * @Return cid String
   * */
  public String addMetadataFileOnIPFS(MusicMetadata musicMetadata) throws IOException {
    String metadata = objectMapper.writeValueAsString(musicMetadata);
    NamedStreamable.ByteArrayWrapper file = new ByteArrayWrapper(metadata.getBytes());
    MerkleNode metadataCid = ipfs.add(file).get(0);
    return metadataCid.hash.toBase58();
  }
  /**
   * Retrieve
   *
   * */
}
