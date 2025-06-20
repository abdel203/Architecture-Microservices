# 📦 ms-dauphine-project

Projet de microservices développé dans le cadre du Master 2 MIAGE - Université Paris Dauphine.

Ce projet utilise **Docker** pour le déploiement des services, et inclut des bases de données PostgreSQL, MongoDB et Neo4j.

---

## ⚙️ Prérequis

- [Docker](https://www.docker.com/) installé sur votre machine
- Git (optionnel)

---

## 🚀 Démarrage rapide

### 1. Télécharger et installer Docker

Si Docker n'est pas encore installé, suivez les instructions officielles ici :  
👉 https://docs.docker.com/get-docker/

---

### 2. Créer un dossier <name>

```bash
mkdir <name>
cd ./<name>
```

---

### 3. Cloner et ouvrir le projet

```bash
git clone <URL_DU_REPO>
```

---

### 4. Lancer uniquement PostgreSQL (étape initiale)

```bash
docker compose up -d postgresql
```

---

### 5. Créer la base de données `information`

Cette base est nécessaire au bon fonctionnement de l'application.

```bash
docker exec -it ms_pg_sql2 \
  psql -U root -d postgres -c "CREATE DATABASE information;"
```

---

### 6. Vérifier que la base `information` existe

```bash
docker-compose exec postgresql psql -U root -l
```

---

### 7. Stopper le conteneur PostgreSQL (optionnel)

```bash
docker-compose stop postgresql
```

---

### 8. Lancer tous les services Docker

#### ▶️ Premier lancement (avec compilation)

```bash
docker compose up --build -d
```

#### 🚀 Lancements suivants (si build déjà créer)

```bash
docker compose up -d
```

---

### 9. Tester l'application : 

Pour tester l'application, ouvrir postman et importer le fichier TestSimulation.json présent dans le répertoire.
Ceci va permettre à postman de mettre à votre disposition plusieurs dossiers pour un jeu de test.
Voici l'ordre d'éxecution des tests que nous vous conseillons pour éviter les erreurs : 
- Ouvrir le dossier create -> create (Batch).
- Executer dans l'ordre les requetes : POST create cities/POST create lodging/ POST create points of interest / POST create activity.
- Ouvrir le dossier roads.
- Executer la requete POST create roads (batch)
- Maintenant, vous pouvez vous amusez avec les requetes mises à votre disposition dans le repertoire qui ont pour but la découverte des villes, logements, activités, centres d'intérets etc. Ne pas éxecuter la requete GET get trip by reference avant d'avoir créer un 'trip', une simulation de création de voyage est disponible grace à l'execution de la route "create -> create (one by one) -> POST create trip", une fois cette requete executer elle renvoie la reference du voyage qui pourra etre utilsier en paramètre de la requete GET get trip by reference pour voir le sommaire du voyage programmé.
---

## 📬 Contact

Pour toute question, contactez :  
**El metni Abdelbarie**  
**Abdelatif Kenzi**  
Email : [abdelbarielmetni@gmail.com]
        [k.abdelatif03@gmail.com]

---
