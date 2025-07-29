# Système de Suivi et d'Évaluation de Projets (SSE Projet)

## 📖 Description

Le **Système de Suivi et d'Évaluation de Projets (SSE)** est une application web moderne développée avec Spring Boot qui permet de gérer le cycle de vie complet des projets : de leur création à leur évaluation, en passant par le suivi des indicateurs de performance et la génération de rapports.

## 🏗️ Architecture Technique

### **Technologies utilisées**
- **Backend** : Spring Boot 3.3.5
- **Java** : Version 21
- **Base de données** : PostgreSQL
- **ORM** : JPA/Hibernate
- **Documentation API** : Swagger/OpenAPI 3
- **Validation** : Bean Validation (Jakarta)
- **Build** : Maven
- **Tests API** : Postman

### **Architecture en couches**
```
┌─────────────────────────────────────┐
│           Controllers               │  ← API REST + Documentation Swagger
├─────────────────────────────────────┤
│              DTOs                   │  ← Transfert de données + Validation
├─────────────────────────────────────┤
│             Services                │  ← Logique métier + Mappers
├─────────────────────────────────────┤
│          Repositories               │  ← Accès aux données JPA
├─────────────────────────────────────┤
│             Models                  │  ← Entités JPA + Héritage
├─────────────────────────────────────┤
│           PostgreSQL                │  ← Base de données relationnelle
└─────────────────────────────────────┘
```

## 🎯 Fonctionnalités Principales

### **👥 Gestion des Utilisateurs**
- **Types d'utilisateurs** : Administrateur, Chef de Projet, Décideur, Évaluateur
- **Héritage JPA** : Tous stockés dans la table `users` avec discriminateur
- **CRUD complet** : Création, lecture, mise à jour, suppression
- **Factory Pattern** : Création automatique selon le type

### **📋 Gestion des Projets**
- **Cycle de vie complet** : Planification → En cours → Terminé → Suspendu
- **Relations** : Association avec les chefs de projet
- **DTOs** : Évite les références circulaires
- **Validation** : Contraintes métier (dates, budget, etc.)

### **📊 Gestion des Indicateurs**
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


