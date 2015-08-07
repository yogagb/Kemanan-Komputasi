//Code Made By Yoga Gunawan Budijono  - 1305001451 - Teknik Informatika 2013
//Client
//import library yang dibutuhkan pada koding dibawah
import java.net.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

class DesEncrypter {
  Cipher ecipher;

 //menginisialisasi key sebagai kunci untuk enkripsi data menggunakan algoritma DES
  DesEncrypter(SecretKey key) throws Exception {
    ecipher = Cipher.getInstance("DES");
    ecipher.init(Cipher.ENCRYPT_MODE, key);
  }

  public String encrypt(String str) throws Exception {
    //Enkripsi Chiper Menggunakan utf-8
    byte[] utf8 = str.getBytes("UTF8");
    //Enkripsi 
    byte[] enc = ecipher.doFinal(utf8);

    //Enkripsi byte menjadi daya String menggunakan base 64
	return Base64.getEncoder().encodeToString(enc);
  }
  
}


public class  Client
{
	public static void main(String args[]) throws Exception
	{
		//membuat soket baru dengan port 2000 dan jaringan lokal
		Socket sk=new Socket("localhost",2000);
		BufferedReader sin=new BufferedReader(new InputStreamReader(sk.getInputStream()));
		
		//stream pertama untuk menampung message client
		PrintStream sout=new PrintStream(sk.getOutputStream());
		//untuk input data String kedalam stream menggunakan input dari user
		BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	
		//stream kedua untuk mengirim key yang di generate agar tidak tercampur
		PrintStream sout1=new PrintStream(sk.getOutputStream());	
		
		
		String s;
		
		while ( true )
		{
			//client menggenerate key setiap kali mengirim pesan
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			//membuat key sebagai patokan untuk Enksripsi algoritma DES
			DesEncrypter encrypter = new DesEncrypter(key);
			
			//input pesan
		    System.out.print("Client says  : ");			
			s=stdin.readLine();
			
			//enktipsi pesan
			String enkrip = encrypter.encrypt(s);
			sout.println(enkrip);
			
			System.out.println("Original Key : "+key);
			
			//mengubah key menjadi bentuk String dengan base64
			String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
			System.out.println("Encoded Key  : " + encodedKey +"\n");
			
			//mengirim key kepada server menggunakan soket sout1
			sout1.println(encodedKey);
			
		}
	}
}

