
/** 
* 
* @author Muhammed Baha Bakan baha.bakan@ogr.sakarya.edu.tr
* @since 05.04.2024 
* <p> 
*  Java dosyalarını bulduğum,incelediğim,kontrolleri sağladığım ve yazdırma işlemini yaptığım sınıf.
* </p> 
*/ 

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaDosyaInceleme {
  
	//Burada klassördeki tüm java dosyalarını bulup list nesnesine eklemeyi sağladım. Bu dosyaları inceliyor.
	public static void klasördekiClassİçerenJavaDosyalarınıİncele(File klasör) {
        List<File> javaDosyaları = new ArrayList<>();
        classİçerenJavaDosyalarınıBul(klasör, javaDosyaları);
        for (File javaDosyası : javaDosyaları) {
            try {
                javaDosyasınıİncele(javaDosyası);
                System.out.println("-----------------------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	//Burada klasördeki tüm dosyaları inceledim.İçinde bir sınıf tanımı varsa dosyayı javadosyaları içine ekledim.
    private static void classİçerenJavaDosyalarınıBul(File klasör, List<File> javaDosyaları) {
        File[] dosyalar = klasör.listFiles();
        if (dosyalar != null) {
            for (File dosya : dosyalar) {
                if (dosya.isDirectory()) {
                    classİçerenJavaDosyalarınıBul(dosya, javaDosyaları);
                } else if (dosya.getName().endsWith(".java") && içindeClassVarMi(dosya)) {
                    javaDosyaları.add(dosya);
                }
            }
        }
    }
    //İçinde sınıf varsa true döndürmeyi sağladım.
    private static boolean içindeClassVarMi(File dosya) {
        try (BufferedReader okuyucu = new BufferedReader(new java.io.FileReader(dosya))) {
            String satır;
            while ((satır = okuyucu.readLine()) != null) {
                if (satır.contains("class ")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int diğerYorumSatırSayısınıHesapla(File dosya) throws IOException {
        BufferedReader okuyucu = new BufferedReader(new java.io.FileReader(dosya));
        String satır;
        int diğerYorumSatırSayısı = 0;
        boolean çokSatırlıYorum = false; 

        while ((satır = okuyucu.readLine()) != null) {
            satır = satır.trim();
            if (satır.startsWith("//")) {
                diğerYorumSatırSayısı++;
            } else if (satır.startsWith("/*")) {
                if (!satır.contains("*/")) {
                    çokSatırlıYorum = true; 
                } else {
                    
                    if (!satır.endsWith("*/")) {
                        diğerYorumSatırSayısı++;
                    }
                }
                if (!satır.startsWith("/**")) {
                    while ((satır = okuyucu.readLine()) != null) {
                        if (satır.contains("*/")) {
                            çokSatırlıYorum = false; 
                            break;
                        }
                        diğerYorumSatırSayısı++;
                    }
                }
            } else if (satır.contains("//")) {
                
                int index = satır.indexOf("//");
                if (index > 0) {
                    diğerYorumSatırSayısı++;
                }
            }
        }
        okuyucu.close();
        return diğerYorumSatırSayısı;
    }

    private static void javaDosyasınıİncele(File dosya) throws IOException {
       
        BufferedReader okuyucu = new BufferedReader(new java.io.FileReader(dosya));
        String satır;
        int javadocSatırSayısı = 0;
        int kodSatırSayısı = 0;
        int loc = 0;
        int fonksiyonSayısı = 0;
        boolean javadocIçinde = false; 

        
        int diğerYorumSatırSayısı = diğerYorumSatırSayısınıHesapla(dosya);

        while ((satır = okuyucu.readLine()) != null) {
            loc++;
            satır = satır.trim();
            if (satır.startsWith("//")) {
                if (javadocIçinde) {
                    javadocSatırSayısı++;
                }
            } else if (satır.startsWith("/*")) {
                if (satır.startsWith("/**")) {
                    javadocIçinde = true; 
                }
                if (!satır.contains("*/")) {
                    while ((satır = okuyucu.readLine()) != null) {
                        loc++;
                        if (satır.contains("*/")) {
                            if (javadocIçinde) {
                                javadocIçinde = false; 
                            }
                            break;
                        }
                        if (javadocIçinde) {
                            javadocSatırSayısı++;
                        }
                    }
                }
            } else if (!satır.trim().isEmpty()) {
                
                kodSatırSayısı++;
                
                if (!javadocIçinde && (satır.contains("(") && satır.contains(")") && !satır.contains(";") && !satır.contains("="))) {
                    
                    fonksiyonSayısı++;
                }
            }
        }
        okuyucu.close();

        double doubleJavadoc= (double)(int)javadocSatırSayısı; 
        double doubleDiğerYorum= (double)(int)diğerYorumSatırSayısı;
        double doubleFonksiyon= (double)(int)fonksiyonSayısı;
        double doubleKod= (double)(int)kodSatırSayısı;

        double yg=((doubleJavadoc+doubleDiğerYorum)*(0.8))/doubleFonksiyon; 
        double yh=(doubleKod/doubleFonksiyon)*(0.3); 

        double yorumSapmaYüzdesi=((100*yg)/yh)-100;


        
        System.out.println("Sınıf: " + dosya.getName());
        System.out.println("Javadoc Satır Sayısı: " + javadocSatırSayısı);
        System.out.println("Yorum Satır Sayısı: " + diğerYorumSatırSayısı);
        System.out.println("Kod Satır Sayısı: " + kodSatırSayısı);
        System.out.println("LOC: " + loc);
        System.out.println("Fonksiyon Sayısı: " + fonksiyonSayısı);
        System.out.println("Yorum Sapma Yüzdesi: %" + yorumSapmaYüzdesi);
    }
}

