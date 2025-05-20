# Zenite

A modern Android application built with Jetpack Compose.

## Branding

### Colors
- Primary: #144364
- Secondary: #19BFB7
- Tertiary: #B8EFEC
- Black: #000000
- White: #FFFFFF
- Gray Light: #A8A8A9
- Gray Dark: #676767

### Typography
- Quicksand: Used for headings and titles
- Poppins: Used for body text and UI elements

## Project Setup

This project uses:
- Kotlin programming language
- Jetpack Compose for UI
- Material Design 3
- Google Fonts integration for Quicksand and Poppins fonts

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Run the app on an emulator or physical device

## Development

- The UI theme is defined in `app/src/main/java/com/example/zenite/ui/theme/`
- Colors are defined in both the Compose theme and XML resources
- Fonts are loaded from Google Fonts using the Compose Google Fonts dependency

## Building and Running

```bash
# Build the app
./gradlew build

# Install on a connected device
./gradlew installDebug
```

---

## Criando telas (Layout Standard)

Todas as telas **devem usar** o componente `ZeniteScreen` como base. Ele já inclui:

* TopBar com botão de menu, título centralizado e avatar do usuário logado
* BottomBar com botão flutuante
* Drawer (mwenu) lateral e navegação automática

---

## Regras de desenvolvimento

* Usar o padrão **MVVM**, para evitar lógica nas views. Toda regra e estado deve ficar no ViewModel
* Criar as telas dentro do pacote `ui/screens/`
* **Nao** usar texto fixo na interface (nada de hardcoded). Toda string deve estar no `strings.xml` e ser usada com `stringResource` para facilitar internacionalizacao
* Adicionar novas entradas no `strings.xml` dentro de `res/values/`
* Colocar recursos visuais apenas em `res/drawable` ou na pasta `assets/`

---
