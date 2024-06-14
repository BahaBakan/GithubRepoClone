/** 
* 
* @author Muhammed Baha Bakan baha.bakan@ogr.sakarya.edu.tr
* @since 05.04.2024 
* <p> 
*  Kullanıcıdan URL alıp klonlama işlemlerini yaptığım sınıf
* </p> 
*/ 


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class URLAlVeRepoKlonla {
	//Burada kullanıcıdan bir depo url adresi aldım. .trim ile boşlukların temizlenmesini sağladım
	public static String kullanıcıdanRepoUrlAl() {
        String url = "";
        try {
            BufferedReader okuyucu = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("GitHub Depo URLsini giriniz:");
            url = okuyucu.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
//Burada klonlama işlemlerini sağladım. Kullanıcının ana dizininde bir git klasörüne klonladım.
    public static File repoKlonla(String url) {
        File klonlanmışRepo = null;
        try {
            
            String kullanıcıAnaDizini = System.getProperty("user.home");
            File gitKlasörü = new File(kullanıcıAnaDizini, "klonDosyası");
            if (!gitKlasörü.exists()) {
                gitKlasörü.mkdir();
            }

            
            Process işlem = Runtime.getRuntime().exec("git clone " + url, null, gitKlasörü);
            int çıkışKodu = işlem.waitFor();
            if (çıkışKodu == 0) {
                System.out.println("Depo başarıyla klonlandı.");
                
                File[] klasörler = gitKlasörü.listFiles();
                if (klasörler != null) {
                    for (File klasör : klasörler) {
                        if (klasör.isDirectory()) {
                            klonlanmışRepo = klasör;
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Depo klonlanırken hata oluştu.Lütfen URL'nin doğruluğunu kontrol edin.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return klonlanmışRepo;
    }
}

