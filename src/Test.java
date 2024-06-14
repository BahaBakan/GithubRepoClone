/** 
* 
* @author Muhammed Baha Bakan baha.bakan@ogr.sakarya.edu.tr
* @since 05.04.2024 
* <p> 
*  Test sınıfım. 
* </p> 
*/ 


import java.io.File;

public class Test {
    public static void main(String[] args) {
        String url = URLAlVeRepoKlonla.kullanıcıdanRepoUrlAl();
        File klonlanmışRepo = URLAlVeRepoKlonla.repoKlonla(url);
        if (klonlanmışRepo != null) {
            JavaDosyaInceleme.klasördekiClassİçerenJavaDosyalarınıİncele(klonlanmışRepo);
        } else {
            System.out.println("Klonlanmış depo bulunamadı.");
        }
    }
}