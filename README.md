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
 
---

## 📬 Contact

Pour toute question, contactez :  
**El metni Abdelbarie**  
**Abdelatif Kenzi**  
Email : [abdelbarielmetni@gmail.com]
        [k.abdelatif03@gmail.com]

---
