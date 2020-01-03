# Android-Shopping-List
Projet pour le laboratoire d'android

# Installation et lancement WebAPI
```
cd API/
npm install --save express jsonwebtoken (Si le dossier 'node_modules' n'existe pas)
npm start (Pour lancer l'api)
```
# Petit plus
Changer l'adresse de l'api dans le ShoppingList/app/src/main/java/be/amellaa/shoppinglist/dto/ShoppingListDTO.kt
```
const val DOMAIN_URL = "https://x.x.x.x:3000"
```