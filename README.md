Sistema de Gestão de Biblioteca (Java Swing & Hibernate)

Projeto de Sistema de Gestão de Biblioteca desenvolvido em Java Swing, utilizando o padrão de arquitetura MVCR (Model, View, Controller, Repository) e o ORM Hibernate para interação com o banco de dados.

1. Funcionalidades do sistema

O sistema permite o gerenciamento de livros e usuários, além de permitir o empréstimo e devolução de livros.

Cadastro de livros

O sistema deve permitir que novos livros sejam cadastrados. Cada livro deve ter:

Título

Tema

Autor

ISBN

Data de publicação (com máscara dd/MM/yyyy)

Quantidade disponível (exemplares)

Número de identificação (id)

Cadastro de usuários

O sistema deve permitir o cadastro de novos usuários. Cada usuário deve ter:

Nome

Sexo

Número do celular (com máscara (xx) xxxxx-xxxx)

E-mail (com máscara de e-mail)

Número de identificação (id)

Empréstimo de livros

Um usuário deve ser capaz de pegar livros emprestados, contanto que haja exemplares disponíveis. O sistema deve registrar:

Usuário que pegou o livro emprestado

Data do empréstimo (com máscara dd/MM/yyyy)

Data de devolução prevista

Data de devolução

Número de identificação (id)

Devolução de livros

O sistema deve permitir que o usuário devolva os livros que foram emprestados.

1. Camadas do sistema (Arquitetura MVCR)

Model: Representa os dados do sistema (classes Livro, Usuario, Emprestimo).

Repository: Controla a interação com o banco de dados (Hibernate). Todos os métodos que interagem com o banco de dados são apresentados nessa camada.

View: Responsável pela interface do usuário (Telas em Java Swing).

Controller: Controla a interação entre a View e o Repository, gerenciando as operações e regras de negócio.

1. Regras de Negócio

Um usuário pode pegar até 5 livros emprestados ao mesmo tempo.

Um livro só pode ser emprestado se houver exemplares disponíveis (quantidade > 0).

O prazo máximo de empréstimo é de 14 dias. Após esse período, o sistema deve sinalizar que o livro está atrasado.

1. Tarefas e Funcionalidades Implementadas

[x] CRUD de Livros: Implementação das operações de Criar, Ler, Atualizar e Deletar livros.

[x] CRUD de Usuários: Implementação das operações de Criar, Ler, Atualizar e Deletar usuários.

[x] Fazer Empréstimo: Implementação da lógica de empréstimo, validando as regras de negócio.

[x] Registrar Devolução: Implementação da lógica de devolução.

[x] Listar Livros Disponíveis: Filtro para mostrar apenas livros com exemplares disponíveis.

[x] Cálculo de Multas: Recurso para calcular multas em caso de devolução atrasada.