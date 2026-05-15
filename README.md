# 🎵 Sistema de Streaming de Música

Sistema de streaming de música desenvolvido em Java com execução via terminal, simulando o funcionamento de uma plataforma de áudio completa. Usuários podem criar playlists, reproduzir músicas e receber recomendações personalizadas, com experiências diferenciadas conforme o tipo de conta (Free ou Premium).

---

## 📋 Funcionalidades

- **Reprodução de músicas** – Músicas podem ser reproduzidas, pausadas, paradas e avançadas no tempo, com controle de estado interno (tocando/pausado/parado)
- **Gerenciamento de playlists** – Criação de playlists, adição e remoção de músicas, navegação entre itens com suporte a avançar e voltar
- **Controle de downloads (Premium)** – Usuários premium podem baixar músicas para acesso offline, remover downloads e acompanhar o tamanho total dos arquivos baixados (5MB por música)
- **Limite de playlists (Free)** – Usuários free têm limite de 3 playlists; ao atingir o limite, novas adições são bloqueadas com mensagem informativa
- **Recomendações personalizadas** – Geração de sugestões por gênero favorito (até 5 músicas) ou de forma aleatória a partir de uma biblioteca global
- **Utilitários de suporte** – Formatação de tempo nos formatos `MM:SS`, `HH:MM:SS` e legível em português; validações de nome, e-mail, duração e índice

---

## 🏛️ Arquitetura

### Pacotes e Organização

```
br.com.streaming
├── modelo
│   ├── ItemReproducao.java   (classe abstrata)
│   ├── Musica.java
│   ├── Playlist.java
│   ├── Usuario.java          (classe abstrata)
│   ├── UsuarioFree.java
│   └── UsuarioPremium.java
├── servico
│   ├── Reproduzivel.java     (interface)
│   ├── Baixavel.java         (interface)
│   ├── GeradorRecomendacoes.java
│   └── StreamingMusica.java
└── util
    ├── FormatarTempo.java
    └── Validador.java
```

### Conceitos de POO Aplicados

**Herança**
- `ItemReproducao` → `Musica` e `Playlist`
- `Usuario` → `UsuarioFree` e `UsuarioPremium`
- Atributos `protected` permitem acesso controlado pelas subclasses; construtores usam `super()` para inicialização

**Classes Abstratas**
- `ItemReproducao` declara `reproduzir()` como abstrato, forçando implementação nas subclasses
- `Usuario` declara `getLimitePlaylists()` e `podeBaixar()` como abstratos

**Interfaces**
- `Reproduzivel` — contrato de controle de reprodução (`reproduzir`, `pausar`, `parar`, `getDuracaocompleta`), implementada por `Musica` e `Playlist`
- `Baixavel` — contrato de download (`baixar`, `removerDownload`, `estaBaixada`, `getTamanhoBaixados`), implementada por `UsuarioPremium`

**Polimorfismo e Sobrescrita (@Override)**
- `reproduzir()`, `pausar()`, `parar()` sobrescritos em `Musica` e `Playlist` com comportamentos distintos
- `adicionarPlaylist()` sobrescrito em `UsuarioFree` para incluir verificação de limite
- `exibirInfo()` sobrescrito em `Musica`, `Playlist` e `UsuarioPremium` usando `super.exibirInfo()` para reaproveitar a base

**Coleções e Composição**
- `Playlist` agrega `List<ItemReproducao>` e recalcula a duração total automaticamente a cada adição/remoção
- `UsuarioPremium` mantém `List<Musica>` para controle de downloads
- `GeradorRecomendacoes` mantém uma `List<Musica>` como biblioteca global

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 11 ou superior
- Terminal (cmd, PowerShell, bash)

### Compilação

```bash
# Na raiz do projeto, compile todos os arquivos Java
javac -d bin src/br/com/streaming/modelo/*.java \
           src/br/com/streaming/servico/*.java \
           src/br/com/streaming/util/*.java
```

### Execução

```bash
# Execute a classe principal
java -cp bin br.com.streaming.servico.StreamingMusica
```

### Compilação e execução (alternativa rápida com IDE)

Importe o projeto em uma IDE como IntelliJ IDEA ou Eclipse e execute a classe `StreamingMusica` diretamente.

---

## 👤 Autor

- **Nome:** Eduardo Quintino Filho
- **RGM:** 41775317

---

## 📅 Histórico de Checkpoints

### CP1 — Estrutura Procedural Base

Implementação inicial do sistema em estilo procedural, com menus de navegação e as funcionalidades centrais funcionando:

- Menu principal e navegação via terminal
- Cadastro completo com validações de entrada
- Listagem de músicas
- Busca por título
- Método de formatação de duração
- Busca por artista e por gênero
- Cálculo de estatísticas: duração total, média e gênero mais comum

---

### CP2 — Refatoração para Orientação a Objetos

Refatoração do código procedural do CP1 para usar **Orientação a Objetos**, criando classes que representam as entidades do sistema:

- `Musica` — representa uma música individual
- `Playlist` — agrupa músicas em uma coleção
- `Usuario` — gerencia playlists do usuário

---

### CP3 — Encapsulamento e Construtores

Aplicação de **encapsulamento** e **construtores** em todas as classes. Todos os atributos tornados `private`, acessados exclusivamente via getters e setters com validações. Construtores garantem que objetos sejam criados em estado válido desde o início:

- Atributos `private` com getters e setters validados
- Construtores que **inicializam** atributos com valores válidos e **garantem** que o objeto nunca esteja em estado inválido
- Suporte a sobrecarga de construtores e uso de `this()` para reutilização entre eles

---

### CP4 — Herança

Implementação de **hierarquias de classes** usando herança para suportar diferentes tipos de usuários (Free e Premium) com comportamentos distintos:

```
Usuario (classe base)
├── UsuarioFree  (subclasse)
└── UsuarioPremium (subclasse)
```

- Reutilização de código evitando duplicação
- Organização de classes relacionadas em hierarquia
- Subclasses especializam comportamentos da classe base
- Relação "é-um": `UsuarioFree` é-um `Usuario`

---

### CP5 — Polimorfismo

Aplicação de **polimorfismo** para permitir que diferentes tipos de usuários e playlists sejam tratados de forma genérica, mas com comportamentos específicos:

- Variável do tipo base referenciando objetos de subclasses
- Mesmo método com comportamentos diferentes em cada subclasse
- Código genérico funcionando com tipos específicos
- Maior flexibilidade e extensibilidade do sistema

```java
// Uma variável do tipo base pode referenciar objetos de subclasses
Usuario usuario = new UsuarioPremium("João", "joao@email.com", "Mensal");

// Chama a versão específica de UsuarioPremium
usuario.reproduzirMusica(musica); // "🎵 Reproduzindo em ALTA QUALIDADE..."
```

---

### CP6 — Interfaces, Pacotes e Finalização

Criação de **interfaces**, organização do código em **pacotes profissionais** e finalização do sistema completo demonstrando domínio de todos os conceitos de POO:

- ✅ Criação e implementação das interfaces `Reproduzivel` e `Baixavel`
- ✅ Organização do código em pacotes (`br.com.streaming.*`)
- ✅ Sistema completo e funcional
- ✅ Código limpo e boas práticas aplicadas
- ✅ README.md do projeto
- ✅ Domínio completo de POO demonstrado

> ❌ **Não exigido:** Javadoc (documentação HTML), testes unitários e interface gráfica — o sistema opera inteiramente via console/terminal.
