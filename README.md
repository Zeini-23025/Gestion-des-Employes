# Gestion-des-Employés

Bienvenue dans le projet **Gestion des Employés** !  
Cette application web permet de gérer les employés, leurs départements, salaires, et bien plus.  
Elle utilise **Spring Boot**.

## 🚀 Méthodes pour exécuter le projet

### 1️⃣ Mode Développement (manuellement)
> Idéal pour contribuer au projet

#### 🔁 Lancer le projet (Spring Boot)
```bash
git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
cd Gestion-des-Employes/server
./mvnw spring-boot:run
```

### 2️⃣ Avec Docker Compose
> Rapide et simple, tout tourne ensemble

```bash
git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
cd Gestion-des-Employes
docker-compose up --build
```

### 3️⃣ Utilisation des images Docker (pull depuis Docker Hub)
> Pas besoin de builder, juste pull et run

```bash
docker pull zeini/spring-server:latest

# Lancer le backend
docker run --name gestion-des-employe -p 8080:8080 -d zeini/spring-server:latest
```

---

## 💬 Remerciements

Merci de contribuer, et amuse-toi bien avec le projet !
