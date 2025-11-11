# 📚 Sistema de Gestão de Biblioteca (Java Swing & Hibernate)

Projeto de Sistema de Gestão de Biblioteca desenvolvido em Java Swing, utilizando o padrão de arquitetura MVCR (Model, View, Controller, Repository) e o ORM Hibernate para interação com o banco de dados.

---

## 🚀 1. Funcionalidades do sistema

O sistema permite o gerenciamento completo de livros e alunos, além do controle de empréstimos e devoluções.

### 📖 Cadastro de Livros
O sistema permite que novos livros sejam cadastrados, editados e excluídos.

| Atributo | Detalhe |
| :--- | :--- |
| Título | Título principal do livro. |
| Tema | Gênero ou categoria (ex: Ficção, Técnico). |
| Autor | Autor principal. |
| ISBN | Código ISBN do livro (único). |
| Data de publicação | Data de lançamento (com máscara `dd/MM/yyyy`). |
| Quantidade disponível | Nº de exemplares em estoque. |
| Número de identificação | Chave primária (ID). |

### 👤 Cadastro de Alunos
O sistema deve permitir o cadastro de novos alunos.

| Atributo | Detalhe |
| :--- | :--- |
| Nome | Nome completo do aluno. |
| Sexo | Gênero do aluno. |
| Número do celular | Telefone de contato (com máscara `(xx) xxxxx-xxxx`). |
| E-mail | Endereço de e-mail (único). |
| Número de identificação | Chave primária (ID). |

### ➡️ Empréstimo de Livros
Um aluno pode pegar livros emprestados, contanto que as regras de negócio sejam atendidas. O sistema registra:
* Aluno que pegou o livro.
* Data do empréstimo (automática).
* Data de devolução prevista (automática).
* Data de devolução real (preenchida ao final).

### ⬅️ Devolução de Livros
O sistema permite que o aluno devolva os livros, atualizando o registro de empréstimo (com a data de devolução real) e aumentando a quantidade de exemplares do livro de volta ao estoque.

---

## 🏗️ 2. Camadas do sistema (Arquitetura MVCR)

* **Model:** 📦 Representa os dados do sistema (classes `AlunoModel`, `LivroModel`, `EmprestimoModel`).
* **Repository:** 🗄️ Controla a interação direta com o banco de dados (Hibernate/JPA). É a única camada que "fala" JPQL.
* **View:** 🖥️ Responsável pela interface do usuário (Telas em Java Swing, como `CadastrarAluno`, `ApagarLivro`, etc.).
* **Controller:** 🧠 Orquestra a aplicação. Controla a interação entre a View e o Repository, gerenciando as operações e regras de negócio.

---

## 📜 3. Regras de Negócio Implementadas

🚫 **Limite de Empréstimos:** Um aluno pode pegar até 5 livros emprestados ao mesmo tempo.
✅ **Disponibilidade:** Um livro só pode ser emprestado se houver exemplares disponíveis (quantidade > 0).
⏰ **Prazo:** O prazo máximo de empréstimo é de 14 dias.
💸 **Multas:** O sistema calcula multas em caso de devolução atrasada (R$ 1,50/dia) ou por avarias (R$ 25,00).
🗑️ **Exclusão Segura (Cascade Manual):** * Um Aluno ou Livro **não pode** ser excluído se tiver **empréstimos ativos** (pendentes).
* Se o item tiver apenas um *histórico* (empréstimos já devolvidos), o Controller apaga o histórico primeiro para depois apagar o item, garantindo a integridade do banco de dados (evitando erros de Foreign Key).

---

## ✅ 4. Tarefas e Funcionalidades Implementadas

- [x] **CRUD de Livros:** Implementação das operações de Criar, Ler, Atualizar e Deletar livros.
- [x] **CRUD de Alunos:** Implementação das operações de Criar, Ler, Atualizar e Deletar alunos.
- [x] **Fazer Empréstimo:** Implementação da lógica de empréstimo, validando as regras de negócio.
- [x] **Registrar Devolução:** Implementação da lógica de devolução.
- [x] **Listagens e Relatórios:** Telas para listar livros disponíveis e empréstimos ativos.
- [x] **Cálculo de Multas:** Recurso para calcular multas (atraso/avaria) no momento da devolução.
- [x] **UI/UX:** Tradução dos botões de confirmação (`JOptionPane`) para Português, melhorando a experiência do usuário.