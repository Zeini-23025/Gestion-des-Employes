## Hi onii-chan, genki desu ka? Daisuki  💖😊

# Gestion-des-Employés


Bienvenue dans le projet **Gestion des Employés** !  
Cette application web permet de gérer les employés, leurs départements, salaires, et bien plus.  
Elle utilise **Spring Boot** pour le backend, **React** pour le frontend

## 🚀 Méthodes pour exécuter le projet

### 1️⃣ Mode Développement (manuellement)
> Idéal pour contribuer au projet

#### 🔁 Backend (Spring Boot)
```bash
git clone https://github.com/Zeini-23025/Gestion-des-Employes.git
cd Gestion-des-Employes/server
./mvnw spring-boot:run
```

#### 💻 Frontend (React)
```bash
cd ../frontend
npm install
npm start
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
docker pull zeini/react-client:latest



# Lancer le backend
docker run --name employe-backend --network employe-net -p 8080:8080 -d zeini/spring-server:latest

# Lancer le frontend
docker run --name employe-frontend --network employe-net -p 3000:80 -d zeini/react-client:latest
```

---


---

## 💬 Remerciements

### Good bye oni-sama 😘  
Merci de contribuer, et amuse-toi bien avec le projet !
