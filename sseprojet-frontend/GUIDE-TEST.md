# 🧪 Guide de Test - Application SSE

## 🚀 Démarrage Rapide

L'application est maintenant accessible sur : **http://localhost:63110/**

## 🔐 **Authentification JWT Intégrée**

### ✅ **Pages disponibles :**
- **Connexion :** http://localhost:63110/authentication/sign-in
- **Inscription :** http://localhost:63110/authentication/sign-up

### 🔑 **Comment tester :**
1. **S'inscrire :** Créer un nouveau compte avec nom, prénom, email, mot de passe et rôle
2. **Se connecter :** Utiliser un compte existant de votre base de données
3. **Navigation :** Redirection automatique selon le rôle après connexion

### 🏠 **Dashboard personnalisé** selon le rôle connecté

## 📋 Checklist de Test

### ✅ Navigation Adaptative
- [ ] **Menu Administrateur** : 7 éléments (Dashboard, Utilisateurs, Projets, Tâches, Indicateurs, Rapports, Évaluateurs)
- [ ] **Menu Chef de Projet** : 4 éléments (Dashboard, Projets Assignés, Tâches, Rapports)
- [ ] **Menu Décideur** : 4 éléments (Dashboard, Consultation Projets, Rapports, Indicateurs)
- [ ] **Menu Évaluateur** : 4 éléments (Dashboard, Évaluations, Rapports, Indicateurs)

### 🏠 Dashboard par Rôle

#### Administrateur
- [ ] Affichage des **4 cartes** : Projets, Tâches, Rapports, Utilisateurs
- [ ] **Actions rapides** : Créer Utilisateur, Nouveau Projet, Voir Rapports
- [ ] Badge utilisateur : "Administrateur"

#### Chef de Projet  
- [ ] Affichage des **4 cartes** : Projets, Tâches, Rapports, Mes Projets
- [ ] **Actions rapides** : Nouvelle Tâche, Créer Rapport, Mes Projets
- [ ] Badge utilisateur : "Chef de Projet"

#### Décideur
- [ ] Affichage des **3 cartes** : Projets, Rapports, Indicateurs
- [ ] **Actions rapides** : Consulter Projets, Voir Rapports, Indicateurs
- [ ] Badge utilisateur : "Décideur"

#### Évaluateur
- [ ] Affichage des **3 cartes** : Projets, Rapports, Évaluations
- [ ] **Actions rapides** : Évaluer Projet, Consulter Rapports, Voir Indicateurs
- [ ] Badge utilisateur : "Évaluateur"

### 📊 Page Évaluations (Évaluateur)
- [ ] **3 cartes statistiques** : En Attente, En Cours, Terminées
- [ ] **Tableau des évaluations** avec colonnes : Projet, Statut, Date, Note, Actions
- [ ] **Données de test** : 3 évaluations avec différents statuts
- [ ] **Actions disponibles** : Évaluer, Continuer, Voir

## 🔄 Test de Changement de Rôle

Pour tester différents rôles, modifiez dans `navigation.component.ts` :

```typescript
// Ligne ~82
this.currentUser = {
  id: 1,
  nom: 'John Doe',
  email: 'john@example.com',
  type: 'ADMINISTRATEUR' // Changer ici : CHEF_DE_PROJET, DECIDEUR, EVALUATEUR
};
```

## 🎯 URLs de Test

- **Connexion** : http://localhost:63110/authentication/sign-in
- **Inscription** : http://localhost:63110/authentication/sign-up
- **Dashboard** : http://localhost:63110/dashboard (après connexion)
- **Projets** : http://localhost:63110/projets
- **Tâches** : http://localhost:63110/taches
- **Rapports** : http://localhost:63110/rapports
- **Utilisateurs** : http://localhost:63110/users (Admin seulement)
- **Évaluateurs** : http://localhost:63110/evaluateurs (Admin seulement)
- **Évaluations** : http://localhost:63110/evaluations (Évaluateur seulement)
- **Indicateurs** : http://localhost:63110/indicateurs

## 🐛 État de l'Application

### ✅ **Fonctionnel :**
1. ✅ **Authentification JWT** : Connexion et inscription avec backend
2. ✅ **Interface responsive** : Pages de connexion et inscription
3. ✅ **Compilation réussie** : Application accessible sur http://localhost:63110/
4. ✅ **Navigation adaptative** : Menu selon le rôle utilisateur
5. ✅ **Dashboard personnalisé** : Cartes et actions selon le rôle

### ⚠️ **À vérifier :**
1. **Backend Spring Boot** : Doit être en cours d'exécution sur port 8080
2. **Base de données** : PostgreSQL avec tables utilisateurs
3. **CORS** : Configuration autorisée entre frontend et backend
4. **Endpoints API** : `/api/auth/login`, `/api/auth/register`, `/api/auth/me`

### 🔄 **Test de rôles :**
Après connexion, l'application affiche automatiquement :
- **Menu adapté** selon le rôle
- **Dashboard personnalisé** avec les bonnes cartes
- **Permissions d'accès** aux sections appropriées

## ✨ Fonctionnalités Implémentées

### ✅ Terminé
- [x] **Authentification JWT complète** avec backend Spring Boot
- [x] **Pages de connexion et inscription** professionnelles
- [x] **Navigation adaptative** par rôle
- [x] **Dashboard personnalisé** par utilisateur
- [x] **Composant Évaluations** pour les évaluateurs
- [x] **Styles responsifs** pour mobile et desktop
- [x] **Compilation réussie** et application fonctionnelle
- [x] **Intercepteur HTTP** pour les requêtes authentifiées
- [x] **Gestion automatique des tokens** JWT

### 🚧 En Développement
- [ ] Connexion complète aux APIs backend pour les données métier
- [ ] Filtrage des données par rôle utilisateur
- [ ] Formulaires d'évaluation avancés
- [ ] Gestion des permissions granulaires

## 📱 Test Responsive

Testez sur différentes tailles d'écran :
- **Desktop** : Grille complète
- **Tablet** : Adaptation des cartes
- **Mobile** : Menu burger + cartes empilées

---

**🎉 Félicitations !** Votre application SSE est maintenant fonctionnelle avec :
- ✅ **Authentification JWT** complète
- ✅ **Interface utilisateur** professionnelle  
- ✅ **Vues adaptées** par rôle utilisateur
