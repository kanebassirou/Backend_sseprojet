# 🔐 Configuration JWT Frontend - Angular

## 📋 Récapitulatif de l'intégration JWT

Votre frontend Angular a été **entièrement migré** pour utiliser l'authentification JWT de votre backend Spring Boot. Voici ce qui a été configuré :

## 🔧 **Modifications apportées**

### 1. **Modèles mis à jour** (`user.model.ts`)
- ✅ Interface `LoginRequest` 
- ✅ Interface `LoginResponse`
- ✅ Interface `RegisterRequest`
- ✅ Interface `User` compatible avec votre backend

### 2. **Service JWT** (`jwt.service.ts`)
- ✅ Gestion du token JWT (stockage, récupération, suppression)
- ✅ Décodage automatique du token
- ✅ Vérification de l'expiration
- ✅ Headers d'autorisation pour les requêtes API

### 3. **Service d'authentification** (`auth.service.ts`)
- ✅ Connexion via `/api/auth/login`
- ✅ Récupération du profil via `/api/auth/me`
- ✅ Inscription via `/api/auth/register`
- ✅ Gestion de l'état utilisateur avec BehaviorSubject
- ✅ Redirection automatique selon le rôle

### 4. **Intercepteur HTTP** (`auth.interceptor.ts`)
- ✅ Ajout automatique du token JWT aux requêtes
- ✅ Configuration dans `app.config.ts`

### 5. **Page de test Admin** (`admin-test.component.ts`)
- ✅ Test de l'API backend
- ✅ Initialisation du compte administrateur

## 🚀 **Comment utiliser**

### 1. **Démarrer le backend** (Port 8080)
```bash
# Dans votre projet Spring Boot
./mvnw spring-boot:run
```

### 2. **Démarrer le frontend** (Port 4200)
```bash
# Dans ce dossier
npm start
```

### 3. **Initialiser l'admin** (Première utilisation)
1. Aller sur : http://localhost:4200/authentication/admin-test
2. Cliquer sur "Initialiser Admin"
3. Cela créera le compte : admin@test.com / password123

### 4. **Se connecter**
1. Aller sur : http://localhost:4200
2. Utiliser un des comptes de test :
   - **Admin :** admin@test.com / password123
   - **Chef :** chef@test.com / password123
   - **Décideur :** decideur@test.com / password123
   - **Évaluateur :** evaluateur@test.com / password123

## 🔄 **Flux d'authentification**

1. **Connexion :** L'utilisateur saisit email/mot de passe
2. **API :** Appel POST vers `/api/auth/login`
3. **Token :** Réception et stockage du JWT
4. **Profil :** Auto-récupération du profil utilisateur
5. **Navigation :** Redirection selon le rôle
6. **Requêtes :** Token automatiquement ajouté aux en-têtes

## 🛡️ **Sécurité**

- ✅ Token JWT stocké en localStorage
- ✅ Vérification automatique de l'expiration
- ✅ Nettoyage automatique si token invalide
- ✅ Guards protégeant les routes sensibles
- ✅ Intercepteur pour les requêtes authentifiées

## 📊 **API Endpoints utilisés**

| Endpoint | Méthode | Usage |
|----------|---------|-------|
| `/api/auth/login` | POST | Connexion utilisateur |
| `/api/auth/me` | GET | Profil utilisateur |
| `/api/auth/register` | POST | Inscription |
| `/api/auth/init-admin` | POST | Initialisation admin |
| `/api/auth/test` | GET | Test API |
| `/api/auth/list-users` | GET | Liste utilisateurs (admin) |

## 🎯 **Prochaines étapes**

1. **Tester la connexion** avec les comptes fournis
2. **Vérifier les rôles** et permissions
3. **Ajuster les redirections** si nécessaire
4. **Connecter les autres services** (projets, tâches, etc.) à l'API

## 🔍 **Debug**

- **Console navigateur :** Messages de log détaillés
- **Network tab :** Voir les requêtes/réponses
- **Page test :** `/authentication/admin-test` pour diagnostics

---

🎉 **Votre frontend est maintenant entièrement intégré avec votre API JWT !**
