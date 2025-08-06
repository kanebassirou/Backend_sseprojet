# 🎯 Guide des Vues par Type d'Utilisateur - SSE Projet Frontend

## 📋 Vue d'ensemble

Ce document décrit l'implémentation des vues spécifiques pour chaque type d'utilisateur dans le système SSE Projet, conformément aux spécifications backend et aux permissions de rôle.

## 🔐 Types d'Utilisateurs et Permissions

### 1. **ADMINISTRATEUR** 
**Accès complet** - Gestion de tout le système

**🏠 Dashboard :**
- Vue d'ensemble globale de tous les projets
- Statistiques des utilisateurs (total, nouveaux)
- Gestion complète du système

**📱 Vues Disponibles :**
- ✅ **Gestion Utilisateurs** (`/users`) - CRUD complet
- ✅ **Projets** (`/projets`) - Tous les projets
- ✅ **Tâches** (`/taches`) - Toutes les tâches
- ✅ **Indicateurs** (`/indicateurs`) - Tous les indicateurs
- ✅ **Rapports** (`/rapports`) - Tous les rapports
- ✅ **Évaluateurs** (`/evaluateurs`) - Gestion des évaluateurs

**🚀 Actions Rapides :**
- Créer Utilisateur
- Nouveau Projet
- Voir Rapports

**🔗 Endpoints Principaux :**
- `/api/users/*`
- `/api/projets/*`
- `/api/taches/*`
- `/api/indicateurs/*`
- `/api/rapports/*`

---

### 2. **CHEF_DE_PROJET**
**Gestion de projets assignés** - Focus sur les projets sous sa responsabilité

**🏠 Dashboard :**
- Projets assignés uniquement
- Tâches de ses projets
- Statistiques de ses projets

**📱 Vues Disponibles :**
- ✅ **Projets Assignés** (`/projets`) - Filtrés par chef
- ✅ **Tâches** (`/taches`) - De ses projets uniquement
- ✅ **Rapports** (`/rapports`) - De ses projets

**🚀 Actions Rapides :**
- Nouvelle Tâche
- Créer Rapport
- Mes Projets

**🔗 Endpoints Principaux :**
- `/api/projets/chef/{chefId}`
- `/api/taches/projet/{id}`
- `/api/rapports/projet/{id}`

---

### 3. **DECIDEUR**
**Consultation stratégique** - Vue d'ensemble pour la prise de décision

**🏠 Dashboard :**
- Vue panoramique des projets
- Indicateurs clés de performance
- Rapports stratégiques

**📱 Vues Disponibles :**
- ✅ **Consultation Projets** (`/projets`) - Lecture seule
- ✅ **Rapports** (`/rapports`) - Consultation
- ✅ **Indicateurs** (`/indicateurs`) - Analyse

**🚀 Actions Rapides :**
- Consulter Projets
- Voir Rapports
- Indicateurs

**🔗 Endpoints Principaux :**
- `/api/projets/*` (lecture)
- `/api/rapports/*` (lecture)
- `/api/indicateurs/projet/{id}`

---

### 4. **EVALUATEUR**
**Évaluation de projets** - Focus sur l'évaluation et les rapports

**🏠 Dashboard :**
- Évaluations en attente
- Projets à évaluer
- Historique des évaluations

**📱 Vues Disponibles :**
- ✅ **Évaluations** (`/evaluations`) - Interface d'évaluation
- ✅ **Rapports** (`/rapports`) - Consultation et création
- ✅ **Indicateurs** (`/indicateurs`) - Analyse des projets évalués

**🚀 Actions Rapides :**
- Évaluer Projet
- Consulter Rapports
- Voir Indicateurs

**🔗 Endpoints Principaux :**
- `/api/rapports/*`
- `/api/indicateurs/projet/{id}`
- `/api/evaluations/*` (futur)

---

## 🛠️ Implémentation Technique

### Navigation Adaptative
```typescript
// Navigation basée sur le rôle utilisateur
private menusByRole = {
  'ADMINISTRATEUR': [
    { path: '/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
    { path: '/users', icon: 'people', label: 'Gestion Utilisateurs' },
    { path: '/projets', icon: 'folder', label: 'Projets' },
    // ... autres menus
  ],
  'CHEF_DE_PROJET': [
    { path: '/dashboard', icon: 'dashboard', label: 'Tableau de Bord' },
    { path: '/projets', icon: 'folder', label: 'Projets Assignés' },
    // ... menus spécifiques
  ]
  // ... autres rôles
}
```

### Dashboard Adaptatif
```typescript
// Méthodes de permission pour l'affichage conditionnel
canViewProjets(): boolean {
  return ['ADMINISTRATEUR', 'CHEF_DE_PROJET', 'DECIDEUR'].includes(this.userType || '');
}

canViewTaches(): boolean {
  return ['ADMINISTRATEUR', 'CHEF_DE_PROJET'].includes(this.userType || '');
}
```

### Composant Évaluations
```typescript
// Interface spécifique pour les évaluations
interface Evaluation {
  id: number;
  projetId: number;
  projetNom: string;
  statut: 'EN_ATTENTE' | 'EN_COURS' | 'TERMINEE';
  note?: number;
  // ...
}
```

---

## 📁 Structure des Fichiers

```
src/app/
├── shared/
│   ├── navigation.component.ts     # Navigation adaptative
│   ├── navigation.component.html   # Template avec menus par rôle
│   └── navigation.component.css    # Styles de navigation
├── dashboard/
│   ├── dashboard.component.ts      # Dashboard adaptatif
│   ├── dashboard.component.html    # Template avec vues par rôle
│   └── dashboard.component.css     # Styles du dashboard
├── evaluations/
│   └── evaluations.component.ts    # Composant pour évaluateurs
├── models/
│   └── user.model.ts              # Types d'utilisateurs
└── services/
    ├── *.service.ts               # Services par entité
    └── index.ts                   # Export des services
```

---

## 🎨 Interface Utilisateur

### Caractéristiques Communes
- **Design responsive** avec Angular Material
- **Navigation latérale** adaptative
- **Cartes statistiques** personnalisées par rôle
- **Actions rapides** contextuelles
- **Thème cohérent** avec indicateurs visuels

### Éléments Spécifiques par Rôle
- **Couleurs d'accentuation** différentes par type d'utilisateur
- **Icônes contextuelles** pour chaque action
- **Statistiques pertinentes** selon les responsabilités
- **Raccourcis d'actions** adaptés aux tâches courantes

---

## 🚀 Prochaines Étapes

### Phase 1 - Finalisation (En cours)
- [x] Navigation adaptative
- [x] Dashboard par rôle
- [x] Composant Évaluations
- [ ] Tests de navigation

### Phase 2 - Intégration Backend
- [ ] Connexion aux APIs spécifiques
- [ ] Filtrage des données par rôle
- [ ] Gestion des permissions côté client

### Phase 3 - Fonctionnalités Avancées
- [ ] Notifications en temps réel
- [ ] Tableaux de bord interactifs
- [ ] Rapports personnalisés
- [ ] Système d'évaluation complet

---

## 📊 Endpoints API Mappés

| Utilisateur | Endpoints Principaux | Fonctionnalités |
|-------------|---------------------|-----------------|
| **Admin** | `/api/users/*`, `/api/projets/*` | CRUD complet |
| **Chef** | `/api/projets/chef/{id}`, `/api/taches/projet/{id}` | Gestion projets assignés |
| **Décideur** | `/api/projets/*`, `/api/rapports/*` | Consultation stratégique |
| **Évaluateur** | `/api/rapports/*`, `/api/indicateurs/projet/{id}` | Évaluation et analyse |

---

## 🔧 Configuration et Démarrage

```bash
# Installation
npm install

# Démarrage du serveur de développement
ng serve --port 4200

# Build de production
ng build

# Tests
ng test
```

---

**✅ État Actuel :** Vues par utilisateur implémentées et fonctionnelles
**🎯 Objectif :** Interface adaptative complète selon les rôles métier
**📅 Dernière mise à jour :** 4 août 2025
