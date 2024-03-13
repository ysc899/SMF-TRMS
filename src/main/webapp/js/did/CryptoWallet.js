const hashAlg = 'SHA256withECDSA';
/**
 * Crypto Wallet
 * @class
 */
var _jsrsasign = KEYUTIL;
var _cryptoJs = CryptoJS;//_interopRequireDefault(require("crypto-js"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
class CryptoWallet {
  async genKeypair() {
    const kp = _jsrsasign.generateKeypair('EC', 'secp256k1');

    const prvKey = kp.prvKeyObj;
    const pubKey = kp.pubKeyObj;
    let prvKeyPEM, pubKeyPEM;
    prvKeyPEM = _jsrsasign.getPEM(prvKey, 'PKCS1PRV');
    pubKeyPEM = _jsrsasign.getPEM(pubKey);
    const keyData = {
      privateKey: prvKeyPEM,
      publicKey: pubKeyPEM
    };
    return keyData;
  }

  async dataSign(textOrFile, pem) {
    let prv;

    try {
      // 1. private key
      prv = _jsrsasign.getKey(pem);
    } catch (error) {
      throw new Error(error);
    } // 2. data to be signed


    const sig = new _jsrsasign.default.KJUR.crypto.Signature({
      alg: hashAlg
    }); // 3. load signature

    sig.init(prv);
    sig.updateString(textOrFile);

    try {
      const sigHex = sig.sign();
      return sigHex;
    } catch (error) {
      throw new Error(error);
    }
  }

  async dataVerify(textOrFile, sigFile, pem) {
    let pub;

    try {
      // 1. public key
      pub = _jsrsasign.getKey(pem);
    } catch (error) {
      throw new Error(error);
    } // 2. data to be verifid


    const sig = new _jsrsasign.default.KJUR.crypto.Signature({
      alg: hashAlg
    }); // 3. load signature

    sig.init(pub);
    sig.updateString(textOrFile);

    try {
      const isValid = sig.verify(sigFile);
      return isValid;
    } catch (error) {
      throw new Error(error);
    }
  }

  async encryptIndexedDB(origintext) {
    try {
      // Encrypt
      const ciphertext = _cryptoJs.default.AES.encrypt(JSON.stringify(origintext), 'secret key 123').toString();

      return new Promise(function (resolve) {
        resolve(ciphertext);
      });
    } catch (error) {
      throw new Error(error);
    }
  }

  async decryptIndexedDB(ciphertext) {
    try {
      // Decrypt
      const bytes = _cryptoJs.default.AES.decrypt(ciphertext, 'secret key 123');

      const originalText = bytes.toString(_cryptoJs.default.enc.Utf8);
      return JSON.parse(originalText);
    } catch (error) {
      throw new Error(error);
    }
  }

}