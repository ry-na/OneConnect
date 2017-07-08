package localhost.android.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Random;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

/** Android Keystore Sample for API Level 18 */
/* http://qiita.com/Koganes/items/e8253f13ecb534ca11a1*/
public class AndroidKeyStoreManager {
    static private AndroidKeyStoreManager sInstance;
    static private final String KEY_STORE_ALIAS = "oneconnect_android"; // Change me

    static private final String KEY_STORE_ALGORITHM = "RSA";
    static private final String KEY_PROVIDER = "AndroidKeyStore";
    static private final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    private Context mContext;
    private KeyStore mKeyStore;

    public AndroidKeyStoreManager(Context context) {
        this.mContext = context;
        try {
            mKeyStore = KeyStore.getInstance(KEY_PROVIDER);
            mKeyStore.load(null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("I/O Exception");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Security Exception");
        }
    }

    static public AndroidKeyStoreManager getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new AndroidKeyStoreManager(context);
        } else {
            sInstance.mContext = context;
        }
        return sInstance;
    }


    public PublicKey getPublicKey() {
        try {
            if (mKeyStore.containsAlias(KEY_STORE_ALIAS)) {
                return mKeyStore.getCertificate(KEY_STORE_ALIAS).getPublicKey();
            } else {
                return createKeyPair().getPublic();
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable Public Key.");
        }
    }

    public PrivateKey getPrivateKey() {
        try {
            if (mKeyStore.containsAlias(KEY_STORE_ALIAS)) {
                return (PrivateKey) mKeyStore.getKey(KEY_STORE_ALIAS, null);
            } else {
                return createKeyPair().getPrivate();
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable Private Key.");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private KeyPair createKeyPair() {
        KeyPairGenerator kpg = null;

        try {
            kpg = KeyPairGenerator.getInstance(KEY_STORE_ALGORITHM, KEY_PROVIDER);
            kpg.initialize(createKeyPairGeneratorSpec());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }

        return kpg.generateKeyPair();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private KeyPairGeneratorSpec createKeyPairGeneratorSpec() {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 100);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(mContext)
                .setAlias(KEY_STORE_ALIAS)
                .setSubject(new X500Principal(String.format("CN=%s", KEY_STORE_ALIAS)))
                .setSerialNumber(BigInteger.valueOf(100000))
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();

        return spec;
    }

    public byte[] encrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());

            return cipher.doFinal(bytes);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Encryption failed.");
        }
    }

    public byte[] decrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

            return cipher.doFinal(bytes);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Decryption failed.");
        }
    }
}
