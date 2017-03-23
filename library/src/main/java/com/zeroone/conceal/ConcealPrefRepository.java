package com.zeroone.conceal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.facebook.android.crypto.keychain.AndroidConceal;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.CryptoConfig;
import com.facebook.crypto.Entity;
import com.facebook.crypto.keychain.KeyChain;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author : hafiq on 23/03/2017.
 */

public class ConcealPrefRepository {

    private Context mContext;
    private CryptoConfig mKeyChain = CryptoConfig.KEY_256;
    private CryptoConfig mConfig = CryptoConfig.KEY_256;
    private String mPrefname;
    private boolean mEnabledCrypto = false;
    private boolean mEnableCryptKey = false;
    private String mEntityPasswordRaw = null;
    private Entity mEntityPassword = null;
    private static SharedPreferences sharedPreferences;
    private Crypto crypto;
    SharedPreferences.Editor editor;

    static ConcealCrypto concealCrypto;

    @SuppressLint("CommitPrefEdits")
    ConcealPrefRepository(PreferencesBuilder builder){
        mContext = builder.mContext;
        mKeyChain = builder.mKeyChain;
        mConfig = builder.mConfig;
        mPrefname = builder.mPrefname;
        mEnabledCrypto = builder.mEnabledCrypto;
        mEnableCryptKey = builder.mEnableCryptKey;
        sharedPreferences = builder.sharedPreferences;
        crypto = builder.crypto;
        mEntityPasswordRaw = builder.mEntityPasswordRaw;
        mEntityPassword = builder.mEntityPassword;


        //init editor
        editor = sharedPreferences.edit();

        //init crypto
        concealCrypto = new ConcealCrypto(crypto,mEntityPassword);
        concealCrypto.setEnableCrypto(mEnabledCrypto);
        concealCrypto.setEnableKeyCrypt(mEnableCryptKey);
    }




















    /* Save Data */

    public void putString(String key, String value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(value)).apply();
    }

    public void putInt(String key, int value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Integer.toString(value))).apply();
    }

    public void putLong(String key, long value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Long.toString(value))).apply();
    }

    public void putDouble(String key, double value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Double.toString(value))).apply();
    }

    public void putFloat(String key, float value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Float.toString(value))).apply();
    }

    public void putBoolean(String key, boolean value) {
        editor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Boolean.toString(value))).apply();
    }


    /* Fetch Data */

    public String getString(String key){
        return concealCrypto.deObscure(sharedPreferences.getString(concealCrypto.hashKey(key),null));
    }

    public String getString(String key,String def){
        return concealCrypto.deObscure(sharedPreferences.getString(concealCrypto.hashKey(key),def));
    }

    public Integer getInt(String key){
        try {
            String value = getString(key);
            if (value == null)
                return -99;

            return Integer.parseInt(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return -99;
        }
    }

    public Integer getInt(String key,int def){
        try {
            String value = getString(key);

            if (value == null)
                return def;

            return Integer.parseInt(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return -99;
        }
    }

    public Float getFloat(String key){
        try {
            String value = getString(key);
            if (value == null)
                return 0f;

            return Float.parseFloat(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0f;
        }
    }

    public Float getFloat(String key,float def){
        try {
            String value = getString(key);

            if (value == null)
                return def;

            return Float.parseFloat(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return def;
        }
    }

    public Double getDouble(String key){
        try {
            String value = getString(key);
            if (value == null)
                return 0D;

            return Double.parseDouble(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0D;
        }
    }

    public Double getDouble(String key,double def){
        try {
            String value = getString(key);

            if (value == null)
                return def;

            return Double.parseDouble(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return def;
        }
    }


    public Long getLong(String key){
        try {
            String value = getString(key);
            if (value == null)
                return 0L;

            return Long.parseLong(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0L;
        }
    }

    public Long getLong(String key,long def){
        try {
            String value = getString(key);

            if (value == null)
                return def;

            return Long.parseLong(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return def;
        }
    }


    public Boolean getBoolean(String key){
        try {
            String value = getString(key);
            return value != null && Boolean.parseBoolean(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Boolean getBoolean(String key,boolean def){
        try {
            String value = getString(key);
            if (value == null)
                return def;

            return Boolean.parseBoolean(value);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static final class Editor {

        private SharedPreferences.Editor mEditor;

        @SuppressLint("CommitPrefEdits")
        public Editor() {
            mEditor = sharedPreferences.edit();
        }

        public Editor putString(String key, String value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(value));
            return this;
        }

        public Editor putInt(String key, int value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Integer.toString(value)));
            return this;
        }

        public Editor putLong(String key, long value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Long.toString(value)));
            return this;
        }

        public Editor putDouble(String key, double value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Double.toString(value)));
            return this;
        }

        public Editor putFloat(String key, float value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Float.toString(value)));
            return this;
        }

        public Editor putBoolean(String key, boolean value) {
            mEditor.putString(concealCrypto.hashKey(key), concealCrypto.obscure(Boolean.toString(value)));
            return this;
        }

        public Editor putListString(String key, List<String> value){
            return this;
        }

        public Editor remove(String key) {
            mEditor.remove(concealCrypto.hashKey(key));
            return this;
        }

        public Editor clear() {
            mEditor.clear();
            return this;
        }

        public boolean commit() {
            return mEditor.commit();
        }


        public void apply() {
            mEditor.apply();
        }
    }

    public static class PreferencesBuilder{

        private Context mContext;
        private CryptoConfig mKeyChain = CryptoConfig.KEY_256;
        private CryptoConfig mConfig = CryptoConfig.KEY_256;
        private String mPrefname;
        private boolean mEnabledCrypto = false;
        private boolean mEnableCryptKey = false;
        private String mEntityPasswordRaw = null;
        private Entity mEntityPassword = null;
        private SharedPreferences sharedPreferences;
        private Crypto crypto;

        public PreferencesBuilder(Context context) {
            mContext = context;
        }

        public PreferencesBuilder useDefaultPrefStorage(){
            return this;
        }

        public PreferencesBuilder useThisPrefStorage(String prefName){
            mPrefname = prefName;
            return this;
        }

        public PreferencesBuilder enableCrypto(boolean enabled,boolean cryptKey){
            mEnabledCrypto = enabled;
            mEnableCryptKey = cryptKey;
            return this;
        }

        public PreferencesBuilder SharedPrefsBackedKeyChain(CryptoConfig keyChain){
            mKeyChain = keyChain;
            return this;
        }

        public PreferencesBuilder createCryptoBits(CryptoConfig config){
            mConfig = config;
            return this;
        }

        public PreferencesBuilder createPassword(String password){
            mEntityPasswordRaw = password;
            return this;
        }

        public ConcealPrefRepository create(){

            if (mPrefname!=null){
                sharedPreferences = mContext.getSharedPreferences(mPrefname, MODE_PRIVATE);
            }
            else{
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            }

            if (mEnabledCrypto) {

                if (mEntityPassword == null){
                    mEntityPasswordRaw = BuildConfig.APPLICATION_ID;
                }

                mEntityPasswordRaw = new String(Base64.encode(mEntityPasswordRaw.getBytes(),Base64.DEFAULT));
                mEntityPassword = Entity.create(mEntityPasswordRaw);

                if (mKeyChain == null) {
                    mKeyChain = CryptoConfig.KEY_256;
                }

                KeyChain makeChain = new SharedPrefsBackedKeyChain(mContext, mKeyChain);

                if (mConfig == null) {
                    crypto = AndroidConceal.get().createDefaultCrypto(makeChain);
                } else if (mConfig == CryptoConfig.KEY_128) {
                    crypto = AndroidConceal.get().createCrypto128Bits(makeChain);
                } else {
                    crypto = AndroidConceal.get().createCrypto256Bits(makeChain);
                }
            }
            else {
                mEnabledCrypto = false;
                mEnableCryptKey = false;
            }

            return new ConcealPrefRepository(this);

        }
    }

}
