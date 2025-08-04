# Frontend SSE Projets - Angular

Frontend Angular pour le Système de Suivi et d'Évaluation de Projets (SSE).

## 🚀 Fonctionnalités Implémentées

### ✅ Modules Principaux
- **Dashboard** : Vue d'ensemble avec statistiques
- **Gestion des Projets** : Liste, création, modification
- **Gestion des Tâches** : Liste avec filtres et mise à jour de statuts
- **Gestion des Utilisateurs** : Liste des utilisateurs par type
- **Gestion des Rapports** : Liste des rapports
- **Gestion des Indicateurs** : Liste des indicateurs de performance

### 🎨 Interface Utilisateur
- **Angular Material** : Interface moderne et responsive
- **Navigation** : Sidebar avec menu adaptatif
- **Tableaux** : Affichage des données avec tri et filtres
- **Formulaires** : Validation en temps réel
- **Responsive Design** : Compatible mobile et desktop

### 🔧 Architecture Technique
- **Services** : Communication avec l'API REST backend
- **Models** : Types TypeScript pour toutes les entités
- **Standalone Components** : Architecture moderne Angular 18
- **Lazy Loading** : Chargement optimisé des routes
- **HTTP Client** : Intégration complète avec l'API

## 📦 Installation

### Prérequis
- **Node.js** 18+ 
- **npm** ou **yarn**
- **Angular CLI** 18+

### Installation des dépendances
```bash
npm install
```

### Configuration de l'API
L'application est configurée pour utiliser l'API backend sur `http://localhost:8080`. 

## 🏃‍♂️ Démarrage

### Développement
```bash
npm start
# ou
ng serve
```

L'application sera disponible sur `http://localhost:4200`

### Build de production
```bash
npm run build
```

## 🔄 Intégration avec l'API Backend

L'application communique avec l'API backend Spring Boot sur :
```
http://localhost:8080/api/*
```

### Endpoints utilisés
- **Users** : `/api/users`
- **Projets** : `/api/projets`  
- **Tâches** : `/api/taches`
- **Rapports** : `/api/rapports`
- **Indicateurs** : `/api/indicateurs`
- **Évaluateurs** : `/api/evaluateurs`
