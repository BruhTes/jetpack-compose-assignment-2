# TODO Tracker App – Jetpack Compose + Retrofit + Room

An Android application that fetches a list of TODO items from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/todos), displays them in a scrollable Jetpack Compose UI, and allows users to view details. It supports offline access using Room database caching and follows MVVM architecture with repository pattern.

![image](https://github.com/user-attachments/assets/44133e22-c2e4-40b5-9622-7150381ad0b6)
![image](https://github.com/user-attachments/assets/af4e5163-5bdd-4894-adc3-e169bb548b71)
![image](https://github.com/user-attachments/assets/8e1a1bdc-34ed-46ee-899e-8ef371454257)


---

##  Features

- 📡 Fetch TODO items from remote API using **Retrofit**
- 🗃️ Store and cache data locally with **Room**
- 📱 Modern UI with **Jetpack Compose**
- 🔁 Offline-first: loads from cache, syncs with server
- 🧭 Smooth navigation between list and detail screens using **Jetpack Navigation Compose**
- 🛠️ Clean architecture with MVVM and Repository pattern
- 📊 Loading & error states handled gracefully

---

## 📦 Tech Stack

| Layer       | Library/Tool                  |
|-------------|-------------------------------|
| UI          | Jetpack Compose, Navigation   |
| Networking  | Retrofit + Kotlin Coroutines  |
| Database    | Room                          |
| Architecture| MVVM + Repository             |
| Language    | Kotlin                        |

---

## 🧱 Architecture

```plaintext
Data Layer (API + DB)
├── TodoDto       → Network response model
├── TodoEntity    → Room database model
├── TodoDao       → Data access object (Room)
├── ApiService    → Retrofit interface
├── TodoRepository→ Handles all data operations

Domain Layer
├── Todo          → App's internal model

Presentation Layer
├── ViewModels    → Expose data to UI
├── UI Screens    → ListScreen, DetailScreen (Jetpack Compose)
