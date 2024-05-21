package lab.uxm.blockchain_re.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import io.ipfs.multihash.Multihash;
import jakarta.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lab.uxm.blockchain_re.domains.music.model.MusicMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
    return this.ipfs.cat(multihash);
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
    MerkleNode merkleNode = this.ipfs.add(file).get(0);
    return merkleNode.hash.toBase58();
  }
  public String addCopyrightIPFS(String copyright)throws Exception{
    NamedStreamable.ByteArrayWrapper meta = new ByteArrayWrapper(copyright.getBytes());
    MerkleNode cid = this.ipfs.add(meta).get(0);
    return cid.hash.toBase58();
  }
  public String addFileOnIPFS(
      byte[] encryption
  ) throws IOException {
    NamedStreamable.ByteArrayWrapper file = new ByteArrayWrapper(encryption);
    MerkleNode merkleNode = this.ipfs.add(file).get(0);
    return merkleNode.hash.toBase58();
  }
  public String findImageCid(String cid1) throws IOException {
    Multihash multihash = Multihash.fromBase58(cid1);
    byte[] imageByte = this.ipfs.cat(multihash);
    JSONObject songInfo = new JSONObject(new String(imageByte)).getJSONObject("songInfo");
    String imageCid = songInfo.getString("imageCid");

    Multihash imageFilePointer = Multihash.fromBase58(imageCid);
    byte[] fileContents = this.ipfs.cat(imageFilePointer);

    return Base64.getEncoder().encodeToString(fileContents);
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
  public byte[] encryptAES(byte[] gzipped)
      throws Exception {
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
    return cipher.doFinal(gzipped);
  }
  public byte[] compress(byte[] buffer) throws Exception {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
    gzipOutputStream.write(buffer);
    gzipOutputStream.close();
    return byteArrayOutputStream.toByteArray();
  }

  public  String sha1Convert(byte[] buffer) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(buffer);
    byte[] digest = md.digest();
    return DatatypeConverter.printHexBinary(digest).toLowerCase();

  }
}
