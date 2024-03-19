# Contingut
## SQLite local
Aquest projecte de IntelliJ conté una base de dades de *SQL lite* en format .db dins de la carpeta [src/databases](src/databases) a la qual hi accedirem al [Main.kt](src/main/kotlin/Main.kt) usant les següents llibreries de kotlin:
- **DriverManager**: Classe de la qual usarem el mètode static *getConnection()* per tal de definir la connection string que ens permetrà apuntar i connectar-nos al fitxer de base de dades.
- **Statement**: Classe de la qual usarem el mètode *executeQuery()* per tal d'executar la consulta SQL passada per paràmetre. 
- **ResultSet**: Classe la qual l'usarem com a buffer de lectura com si fós un fitxer. Contindrà les dades resultants d'executar la consulta SQL. 

## PostgreSQL remot
També conté un exemple per a connectar-se a una BD de PostgreSQL externa que podeu trobar a [TestPostgreElephant](src/main/kotlin/TestPostgreElephant.kt)

# Llenguatge
Kotlin multiplatform

# IDE
IntelliJ projecte amb **Gradle** SDK 20

# Plugins
Per tal d'usar les classes esmentades anteriorment, és necessari incorporar les següents dependencies a dins del fitxer de configuració de gradle [build.gradle.kts](build.gradle.kts)

## SQLite
```code
dependencies {
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
}
```

## PostgreSQL
```code
dependencies {
    implementation("org.postgresql:postgresql:42.6.0")
}
```

> [!NOTE]
> Aquest projecte ja ho porta incorporat i no cal afegir-ho de nou