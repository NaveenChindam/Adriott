package digi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Akeys {
	
private KeyPairGenerator keyGena;
private KeyPair paira;
private PrivateKey pra;
private PublicKey pua;

public Akeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
	this.keyGena = KeyPairGenerator.getInstance("RSA");
	this.keyGena.initialize(keylength);
}

public void createKeys() {
	this.paira = this.keyGena.generateKeyPair();
	this.pra = paira.getPrivate();
	
	this.pua = paira.getPublic();
}

public PrivateKey getPrivateKey() {
	return this.pra;
}

public PublicKey getPublicKey() {
	return this.pua;
}

public void writeToFile1(String path, byte[] key) throws IOException {
	File f = new File(path);
	f.getParentFile().mkdirs();

	FileOutputStream fos = new FileOutputStream(f);
	fos.write(key);
	fos.flush();
	fos.close();
}

public static void main(String[] args) {
	Akeys gk;
	try {
		gk = new Akeys(1024);
		gk.createKeys();
		gk.writeToFile1("KeyPair/publicKey", gk.getPublicKey().getEncoded());
		gk.writeToFile1("KeyPair/privateKey", gk.getPrivateKey().getEncoded());
	} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
		System.err.println(e.getMessage());
	} catch (IOException e) {
		System.err.println(e.getMessage());
	}
System.out.println("keys generated successfully");
}

}
