# WISS-Quiz-Project
Für die Zwischenprüfung des Moduls 295 "Backend für Applikationen realisieren" mussten wir das Wiss_Quiz Projekt mit den Anforderungen von den Sidequests 3B, 3C und 4B bearbeiten.


## Inhaltsverzeichnis
- [WISS-Quiz-Project](#wiss-quiz-project)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [SQ3B Beziehungen mit Objektrelationalem Mapping](#sq3b-beziehungen-mit-objektrelationalem-mapping)
    - [Ausgangslage](#ausgangslage)
    - [Wissens-Check](#wissens-check)
      - [Wie werden Beziehungen zwischen zwei Datenklassen mit JPA definiert?](#wie-werden-beziehungen-zwischen-zwei-datenklassen-mit-jpa-definiert)
      - [Was passiert, wenn man einen Category-Datensatz löscht, zu dem es Question-Datensätze gibt?](#was-passiert-wenn-man-einen-category-datensatz-löscht-zu-dem-es-question-datensätze-gibt)
      - [Wie arbeiten der Controller und Repository zusammen?](#wie-arbeiten-der-controller-und-repository-zusammen)
    - [Erkenntnisse](#erkenntnisse)
  - [SQ3C](#sq3c)
  - [SQ4B](#sq4b)
  - [Fachbegriffe](#fachbegriffe)


## SQ3B Beziehungen mit Objektrelationalem Mapping

### Ausgangslage

<img title="" alt="UML Diagramm" src="./img/Screenshot 2024-10-21 140848.png">

<p>In dieser Sidequest lernen wir wie man eine REST-API mit Datenbank-Anbindung für das Wiss-Quiz zu erstellen.</p>

**Codebeispiel:**
```java
@Entity
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String question;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /** ommitted all other SETTER/GETTERs */

    public void setCategory(Category cat) {
        this.category = cat;
    }

    public Category getCategory() {
        return category;
    }
}
```

### Wissens-Check
#### Wie werden Beziehungen zwischen zwei Datenklassen mit JPA definiert?
<p>In JPA werden Beziehungen zwischen Datenklassen (Entitäten) durch sogenannte Annotations definiert. Untenstehend die häufigsten Beziehungstypen:</p>

**Einz-zu-Eins-Beziehung (OnetoOne)**
```java
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // weitere Attribute
}
```

**Eins-zu-Viele-Beziehung (OnetoMany)**
```java
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Question> questions;
}

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
```
**Viele-zu-Viele-Beziehung (ManytoMany)**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
```

<ins>**Wichtige Annotationen**</ins>

`@JoinColumn`
<p>Gibt die Spalte an, die als Fremdschlüssel verwendet wird.</p>

`@JoinTable`
<p>Wird verwendet, um eine Zwischentabelle für Viele-zu-Viele-Beziehungen zu definieren.</p>

#### Was passiert, wenn man einen Category-Datensatz löscht, zu dem es Question-Datensätze gibt?

<p>Es hängt ab wie die Cascade Option definiert wurde. In unserem Fall ist es CascadeType.ALL was bedeutet dass wenn die Category gelöscht wird, alle verknüpften Question-Datensätze ebenfalls gelöscht werden.</p>

#### Wie arbeiten der Controller und Repository zusammen?

1.  Der Controller nimmt eine Anfrage vom Benutzer entgegen.
2. Der Controller sagt dem Repository, was es tun soll, z. B. Daten abrufen oder speichern.
3. Das Repository führt die Datenbank-Operationen aus und gibt die Daten an den Controller zurück.
4. Der Controller gibt dem Benutzer das Ergebnis zurück (z. B. als JSON-Antwort oder Webseite).

<p>Man kann sich den **Controller** als "Kellner" vorstellen der die Bestellungen der Gäste (Benutzer) entgegennimmt.</p>
<p>Die Küche ist das Repository, die die Bestellungen verarbeitet, indem es die benötigten Zutaten (Daten) aus der Speisekammer (Datenbank) holt.</p>

### Erkenntnisse


## SQ3C

## SQ4B

## Fachbegriffe
<ins>ORM Object-Relational Mapping</ins>
<p>Ermöglicht Datenbanktabellen (relationale Daten) in Form von Objekten in der Programmiersprache zu verwenden. ORM-Tools verbinden die objektorientierte Welt mit der relationalen Datenbank, indem sie SQL-Abfragen automatisch generieren und die Daten zwischen der Datenbank und den Objekten hin- und herschieben.</p>

<ins>Controller - Verwalter der Anfragen</ins>
<p>Kümmert sich um die Kommunikation zwischen dem Benutzer und der Anwendung. Er nimmt die Anfragen des Benutzers (z. B. in Form von URLs) entgegen und gibt die entsprechenden Antworten zurück.</p>

<ins>Repository - Der Zugang zur Datenbank</ins>
<p>Ist der Teil, der sich um die Datenbank kümmert. Es verwaltet alle Operationen, die mit Daten zu tun haben, wie das Abrufen, Speichern, Aktualisieren oder Löschen von Informationen in der Datenbank.</p>

<ins>Fetchtypes</ins>
<ul>
<li>EAGER</li>

```java
@ManyToOne(fetch = FetchType.EAGER, optional = false)
```
<p>externe Daten werden sofort aus der verknüpften Tabelle geladen (default)</p>
<li>LAZY</li>

```java
@ManyToOne(fetch = FetchType.LAZY)
```

<p>externe Daten werden erst beim Aufruf der entsprechenden GET-Methode aus der verknüpften Tabelle geladen</p>
</ul>


`ResponseEntity<Category>`
<p>Eine Klasse die HTTP-Antworten (Responses) in SpringBoot beschreibt. Sie kann
Statuscodes, Header und dein eigentlichen Rückgabewert enthalten. In diesem Fall ein Category-Objekt.</p>

```java
@PostMapping("/") // Map ONLY POST Requests
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        category = categoryRepository.save(category);
        return ResponseEntity.ok().body(category);
    }
```

`@Autowired`
<p>Es sagt Spring Boot: „Gib mir das CategoryRepository-Objekt, ohne dass ich es explizit erstellen muss.</p>

```java
@Autowired
    private CategoryRepository categoryRepository;
```
`@PathVariable long id`
<p>Wird verwendet, um einen Teil der URL als Parameter zu übernehmen. Wenn die URL zum Beispiel http://localhost:8080/category/5 lautet, wird 5 als Wert für id übernommen.</p>

```java
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category " + id + " deleted.");
    }
```

`@Entity`

`@Table(name="question")`

`@Id`

`@GeneratedValue(strategy=GenerationType.IDENTITY)`

`@JoinColumn(name = "category_id", nullable = false)`

`@JoinColumn`
<p>Gibt die Spalte an, die als Fremdschlüssel verwendet wird.</p>

`@JoinTable`
<p>Wird verwendet, um eine Zwischentabelle für Viele-zu-Viele-Beziehungen zu definieren.</p>