# 🛒 SmartShop – Backend REST API (Spring Boot)

## 📌 Description

**SmartShop** est une application **backend REST API** de gestion commerciale destinée à **MicroTech Maroc**, un distributeur B2B de matériel informatique basé à Casablanca.
L’application permet la gestion complète des **clients**, **produits**, **commandes**, **paiements fractionnés**, ainsi qu’un **système de fidélité automatique** basé sur l’historique des clients.

👉 Application **100% backend**, sans interface graphique.
👉 Tests et démonstrations via **Postman** ou **Swagger**.

---

## 🎯 Objectifs du Projet

* Centraliser la gestion commerciale B2B
* Automatiser la fidélisation client avec remises progressives
* Gérer les commandes multi-produits et paiements multi-moyens
* Assurer la traçabilité complète des opérations financières
* Appliquer des règles métier réalistes (TVA, stock, paiements)

---

## 🧰 Technologies Utilisées

* **Langage** : Java 8+
* **Framework** : Spring Boot
* **ORM** : Spring Data JPA (Hibernate)
* **Base de données** : PostgreSQL / MySQL
* **API** : REST (JSON)
* **Authentification** : HTTP Session (login / logout)
* **Documentation API** : Swagger
* **Tests API** : Postman
* **Tests unitaires** : JUnit, Mockito
* **Mapping** : MapStruct
* **Utilitaires** : Lombok
* **Outils** : Git, GitHub

---

## 🏗️ Architecture

Architecture en couches respectant les bonnes pratiques :

```
Controller → Service → Repository → Database
           ↓
          DTO / Mapper
```

* Séparation claire des responsabilités
* Logique métier centralisée dans la couche Service
* Gestion globale des erreurs avec `@ControllerAdvice`

---

## 👥 Rôles Utilisateurs

* **ADMIN** : Gestion complète (clients, produits, commandes, paiements)
* **CLIENT** : Consultation de ses données personnelles et de son historique

---

## 🔐 Authentification

* Authentification via **HTTP Session**
* Endpoints :

```
POST /auth/login
POST /auth/logout
GET  /auth/me
```

❌ Pas de JWT
❌ Pas de Spring Security

---

## 📦 Fonctionnalités Principales

### 👤 Gestion des Clients

* CRUD clients
* Statistiques automatiques :

  * Nombre total de commandes
  * Montant cumulé
  * Date de première et dernière commande
* Historique détaillé des commandes

---

### 🎖️ Système de Fidélité

Niveaux automatiques basés sur :

* Nombre de commandes confirmées
* Montant total dépensé

| Niveau   | Condition                     |
| -------- | ----------------------------- |
| BASIC    | Par défaut                    |
| SILVER   | ≥ 3 commandes ou ≥ 1 000 DH   |
| GOLD     | ≥ 10 commandes ou ≥ 5 000 DH  |
| PLATINUM | ≥ 20 commandes ou ≥ 15 000 DH |

Remises applicables sur les commandes futures selon le niveau.

---

### 🧾 Gestion des Commandes

* Commandes multi-produits
* Validation automatique du stock
* Calcul automatique :

  * Sous-total HT
  * Remises
  * TVA (20% configurable)
  * Total TTC
* Gestion des statuts :

  * PENDING
  * CONFIRMED
  * CANCELED
  * REJECTED

---

### 💳 Paiements Fractionnés

* Moyens acceptés :

  * Espèces
  * Chèque
  * Virement
* Paiement en plusieurs fois possible
* Suivi du montant restant dû
* Une commande ne peut être **confirmée** que si elle est **totalement payée**

---

## ⚠️ Règles Métier

* Validation du stock avant création de commande
* TVA calculée après remise
* Codes promo format : `PROMO-XXXX`
* Arrondis à 2 décimales
* Impossible de modifier une commande finale

---

## 🚨 Gestion des Erreurs

Gestion centralisée avec `@ControllerAdvice`

Codes HTTP utilisés :

* `400` : Erreur de validation
* `401` : Non authentifié
* `403` : Accès refusé
* `404` : Ressource introuvable
* `422` : Règle métier violée
* `500` : Erreur interne

Format de réponse JSON standardisé.

---

## 📑 Documentation API

* Swagger disponible après lancement :

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Tests

* Tests unitaires avec JUnit & Mockito
* Tests des endpoints via Postman

---

## ▶️ Lancement du Projet

1. Cloner le projet :

```bash
git clone https://github.com/username/smartshop.git
```

2. Configurer la base de données dans `application.yml`

3. Lancer l’application :

```bash
mvn spring-boot:run
```

---

## 📊 Livrables

* Code source (GitHub)
* Diagramme UML (Classes)
* Collection Postman / Swagger
* README.md
* Projet Jira

---

## 👨‍💻 Auteur

**Ayoub Oumha**
Projet académique – Backend Spring Boot
---------------------------------------

## 📄 Licence

Projet à usage pédagogique.
