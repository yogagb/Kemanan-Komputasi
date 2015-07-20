//Code Made By Yoga Gunawan Budijono  - 1305001451 - Teknik Informatika 2013
//Server
//import library yang dibutuhkan pada koding dibawah
import java.net.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

class DesDecrypter {
	Cipher dcipher;
  
 //menginisialisasi key sebagai kunci untuk decode data menggunakan algoritma DES
  DesDecrypter(SecretKey key) throws Exception {
    dcipher = Cipher.getInstance("DES");
    dcipher.init(Cipher.DECRYPT_MODE, key);
  }

  public String decrypt(String str) throws Exception {
    //Decode string menjadi daya byte menggunakan base 64
	byte[] dec = Base64.getDecoder().decode(str);

	//Decode Chiper Menggunakan utf-8
    byte[] utf8 = dcipher.doFinal(dec);

    return new String(utf8, "UTF8"); 			
  }
}



public class  Server
{
	public static void main(String args[]) throws Exception
	{
		//membuat soket baru dengan port 2000 dan jaringan lokal
		ServerSocket ss=new ServerSocket(2000);
		//menerima soket dari client
		Socket sk=ss.accept();
		
		//membaca pesan yang masuk dalam soket
		BufferedReader cin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		
		//string S untuk menyimpan pesan dari Client, K untuk menyimpan Key dari Client
		String s, k;     
			
		while (  true )
		{
			//membaca pesan dari soket dan menyimpan pesan pada string S
			s=cin.readLine();
			System. out.print("Client says      : "+s+"\n");
			
			//membaca key dari soket dan menyimpan pesan pada string k
			k=cin.readLine();
			System. out.print("Encoded Key      : "+k+"\n");
			
			//mengubah key dari String menjadi bentuk Byte dengan base64
			byte[] decodedKey = Base64.getDecoder().decode(k);
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES"); 
			System.out.println("Original Key     : "+originalKey);
			
			//mendekripsi pesan menggunakan Key yang sudah diterjemahkan menggunakan algoritma DES
			DesDecrypter decrypter = new DesDecrypter(originalKey);
			String decrypted = decrypter.decrypt(s);
			System. out.print("Client decrypted : "+decrypted+"\n\n");
		}		
 
	}
}