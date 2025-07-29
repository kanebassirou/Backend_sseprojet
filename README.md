# Système de Suivi et d'Évaluation de Projets (SSE Projet)

## 📋 Description

SSE Projet est une application Spring Boot dédiée à la gestion et au suivi de projets. Elle permet aux équipes de gérer efficacement leurs projets, de suivre les indicateurs de performance, d'effectuer des évaluations et de générer des rapports détaillés.

## 🏗️ Architecture du Projet

### Technologies Utilisées

- **Framework** : Spring Boot 3.5.4
- **Java** : Java 21
- **Base de données** : PostgreSQL
- **ORM** : Spring Data JPA avec Hibernate
- **Build Tool** : Maven
- **Validation** : Spring Boot Validation
- **Dev Tools** : Spring Boot DevTools
- **Autres** : Lombok pour réduire le code boilerplate

### Structure du Projet

```
src/
├── main/
│   ├── java/com/sseprojet/sseprojet/
│   │   ├── SseprojetApplication.java        # Point d'entrée de l'application
│   │   ├── controller/                       # Contrôleurs REST
│   │   │   ├── IndicateurController.java
│   │   │   ├── ProjetController.java
│   │   │   └── UserController.java
│   │   ├── model/                           # Entités JPA
│   │   │   ├── User.java (classe abstraite)
│   │   │   ├── Administrateur.java
│   │   │   ├── ChefDeProjet.java
│   │   │   ├── Decideur.java
│   │   │   ├── Evaluateur.java
│   │   │   ├── Projet.java
│   │   │   ├── Indicateur.java
│   │   │   ├── Evaluation.java
│   │   │   ├── Rapport.java
│   │   │   └── Tache.java
│   │   ├── repository/                      # Repositories JPA
│   │   │   ├── UserRepository.java
│   │   │   ├── ChefDeProjetRepository.java
│   │   │   ├── ProjetRepository.java
│   │   │   ├── IndicateurRepository.java
│   │   │   ├── EvaluationRepository.java
│   │   │   ├── RapportRepository.java
│   │   │   └── TacheRepository.java
│   │   └── service/                         # Services métier
│   │       ├── UserService.java
│   │       └── ProjetService.java
│   └── resources/
│       └── application.properties           # Configuration de l'application
└── test/                                    # Tests unitaires
```

## 🔧 Fonctionnalités Implémentées

### 1. Gestion des Utilisateurs ✅
- **Types d'utilisateurs** : Administrateur, Chef de Projet, Décideur, Évaluateur
- **Héritage** : Utilisation de l'héritage JPA avec stratégie SINGLE_TABLE
- **Validation** : Email unique, champs obligatoires
- **API REST** : CRUD complet pour les utilisateurs (UserController + UserService)
- **Repository** : Recherche par email, vérification d'existence

### 2. Gestion des Projets ✅
- **Entité Projet** : Titre, description, dates, budget, statut
- **Relations** : Un projet appartient à un Chef de Projet
- **API REST** : CRUD complet (ProjetController + ProjetService)
  - Récupération de tous les projets
  - Recherche par ID, statut, titre
  - Création, mise à jour, suppression
- **Filtrage** : Par statut et recherche textuelle dans le titre

### 3. Système d'Indicateurs ✅
- **Entité Indicateur** : Nom, type, objectif
- **Relations** : Indicateurs liés aux projets
- **API REST** : CRUD complet avec filtrage par projet et type (IndicateurController)
- **Repository** : Recherche par projet et par type

## 📋 Modèles de Données Implémentés

### Entités Complètement Implémentées
- **User** (classe abstraite) : Base pour tous les utilisateurs
- **Administrateur** : Hérite de User
- **ChefDeProjet** : Hérite de User, peut gérer plusieurs projets
- **Decideur** : Hérite de User  
- **Evaluateur** : Hérite de User
- **Projet** : Entité principale avec relations vers toutes les autres entités
- **Indicateur** : Entité liée aux projets
- **Evaluation** : Entité liée aux projets (modèle + repository)
- **Rapport** : Entité liée aux projets (modèle + repository)
- **Tache** : Entité liée aux projets (modèle + repository)

### Repositories Implémentés
- **UserRepository** : CRUD + recherche par email
- **ProjetRepository** : CRUD + recherche par chef de projet, statut, titre
- **IndicateurRepository** : CRUD + recherche par projet et type
- **EvaluationRepository** : CRUD + recherche par projet et plage de dates
- **RapportRepository** : CRUD + recherche par projet et auteur
- **TacheRepository** : CRUD + recherche par projet et statut
- **ChefDeProjetRepository** : CRUD de base

## 🗄️ Modèle de Base de Données

### Tables Principales
- `users` : Stockage des utilisateurs avec discriminateur de rôle
- `projets` : Informations sur les projets
- `indicateurs` : Indicateurs de performance des projets
- `evaluations` : Évaluations périodiques des projets
- `rapports` : Rapports générés pour les projets
- `taches` : Tâches associées aux projets

### Relations
- `User` → `ChefDeProjet` (héritage)
- `ChefDeProjet` → `Projet` (One-to-Many)
- `Projet` → `Indicateur` (One-to-Many)
- `Projet` → `Evaluation` (One-to-Many)
- `Projet` → `Rapport` (One-to-Many)
- `Projet` → `Tache` (One-to-Many)

## 🚀 Installation et Configuration

### Prérequis
- Java 21 ou supérieur
- Maven 3.6+
- PostgreSQL 17+
- Git

### Configuration de la Base de Données

1. Créer une base de données PostgreSQL :
```sql
CREATE DATABASE sseprojetdb;
```

2. Modifier le fichier `application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sseprojetdb
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

### Lancement de l'Application

1. Cloner le repository
2. Naviguer vers le répertoire du projet
3. Lancer l'application :

**Avec Maven :**
```bash
./mvnw spring-boot:run
```

**Ou en PowerShell (Windows) :**
```powershell
.\mvnw.cmd spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`

## 📡 API REST Endpoints Implémentés

### Utilisateurs (`/api/users`) ✅
- `GET /api/users` - Récupérer tous les utilisateurs
- `GET /api/users/{id}` - Récupérer un utilisateur par ID
- `GET /api/users/email/{email}` - Récupérer un utilisateur par email
- `POST /api/users` - Créer un nouvel utilisateur
- `PUT /api/users/{id}` - Mettre à jour un utilisateur
- `DELETE /api/users/{id}` - Supprimer un utilisateur

### Projets (`/api/projets`) ✅
- `GET /api/projets` - Récupérer tous les projets
- `GET /api/projets/{id}` - Récupérer un projet par ID
- `GET /api/projets/statut/{statut}` - Filtrer par statut
- `GET /api/projets/search?titre={titre}` - Rechercher par titre
- `POST /api/projets` - Créer un nouveau projet
- `PUT /api/projets/{id}` - Mettre à jour un projet
- `DELETE /api/projets/{id}` - Supprimer un projet

### Indicateurs (`/api/indicateurs`) ✅
- `GET /api/indicateurs` - Récupérer tous les indicateurs
- `GET /api/indicateurs/{id}` - Récupérer un indicateur par ID
- `GET /api/indicateurs/projet/{projetId}` - Indicateurs par projet
- `GET /api/indicateurs/type/{type}` - Indicateurs par type
- `POST /api/indicateurs` - Créer un nouvel indicateur
- `PUT /api/indicateurs/{id}` - Mettre à jour un indicateur
- `DELETE /api/indicateurs/{id}` - Supprimer un indicateur

## 🚧 Fonctionnalités à Implémenter

### API REST Manquantes
- **Evaluations** (`/api/evaluations`) - Contrôleur et service à créer
- **Rapports** (`/api/rapports`) - Contrôleur et service à créer  
- **Tâches** (`/api/taches`) - Contrôleur et service à créer

### Services Manquants
- **IndicateurService** - Service métier pour les indicateurs
- **EvaluationService** - Service métier pour les évaluations
- **RapportService** - Service métier pour les rapports
- **TacheService** - Service métier pour les tâches

## 🔐 Gestion des Rôles

Le système implémente quatre types d'utilisateurs :

1. **Administrateur** : Gestion complète du système
2. **Chef de Projet** : Gestion des projets assignés
3. **Décideur** : Consultation des rapports et indicateurs
4. **Évaluateur** : Saisie des évaluations

## 📈 Fonctionnalités Avancées

### Validation des Données
- Validation des emails
- Champs obligatoires
- Contraintes d'unicité (email des utilisateurs)

### Configuration JPA
- Génération automatique des tables (`hibernate.ddl-auto=update`)
- Affichage des requêtes SQL en mode développement
- Dialecte PostgreSQL optimisé

### CORS
- Configuration CORS permissive pour le développement (`origins = "*"`)

## 🧪 Tests

Le projet inclut des tests de base :
- Test de chargement du contexte Spring Boot
- Structure prête pour l'ajout de tests unitaires et d'intégration

## 📦 Build et Déploiement

### Construction du projet
```bash
./mvnw clean package
```

### Exécution du JAR
```bash
java -jar target/sseprojet-0.0.1-SNAPSHOT.jar
```

## 🔍 Prochaines Étapes de Développement

1. **Compléter les Services** :
   - Créer `IndicateurService` pour la logique métier des indicateurs
   - Créer `EvaluationService` pour la gestion des évaluations
   - Créer `RapportService` pour la génération de rapports
   - Créer `TacheService` pour la gestion des tâches

2. **Compléter les API REST** :
   - Créer `EvaluationController` avec endpoints CRUD
   - Créer `RapportController` avec endpoints CRUD
   - Créer `TacheController` avec endpoints CRUD

3. **Améliorations Techniques** :
   - Implémentation de Spring Security pour l'authentification
   - Ajout de validation plus avancée des données
   - Tests unitaires et d'intégration
   - Gestion d'erreurs avec `@ControllerAdvice`
   - Documentation API avec Swagger/OpenAPI

4. **Fonctionnalités Avancées** :
   - Pagination pour les listes importantes
   - Système de notifications
   - Tableaux de bord et statistiques
   - Export de données (PDF, Excel)

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commit les changements (`git commit -am 'Ajout nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Créer une Pull Request

## 📄 Licence

Ce projet est sous licence libre pour usage éducatif et professionnel.

---

**Version** : 0.0.1-SNAPSHOT  
**Date de dernière mise à jour** : 29 juillet 2025  
**Auteur** : Bassirou Kane
