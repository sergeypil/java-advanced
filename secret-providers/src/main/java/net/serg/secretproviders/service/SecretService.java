package net.serg.secretproviders.service;

import lombok.RequiredArgsConstructor;
import net.serg.secretproviders.dto.SecretRequestDto;
import net.serg.secretproviders.entity.Secret;
import net.serg.secretproviders.repository.SecretRepository;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SecretService {

    private final SecretRepository secretRepository;

    private static final String SECRET_KEY = "mySuperSecretKey"; // it should be retrieved from secure source
    private static final String ALGORITHM = "AES";
    
    public String getSecretByUniqueId(String id) {
        Secret secret = secretRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Secret not found"));
        secretRepository.deleteById(id);
        return decrypt(secret.getSecretText());
    }

    public String saveSecret(SecretRequestDto secretRequest) {
        String uniqueId = UUID
            .randomUUID()
            .toString();
        Secret secret = new Secret();
        secret.setId(uniqueId);
        secret.setSecretText(encrypt(secretRequest.getSecretText()));
        secretRepository.save(secret);
        return uniqueId;
    }

    private String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64
                .getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting: " + e.getMessage());
        }
    }

    private String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            final SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            return decryptedString;
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting: " + e.getMessage());
        }
    }
}