# Système de Suivi et d'Évaluation de Projets (SSE)

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-red.svg)](https://maven.apache.org/)

## 📖 Description

Le **Système de Suivi et d'Évaluation de Projets (SSE)** est une API REST complète développée avec Spring Boot qui permet de gérer le cycle de vie complet des projets : de leur création à leur évaluation, en passant par le suivi des indicateurs de performance, la gestion des tâches et la génération de rapports.

## 🏗️ Architecture Technique

### **Stack Technologique**
- **Backend** : Spring Boot 3.3.5
- **Java** : Version 21 (LTS)
- **Base de données** : PostgreSQL 15+
- **ORM** : JPA/Hibernate 6
- **Documentation API** : Swagger/OpenAPI 3
- **Validation** : Bean Validation (Jakarta)
- **Sérialisation** : Jackson avec modules spécialisés
- **Build Tool** : Maven 3.8+
- **Tests API** : Compatible Postman/Insomnia

### **Architecture en Couches**
```
┌─────────────────────────────────────┐
│           Controllers               │  ← 6 API REST + Documentation Swagger
├─────────────────────────────────────┤
│              DTOs                   │  ← Transfert + Validation + Anti-Cycle
├─────────────────────────────────────┤
│             Services                │  ← Logique métier + Transactions
├─────────────────────────────────────┤
│          Repositories               │  ← 47+ méthodes JPA + Requêtes personnalisées
├─────────────────────────────────────┤
│             Models                  │  ← 10 Entités JPA + Héritage + Relations
├─────────────────────────────────────┤
│          Configuration              │  ← Jackson + CORS + Swagger
├─────────────────────────────────────┤
│           PostgreSQL                │  ← Base de données relationnelle
└─────────────────────────────────────┘
```

## 🎯 API REST Complète - 47+ Endpoints

### **🔥 Contrôleurs Implémentés**

| Contrôleur | Endpoints | Fonctionnalités |
|------------|-----------|-----------------|
| **UserController** | 6 | Gestion utilisateurs + types |
| **ProjetController** | 8 | CRUD projets + filtres avancés |
| **IndicateurController** | 7 | KPIs + métriques + recherche |
| **EvaluateurController** | 10 | Évaluateurs + assignation projets |
| **RapportController** | 12 | Rapports + génération auto |
| **TacheController** | 14 | Tâches + statuts + retards |

### **📊 Statistiques de l'API**
- **Total endpoints** : 47+
- **Méthodes HTTP** : GET, POST, PUT, PATCH, DELETE
- **Formats supportés** : JSON
- **Documentation** : Swagger UI intégrée
- **Validation** : Complète avec messages d'erreur explicites

## 🚀 Fonctionnalités Détaillées

### **👥 Gestion des Utilisateurs & Évaluateurs**
```bash
# API Users (Héritage JPA)
GET    /api/users                     # Liste tous les utilisateurs
POST   /api/users                     # Créer un utilisateur
GET    /api/users/{id}                # Détails utilisateur
PUT    /api/users/{id}                # Modifier utilisateur
DELETE /api/users/{id}                # Supprimer utilisateur

# API Évaluateurs (Spécialisée)
GET    /api/evaluateurs               # Liste évaluateurs
POST   /api/evaluateurs               # Créer évaluateur (avec DTO)
GET    /api/evaluateurs/email/{email} # Recherche par email
GET    /api/evaluateurs/recherche?nom=... # Recherche par nom
POST   /api/evaluateurs/{id}/assigner-projet/{projetId} # Assigner projet
DELETE /api/evaluateurs/{id}/retirer-projet # Retirer du projet
```

**Types d'utilisateurs supportés :**
- 👑 **Administrateur** : Gestion complète du système
- 👨‍💼 **Chef de Projet** : Gestion des projets assignés  
- 🎯 **Décideur** : Consultation et validation
- 📊 **Évaluateur** : Évaluation et rapports

### **📋 Gestion des Projets**
```bash
GET    /api/projets                   # Liste projets + pagination
POST   /api/projets                   # Créer projet
GET    /api/projets/{id}              # Détails complets
PUT    /api/projets/{id}              # Modifier projet
DELETE /api/projets/{id}              # Supprimer projet
GET    /api/projets/statut/{statut}   # Filtrer par statut
GET    /api/projets/chef/{chefId}     # Projets d'un chef
```

**Statuts de projet :**
- 🟡 `EN_COURS` (défaut)
- 🟢 `TERMINE`
- 🔴 `SUSPENDU`
- 🔵 `PLANIFIE`

### **📊 Indicateurs de Performance (KPIs)**
```bash
GET    /api/indicateurs               # Tous les indicateurs
POST   /api/indicateurs               # Créer indicateur
GET    /api/indicateurs/projet/{id}   # Indicateurs d'un projet
GET    /api/indicateurs/type/{type}   # Par type (QUANTITATIF/QUALITATIF)
GET    /api/indicateurs/recherche?nom=... # Recherche textuelle
PUT    /api/indicateurs/{id}          # Modifier indicateur
DELETE /api/indicateurs/{id}          # Supprimer indicateur
```

**Types d'indicateurs :**
- 📈 **QUANTITATIF** : Mesures numériques
- 📝 **QUALITATIF** : Évaluations descriptives

### **✅ Gestion des Tâches (Complet)**
```bash
# CRUD Tâches
GET    /api/taches                    # Toutes les tâches
POST   /api/taches                    # Créer tâche
PUT    /api/taches/{id}               # Modifier tâche
PATCH  /api/taches/{id}/statut?statut=EN_COURS # Changer statut uniquement
DELETE /api/taches/{id}               # Supprimer tâche

# Filtres avancés
GET    /api/taches/projet/{id}        # Tâches d'un projet
GET    /api/taches/statut/{statut}    # Par statut
GET    /api/taches/projet/{id}/statut/{statut} # Projet + statut
GET    /api/taches/recherche?intitule=... # Recherche textuelle
GET    /api/taches/en-retard          # Tâches en retard (auto-calculé)
GET    /api/taches/projet/{id}/en-retard # Retards par projet

# Statistiques
GET    /api/taches/stats/projet/{id}  # Stats par statut
GET    /api/taches/count              # Compteur global
GET    /api/taches/count/projet/{id}  # Compteur par projet
```

**Statuts de tâches :**
- 📋 `A_FAIRE` (défaut)
- 🔄 `EN_COURS`
- ✅ `TERMINEE`
- ⏸️ `EN_ATTENTE`

### **📄 Gestion des Rapports**
```bash
# CRUD Rapports
GET    /api/rapports                  # Tous les rapports
POST   /api/rapports                  # Créer rapport
PUT    /api/rapports/{id}             # Modifier rapport
DELETE /api/rapports/{id}             # Supprimer rapport

# Recherche et filtrage
GET    /api/rapports/projet/{id}      # Rapports d'un projet
GET    /api/rapports/auteur/{auteur}  # Par auteur
GET    /api/rapports/recherche?titre=... # Recherche par titre
GET    /api/rapports/periode?dateDebut=...&dateFin=... # Par période
GET    /api/rapports/projet/{id}/auteur/{auteur} # Croisé

# Fonctionnalités avancées
POST   /api/rapports/generer-auto/{projetId} # Génération automatique
GET    /api/rapports/count/projet/{id} # Statistiques
```

## 🛠️ Configuration Technique Avancée

### **🔧 Gestion Jackson (Anti-références circulaires)**
```java
@Configuration
public class JacksonConfig {
    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }
    
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }
}
```

### **📝 DTOs Anti-Sérialisation**
- **EvaluateurDTO** : Évite les problèmes d'héritage `User`
- **RapportDTO** : Simplifie les relations `Projet`
- **TacheDTO** : Optimise les transferts
- **Validation complète** : `@Valid`, `@NotNull`, `@NotBlank`

### **🔍 Recherche Avancée (JPA Queries)**
```java
// Exemple de requête personnalisée
@Query("SELECT t FROM Tache t WHERE t.dateFin < :dateActuelle AND t.statut != 'TERMINEE'")
List<Tache> findTachesEnRetard(@Param("dateActuelle") LocalDate dateActuelle);
```

## 🚀 Installation et Démarrage

### **📋 Prérequis**
- ☕ **Java 21** (LTS recommandé)
- 🐘 **PostgreSQL 15+**
- 📦 **Maven 3.8+**
- 🔧 **IDE** : IntelliJ IDEA / VS Code / Eclipse

### **⚡ Démarrage Rapide**

1. **Cloner le projet**
```bash
git clone https://github.com/kanebassirou/Backend_sseprojet.git
cd Backend_sseprojet
```

2. **Configuration Base de Données**
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sseprojet_db
spring.datasource.username=votre_username
spring.datasource.password=votre_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Compilation et démarrage**
```bash
mvn clean install
mvn spring-boot:run
```

4. **Vérification**
```bash
# Test de fonctionnement
curl http://localhost:8080/api/projets

# Documentation Swagger
http://localhost:8080/swagger-ui/index.html
```

## 📚 Documentation API

### **🔗 Endpoints de test rapide**
```bash
# Vérification des APIs
GET http://localhost:8080/api/taches/test          # "API Taches fonctionne !"
GET http://localhost:8080/api/rapports/test        # "API Rapports fonctionne !"
GET http://localhost:8080/api/evaluateurs          # Liste évaluateurs
GET http://localhost:8080/api/indicateurs          # Liste indicateurs
```

### **📖 Documentation Interactive**
- **Swagger UI** : http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON** : http://localhost:8080/v3/api-docs
- **Tous les endpoints** documentés avec exemples

### **🧪 Exemples JSON**

**Créer une tâche :**
```json
POST /api/taches
{
  "intitule": "Développer l'API REST",
  "dateDebut": "2025-08-03",
  "dateFin": "2025-08-10", 
  "statut": "A_FAIRE",
  "projetId": 1
}
```

**Créer un rapport :**
```json
POST /api/rapports
{
  "titre": "Rapport mensuel",
  "dateGeneration": "2025-08-03",
  "contenu": "Contenu détaillé...",
  "auteur": "Jean Dupont",
  "projetId": 1
}
```

**Créer un évaluateur :**
```json
POST /api/evaluateurs
{
  "nom": "Marie Martin",
  "email": "marie.martin@example.com",
  "motDePasseHash": "password_hash",
  "projetId": 1
}
```

## 🏆 Fonctionnalités Avancées

### **⚡ Performances**
- **Transactions optimisées** : `@Transactional(readOnly = true)`
- **Lazy Loading** : Relations FetchType.LAZY
- **Pagination** : Support intégré JPA
- **Cache** : Prêt pour l'activation

### **🔒 Sécurité (Préparé)**
- **CORS** configuré
- **Validation** complète des entrées
- **Gestion d'erreurs** robuste
- **Logs** de débogage

### **📊 Fonctionnalités Métier**
- **Calcul automatique** des tâches en retard
- **Génération automatique** de rapports
- **Statistiques** par projet et statut
- **Recherche full-text** sur tous les éléments

## 🧪 Tests et Validation

### **✅ Endpoints Testés**
- ✅ Tous les contrôleurs fonctionnels
- ✅ Validation des DTOs
- ✅ Gestion d'erreurs
- ✅ Relations JPA
- ✅ Sérialisation JSON

### **🔧 Outils de Test**
```bash
# Collection Postman disponible
# Tests unitaires avec JUnit 5
# Tests d'intégration Spring Boot
```

## 👨‍💻 Équipe de Développement

- **Développeur Principal** : [Votre Nom]
- **Repository** : https://github.com/kanebassirou/Backend_sseprojet
- **Branche principale** : Master

## 📈 Roadmap

### **🎯 Prochaines Fonctionnalités**
- [ ] 🔐 **Authentification JWT** + Spring Security
- [ ] 📱 **Interface Web** (React/Vue.js)
- [ ] 📊 **Dashboard** temps réel
- [ ] 🔔 **Notifications** automatiques
- [ ] 📤 **Export PDF/Excel** des rapports
- [ ] 🧪 **Tests automatisés** complets

### **⚡ Optimisations Prévues**
- [ ] **Cache Redis** pour les performances
- [ ] **Elasticsearch** pour la recherche
- [ ] **Monitoring** avec Actuator
- [ ] **CI/CD** pipeline
- [ ] **Docker** containerisation

---

## 🎉 Résumé

✅ **API REST complète** avec 47+ endpoints  
✅ **6 contrôleurs** entièrement fonctionnels  
✅ **Architecture robuste** avec DTOs et validation  
✅ **Gestion avancée** des relations JPA  
✅ **Documentation Swagger** intégrée  
✅ **Recherche et filtrage** sophistiqués  
✅ **Gestion d'erreurs** professionnelle  

**Le système SSE est maintenant production-ready !** 🚀
- **Types** : Performance, Qualité, Financier, Temporel
- **Association** : Liés aux projets
- **Suivi** : Valeurs cibles et réelles

### **🔍 Évaluations**
- **Multi-évaluateurs** : Système d'évaluation collaborative
- **Critères** : Évaluation selon différents critères
- **Historique** : Traçabilité des évaluations

### **📄 Rapports**
- **Génération automatique** : Basée sur les données projets
- **Formats** : Support de différents formats de sortie
- **Planification** : Rapports périodiques ou à la demande

### **✅ Tâches**
- **Décomposition** : Tâches liées aux projets
- **Statuts** : Suivi de l'avancement
- **Assignation** : Attribution aux membres d'équipe

## 📚 Documentation API (Swagger)

### **Accès à la documentation**
Une fois l'application démarrée, accédez à :

#### **Interface Swagger UI (Interactive)**
```
http://localhost:8080/swagger-ui.html
```
*Interface web permettant de tester tous les endpoints directement*

#### **Spécification OpenAPI JSON**
```
http://localhost:8080/api-docs
```
*Documentation complète au format JSON pour intégration*

### **Fonctionnalités de la documentation Swagger**
- ✅ **Interface interactive** pour tester tous les endpoints
- ✅ **Schémas complets** des modèles de données avec exemples
- ✅ **Validation en temps réel** des paramètres
- ✅ **Codes de statut HTTP** détaillés avec descriptions
- ✅ **Exemples de requêtes/réponses** pour chaque endpoint
- ✅ **Groupement par ressources** (Users, Projets, Indicateurs)
- ✅ **Documentation des DTOs** avec contraintes de validation

### **APIs documentées**
- **👥 Users API** : Gestion complète des utilisateurs
- **📋 Projets API** : CRUD projets avec DTOs optimisés
- **📊 Indicateurs API** : Gestion des indicateurs de performance
- **🔍 Évaluations API** : Système d'évaluation
- **📄 Rapports API** : Génération et consultation
- **✅ Tâches API** : Gestion des tâches projet

## 🗄️ Modèle de Données

### **Structure de la base de données**
```sql
-- 6 tables principales
├── users          -- Tous les utilisateurs (avec héritage)
├── projets         -- Projets et leur cycle de vie
├── indicateurs     -- Indicateurs de performance
├── evaluations     -- Évaluations des projets
├── rapports        -- Rapports générés
└── taches          -- Tâches des projets
```

### **Héritage des utilisateurs**
```java
User (abstract)
├── Administrateur    -- Gestion système
├── ChefDeProjet      -- Gestion projets
├── Decideur          -- Validation projets
└── Evaluateur        -- Évaluations
```

### **Relations principales**
- `ChefDeProjet` → `List<Projet>` (One-to-Many)
- `Projet` → `List<Indicateur>` (One-to-Many)
- `Projet` → `List<Evaluation>` (One-to-Many)
- `Projet` → `List<Tache>` (One-to-Many)
- `Evaluateur` → `List<Evaluation>` (One-to-Many)

## 🚀 Installation et Démarrage

### **Prérequis**
- Java 21+
- Maven 3.8+
- PostgreSQL 17+
- Git

### **Configuration de la base de données**
1. **Créer la base de données** :
``

2. **Configurer l'application** dans `src/main/resources/application.properties` :
```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/sseprojet
spring.datasource.username=sseuser
spring.datasource.password=ssepassword

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Swagger/OpenAPI
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

### **Installation et lancement**
```bash
# 1. Cloner le projet
git clone le ripo git
cd sseprojet

# 2. Installer les dépendances
mvn clean install

# 3. Démarrer l'application
mvn spring-boot:run
```

### **Vérification du démarrage**
```bash
# Vérifier que l'application fonctionne
curl http://localhost:8080/api/users

# Accéder à Swagger UI
http://localhost:8080/swagger-ui.html
```

## 🧪 Tests et Validation

### **Tests avec Postman**

#### **Collection complète disponible**
Importez la collection Postman fournie pour tester tous les endpoints :

#### **1. Créer un utilisateur**
```http
POST http://localhost:8080/api/users
Content-Type: application/json

{
    "nom": "Jean Admin",
    "email": "admin@exemple.com",
    "motDePasseHash": "admin123",
    "type": "ADMINISTRATEUR"
}
```

#### **2. Créer un projet**
```http
POST http://localhost:8080/api/projets
Content-Type: application/json

{
    "titre": "Application Mobile E-Commerce",
    "description": "Développement d'une application mobile complète",
    "dateDebut": "2025-01-15",
    "dateFin": "2025-08-30",
    "budget": 75000,
    "statut": "EN_COURS",
    "chefDeProjetId": 3
}
```

#### **3. Créer un indicateur**
```http
POST http://localhost:8080/api/indicateurs
Content-Type: application/json

{
    "nom": "Taux d'avancement",
    "type": "PERFORMANCE",
    "objectif": "Mesurer le pourcentage de completion",
    "projet": {"id": 1}
}
```

### **Réponses attendues**
✅ **Pas de références circulaires** grâce aux DTOs  
✅ **Validation automatique** des données d'entrée  
✅ **Codes de statut appropriés** (200, 201, 400, 404)  
✅ **Format JSON cohérent** et documenté

## 📁 Structure du Projet

```
src/
├── main/java/com/sseprojet/sseprojet/
│   ├── config/           # Configuration (Swagger, etc.)
│   │   └── SwaggerConfig.java
│   ├── controller/       # Contrôleurs REST
│   │   ├── UserController.java
│   │   ├── ProjetController.java
│   │   └── IndicateurController.java
│   ├── dto/             # DTOs et validation
│   │   ├── CreateUserRequest.java
│   │   ├── CreateProjetRequest.java
│   │   └── ProjetResponse.java
│   ├── factory/         # Factory pattern
│   │   └── UserFactory.java
│   ├── model/           # Entités JPA
│   │   ├── User.java (abstract)
│   │   ├── Administrateur.java
│   │   ├── ChefDeProjet.java
│   │   ├── Decideur.java
│   │   ├── Evaluateur.java
│   │   ├── Projet.java
│   │   ├── Indicateur.java
│   │   ├── Evaluation.java
│   │   ├── Rapport.java
│   │   └── Tache.java
│   ├── repository/      # Repositories JPA
│   │   ├── UserRepository.java
│   │   ├── ProjetRepository.java
│   │   └── IndicateurRepository.java
│   ├── service/         # Services métier
│   │   ├── UserService.java
│   │   ├── ProjetService.java
│   │   ├── IndicateurService.java
│   │   └── ProjetMapperService.java
│   └── SseprojetApplication.java
├── main/resources/
│   ├── application.properties
│   └── static/          # Ressources statiques
└── test/                # Tests unitaires
```

## 🔧 Fonctionnalités Avancées

### **DTOs et Mapping**
- **Évite les références circulaires** entre entités
- **Contrôle total** de la sérialisation JSON
- **Validation centralisée** avec Bean Validation
- **Services de mapping** pour conversion entité ↔ DTO

### **Factory Pattern**
- **Création automatique** d'utilisateurs selon le type
- **Encapsulation** de la logique de création
- **Extensibilité** pour nouveaux types d'utilisateurs

### **Héritage JPA**
- **Table unique** pour tous les utilisateurs
- **Polymorphisme** avec discriminateur
- **Relations spécialisées** selon le type d'utilisateur


