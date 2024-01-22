# Lab Report
- Bu proje GNU GPL v3 ile lisanslanmıştır.
    - ![Version](https://img.shields.io/badge/version-0.0.1-purple.svg)
    - ![Java](https://img.shields.io/badge/Java-17.0.6-orange)
    - ![Node](https://img.shields.io/badge/Node-18.16-green)


___________


- ## İnceleyebileceğiniz Başlıklar

    1. [Proje Yapısı](#Proje-Hakkında)
    2. [Gereksinimler](#Gereksinimler)
    3. [Kurulumlar](#Kurulumlar)
        - [Backend](#Backend)
            - [Manuel Kurulum](#Manuel-Kurulum)
            - [Docker ile Kurulum](#Docker-ile-Kurulum)
        - [React App](#React-App)
    4. [Yazılımcı Notları](#Yazılımcı-Notları)
    4. [Katkıda Bulunanlar](#Katkıda-Bulunanlar)



_________________________


## Proje Hakkında:

- Proje bir labaratuvar rapor ekleme,silme, güncelleme ve görüntüleme üzerine kurulmuş basit bir CRUD uygulaması denebilir.
- Projede iki farklı kullanıcı rolü mevcuttur bunlardan birisi Laborant (Technician) ve Yönetici (Manager) dır.
- Hastalar dışarıdan kendi TC kimlik numaraları ile birlikte Report Inquiry kısmından raporlarını sorgulayıp görüntüleme imkanına sahiptirler.
- Laborantlar ise sisteme Hastahane kimlik numarası ile kayıt olup, kayıt olduktan sonra bir yönetici tarafından hesabının onaylanması gerekmektedir. Bu yüzden `dev` isimli Spring profili uygulama ilk çalıştığında eğer bir yönetici hesabı yok ise varsayılan olarak bir yönetici hesabı oluşturacaktır.
- Laborantlar ise sisteme giriş yaptıktan sonra var olan raporları görüntüleyebilir, güncelleyebilir ve yeni raporlar ekleyebilir fakat raporları sadece yönetici rolüne sahip bir kişi silebilir.
- Laborantlar Hasta İsim ve Soyisimi ile Hastanın TC kimlik numarasına göre ve Laborant isim soyismine göre aratma yapabilirler.
- Uygulama bir web uygulaması olduğundan dolayı yetkilendirme sistemi çerezler üzerinden sağlanmaktadır. Çerezin özellikleri ; 
    - HttpOnly : true -> Projenin bir hastahane içerisinde kullanılacağını varsayarsak çerezlerin bir Javascript kodu ile elde edilememesi gerekmektedir.
    - SameSite : Lax -> Yine aynı sebepten dolayı dışarıdan başka bir site üzerinde bu uygulamaya ait bilgilerin görüntülenmesi güvenlik açığı olabileceğinden dolayı ancak siteye yönlendirme ile girilebilmesinde sorun olmayacağını düşündüğümden dolayı Lax olarak belirledim.
    - Çerezin değerini oluşturmak için ise JWT kullanılmaktadır.
- Eğer kullanıcı bir çereze sahip değilse react uygulaması içerisinde Navbar bileşeninde backend sunucusuna bir istek yollandığından dolayı eğer başarısız ise tekrar anasayfaya yönlendirme yapılmaktadır.
- Aynı şekilde Users isimli bileşende kullanıcı Yönetici rolüne sahip değilse backendden bir veri gelmeyeceğinden dolayı sayfayı görüntülese bile herhangi bir şekilde bir işlem yapamayacaktır.
- Backendde Arayüz(Interface) ve Somut(Concrete) sınıf şeklinde tasarlanmıştır. Bu Birim Testi konusunda ve Bağımlılıkların yönetilmesi konusunda avantaj sağladığı içindir.
- Projenin backendinde JPA Repository ve MySQL kullanılmaktadır.
- Kullanıcının oturum(session) bilgileri istek atarken kullandığı çerez içerisinde bulunan jwt anahtarından çıkartılmaktadır.

__________________________


## Gereksinimler

- #### NodeJS & npm
    - `https://nodejs.org/en/download/` Windows & Mac
    - `sudo apt install npm` Linux

- #### Maven
    - Kurulum için [bknz](https://www.baeldung.com/install-maven-on-windows-linux-mac)

- #### JDK17
    - [Oracle](https://www.oracle.com/java/technologies/downloads/#java17)
    - [Adoptium](https://adoptium.net/temurin/releases/?package=jdk&version=17)

- #### MySQL Database 
    - Kurulum için inceleyebilirsiniz. [Java T Point](https://www.javatpoint.com/how-to-install-mysql)
    - [XAMPP](https://www.apachefriends.org/tr/index.html)
    - [WampServer](https://www.wampserver.com/en/download-wampserver-64bits/)

__________________________


## Kurulumlar

- Kurulumları aşağıda belirtilen sırayla tamamlamanız önerilmektedir.
    1. ### Backend
        - #### Manuel Kurulum
            1. Backend kurulumu için öncelikle bilgisayarınızda Maven ve JDK17 kurulu olması gerekmektedir.
            2. Bu iki bileşen kurulduktan sonra rest klasörünün içerisinde aşağıda bulunan kodları çalıştırmanız gerekmektedir.
            3. `mvn clean install` Bu komut ile maven sayesinde backend projesi derlenip bir jar dosyası oluşacaktır. 
            4. Sonrasında ise MySQL veritabanı bağlantıları ve diğer seçenekler için aşağıdaki şekilde çalıştırabilirsiniz.
                
                    java -jar 
                        -Dspring.datasource.url=jdbc:mysql://{dburl}/{dbname} 
                        -Dspring.datasource.username={dbusername}
                        -Dspring.datasource.password={dbpassword}
                         target/lab.report.pro-0.0.1-SNAPSHOT.jar
            - dburl : Spring Boot uygulamasının MySQL veritabanına ulaşmak için kullanacağı url. Örneğin;
            localhost:3306
            - dbname : MySQL veritabanınızda tanımlı olan ve verilerinizin kaydedilmesini istediğiniz veritabanı adı. Örneğin;
            labreport
            - dbusername: MySQL veritabanınızda kayıtlı olan ve DBA yetkisine sahip olan kullanıcı adı. Örneğin;
            root
            - dbpassword: MySQL kullanıcısının parolası
            password

        - #### Docker ile Kurulum
            1. Eğer bilgilsayarınızda Docker kurulu ise (Windows işletim sistemi kullanıyorsanız WSL de kurulu olmalıdır.)
            2. rest isimli klasör altında bulunan docker compose dosyasını kullanabilirsiniz.
            3. Tek yapmanız gereken bu komutu çalıştırmaktır. 
                `docker compose up`

     2. ### React App
        - Öncelikle react uygulamasını çalıştırmadan önce bilgisayarınızda NodeJS ve npm kurulu olduğundan ve backend sunusunun çalıştığından emin olunuz.
        - web isimli klasöre girerek `npm install` komutunu çalıştırınız.
        - Sonrasında ise `npm start` komutu ile react uygulamasını çalıştırabilirsiniz.
        - baseUrl:3000 portundan uygulamaya erişebilirsiniz.


## Yazılımcı Notları
- Projeyi yerel sunucunuzda çalıştıracaksanız java -jar komutunu çalıştırırken `-Dspring.profiles.active=dev` çevre değişkenini(environment variable) eklerseniz varsayılan olarak bir yönetici hesabı ekleyecektir. Bu yönetici bilgileri;
    - Hastahane Kimlik Numarası: 0123456
    - Parola : admin


## Katkıda Bulunanlar

### Katkıda Bulunun
    1. `fork`
    2. Yeni bir `branch` açın (`git checkout -b feature-name`)
    3. `commit` edin (`git commit -m commit-message`)
    4. Katkıda bulunanlara isminizi ekleyin.
    5. `branch`’inizi `push` edin (`git push origin feature-name`)
    6. ve **Pull Request** açın!