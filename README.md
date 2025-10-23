# 📚 Revisão: JPA e Hibernate com Jakarta EE
###### Projeto de estudo baseado no curso DevSuperior - Aulão #006

> **Este projeto é uma revisão dos conceitos de JPA (Java Persistence API) e Hibernate**, adaptado para usar **Jakarta EE** ao invés das antigas dependências `javax.persistence`.

## 🎯 Objetivo do Projeto

Este repositório serve como material de **consulta e revisão** dos principais conceitos de:
- **JPA (Java Persistence API)**
- **Hibernate ORM 6.x**
- **Jakarta Persistence 3.0**
- **Mapeamento Objeto-Relacional (ORM)**
- **CRUD básico com banco de dados MySQL**

---

## 📹 Referência Original

Baseado no **Aulão #006** da DevSuperior:

[![Image](https://img.youtube.com/vi/CAP1IPgeJkw/mqdefault.jpg "Vídeo no Youtube")](https://youtu.be/CAP1IPgeJkw)

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 11+ | Linguagem de programação |
| **Maven** | 3.x | Gerenciador de dependências |
| **Hibernate ORM** | 6.2.7.Final | Implementação JPA |
| **Jakarta Persistence** | 3.0 | API de persistência (substitui javax.persistence) |
| **MySQL** | 8.0+ | Banco de dados relacional |
| **MySQL Connector/J** | 8.0.33 | Driver JDBC para MySQL |

### ⚠️ Importante: Migração javax → jakarta

Este projeto foi **atualizado** para usar:
- ✅ `jakarta.persistence.*`
- ❌ ~~`javax.persistence.*`~~ 

---

## 📁 Estrutura do Projeto

```
aulao006/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── aplicacao/
│   │   │   │   └── Programa.java          # Classe principal com exemplos
│   │   │   └── dominio/
│   │   │       └── Pessoa.java            # Entidade JPA
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml        # Configuração JPA
├── pom.xml                                # Dependências Maven
└── README.md                              # Este arquivo
```

---

## 🧩 Conceitos Abordados

### 1. **Mapeamento Objeto-Relacional (ORM)**

O ORM resolve o problema de incompatibilidade entre o paradigma orientado a objetos e o modelo relacional de bancos de dados.

**Problemas tratados pelo ORM:**
- ✅ Conversão de objetos Java para tabelas SQL
- ✅ Contexto de persistência (objetos MANAGED vs DETACHED)
- ✅ Mapa de identidade
- ✅ Carregamento tardio (lazy loading)
- ✅ Gerenciamento de transações

### 2. **JPA - Java Persistence API**

JPA é uma **especificação** (não uma implementação) que define como fazer ORM em Java.

- 📋 **Especificação**: Jakarta Persistence 3.0 (JSR 338)
- 🔧 **Implementação**: Hibernate, EclipseLink, OpenJPA



### 3. **EntityManager - O Gerenciador de Persistência**

O `EntityManager` é responsável por gerenciar o ciclo de vida das entidades:

```java
EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
EntityManager em = emf.createEntityManager();
```

**Operações principais:**
- `persist()` - INSERT (adiciona novo registro)
- `find()` - SELECT (busca por ID)
- `merge()` - UPDATE (atualiza registro existente)
- `remove()` - DELETE (remove registro)

### 5. **Estados de uma Entidade**

- **NEW (Transient)** - Objeto criado mas não gerenciado pelo EntityManager
- **MANAGED** - Objeto sendo monitorado pelo EntityManager (sincronizado com o BD)
- **DETACHED** - Objeto que estava MANAGED mas o EntityManager foi fechado
- **REMOVED** - Objeto marcado para remoção

---

## ⚙️ Configuração

### persistence.xml

Arquivo de configuração localizado em `src/main/resources/META-INF/persistence.xml`:

```xml
<persistence-unit name="exemplo-jpa" transaction-type="RESOURCE_LOCAL">
    <properties>
        <!-- Conexão com MySQL -->
        <property name="jakarta.persistence.jdbc.url"
            value="jdbc:mysql://localhost/JPA_Hibernate?useSSL=false&amp;serverTimezone=UTC" />
        <property name="jakarta.persistence.jdbc.driver" 
            value="com.mysql.cj.jdbc.Driver" />
        <property name="jakarta.persistence.jdbc.user" value="" />
        <property name="jakarta.persistence.jdbc.password" value="" />
        
        <!-- Hibernate -->
        <property name="hibernate.hbm2ddl.auto" value="update" />
        <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect" />
        <property name="hibernate.show_sql" value="true" />
        <property name="hibernate.format_sql" value="true" />
    </properties>
</persistence-unit>
```

**Propriedades importantes:**
- `hibernate.hbm2ddl.auto=update` - Cria/atualiza tabelas automaticamente
- `hibernate.show_sql=true` - Exibe SQL no console (útil para debug)
- `hibernate.format_sql=true` - Formata o SQL exibido

---

## 🚀 Como Executar

### Pré-requisitos

1. **Java 11+** instalado
2. **Maven** instalado
3. **MySQL** rodando na porta 3306
4. Criar banco de dados: `CREATE DATABASE JPA_Hibernate;`
5. Criar usuário: `CREATE USER 'xxx'@'localhost' IDENTIFIED BY 'xxxx';`
6. Dar permissões de usuário

### Executar o projeto

```bash
# Limpar e compilar
mvn clean compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass="aplicacao.Programa"
```

### Testando as operações

No arquivo `Programa.java`, você encontrará três seções comentadas:

1. **PERSISTENCE** - Insere dados no banco (INSERT)
2. **GET** - Busca dados por ID (SELECT)
3. **DELETE** - Remove dados (DELETE)

Descomente a seção que deseja testar de cada vez.

---

## ⚠️ Pontos de Atenção

### 1. **GenerationType.IDENTITY**
Com esta estratégia, o ID é gerado pelo banco (AUTO_INCREMENT). O Hibernate faz o INSERT imediatamente ao chamar `persist()`.


### 4. **Objeto deve estar MANAGED para remoção**
```java
// ✅ CORRETO
Pessoa pessoa = em.find(Pessoa.class, 1);  // MANAGED
em.getTransaction().begin();
em.remove(pessoa);  // Funciona!
em.getTransaction().commit();

// ❌ ERRO
Pessoa pessoa = new Pessoa(1, "Nome", "email");  // NEW, não MANAGED
em.remove(pessoa);  // Erro: objeto não está sendo gerenciado
```

---

## 🐛 Troubleshooting

### "package jakarta.persistence does not exist"
- ✅ Execute `mvn clean install` para baixar as dependências
- ✅ Verifique se está usando Hibernate 6.x no `pom.xml`
- ✅ Recarregue o projeto Maven na IDE

### Erro de conexão MySQL
- ✅ Verifique se o MySQL está rodando
- ✅ Confirme usuário e senha no `persistence.xml`
- ✅ Verifique se o banco de dados foi criado

---

## 📚 Recursos Adicionais

- [Documentação Hibernate 6](https://hibernate.org/orm/documentation/6.2/)
- [Jakarta Persistence Specification](https://jakarta.ee/specifications/persistence/3.0/)
- [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/8.0/en/)
- [Maven Guide](https://maven.apache.org/guides/)
- [DevSuperior - Curso Completo](https://devsuperior.com.br)

---

## 🎓 Aprendizados Principais

1. **JPA é uma especificação**, Hibernate é uma implementação
2. **EntityManager** gerencia o ciclo de vida das entidades
3. Entidades podem estar em 4 estados: NEW, MANAGED, DETACHED, REMOVED
4. Objetos devem estar **MANAGED** para serem removidos
6. A migração de **javax → jakarta** é necessária com Hibernate 6+

---

## 🤝 Contribuições

Este é um projeto de estudo pessoal baseado no material da **DevSuperior**, mas sugestões são bem-vindas!

---

**Desenvolvido como material de revisão e consulta 📖**

