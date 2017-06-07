package digi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encrypt {
	
	private static Cipher ciphera;
	
	public Encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException{
		this.ciphera = Cipher.getInstance("RSA");
	}
	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	public void encryptFile(byte[] input, File output, PublicKey key) throws IOException, GeneralSecurityException {
		this.ciphera.init(Cipher.ENCRYPT_MODE, key);
		writeToFile(output, this.ciphera.doFinal(input));
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
		fis.close();
		String s=new String(fbytes);
		System.out.println("byte code is"+ s);
		return fbytes;
	}
	public static void main(String[] args) throws Exception {
		
		Encrypt e= new Encrypt();
		PublicKey key = e.getPublic("KeyPair/publicKey");
		
		if(new File("KeyPair/text.txt").exists()){
			
		
			e.encryptFile(e.getFileInBytes(new File("KeyPair/output.txt")), new File("KeyPair/output_encrypted.txt"), key);
			
			System.out.println("encryped successfully");
		}

		/*FileOutputStream fos=new FileOutputStream(new File("D:/Desert.jpg"));
		 CipherInputStream cipherIn=new CipherInputStream(new FileInputStream(new File("D:/Desert.jpg")), ciphera);
		  

		 int i;
		 while((i=cipherIn.read())!= -1){
		     fos.write(i);
		 }*/
	}
}
