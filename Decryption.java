package digi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Decryption {
	
private static Cipher cipherb;
	
	public Decryption() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.cipherb = Cipher.getInstance("RSA");
	}
	
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
	public void decryptFile(byte[] input, File output, PrivateKey key) throws IOException, GeneralSecurityException {
		this.cipherb.init(Cipher.DECRYPT_MODE, key);
		writeToFile(output, this.cipherb.doFinal(input));
		System.out.println("input"+input);
    }
	private void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException{
		FileOutputStream fos = new FileOutputStream(output);
		fos.write(toWrite);
		 System.out.println("Decrypted file is " + output);
		fos.flush();
		fos.close();

}
	public byte[] getFileInBytes(File f) throws IOException{
		FileInputStream fis = new FileInputStream(f);
		byte[] fbytes = new byte[(int) f.length()];
		fis.read(fbytes);
		String s=new String(fbytes);
		System.out.println("byte code is"+ s);
		fis.close();
		return fbytes;
	}
	public static void main(String[] args) throws Exception {
		
		Decryption d= new Decryption();
		PrivateKey privateKey = d.getPrivate("KeyPair/privateKey");
	
	if(new File("KeyPair/output_encrypted.txt").exists()){
		
		d.decryptFile(d.getFileInBytes(new File("KeyPair/output_encrypted.txt")), new File("KeyPair/output_decrypted.txt"), privateKey);
		System.out.println("Decrypted Sucessfully");
		
		d.decryptFile(d.getFileInBytes(new File("KeyPair/output_encrypted.txt")), new File("KeyPair/output1_decrypted.txt"), privateKey);
		System.out.println("Decrypted Sucessfully");
}}}

