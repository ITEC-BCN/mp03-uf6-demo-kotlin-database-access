# Contingut
Aquest projecte de IntelliJ conté una base de dades de *SQL lite* en format .db dins de la carpeta [src/databases](src/databases) a la qual hi accedirem usant les següents llibreries de kotlin:
- **DriverManager**: Classe de la qual usarem el mètode static *getConnection()* per tal de definir la connection string que ens permetrà apuntar i connectar-nos al fitxer de base de dades.
- **Statement**: Classe de la qual usarem el mètode *executeQuery()* per tal d'executar la consulta SQL passada per paràmetre. 
- **ResultSet**: Classe la qual l'usarem com a buffer de lectura com si fós un fitxer. Contindrà les dades resultants d'executar la consulta SQL. 

# Llenguatge
Kotlin multiplatform

# IDE
IntelliJ projecte amb **Gradle** SDK 20

# Pluguin
Per tal d'usar les classes esmentades anteriorment, és necessari incorporar la següent dependència a dins del fitxer de configuració de gradle [build.gradle.kts](build.gradle.kts)

![img.png](images/gradle-pluguin.png)

> [!NOTE]
> Aquest projecte ja ho porta incorporat i no cal afegir-ho de nou