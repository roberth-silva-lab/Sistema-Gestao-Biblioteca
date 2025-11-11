package controller;

import model.EmprestimoModel;
import model.LivroModel;
import repository.EmprestimoRepository;
import repository.LivroRepository;

import java.time.LocalDate;
import java.util.List;

public class LivroController {

    private LivroRepository livroRepository;
    private EmprestimoRepository emprestimoRepository;

    public LivroController() {
        this.livroRepository = new LivroRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }


    public boolean salvarLivro(Long id, String titulo, String tema, String autor, String isbn, LocalDate dataPublicacao, int qtdDisponivel) {
        // ... (Seu método salvar está correto, sem mudanças) ...

        if (titulo == null || titulo.trim().isEmpty()) {
            System.err.println("Erro: O título não pode estar vazio.");
            return false;
        }
        if (autor == null || autor.trim().isEmpty()) {
            System.err.println("Erro: O autor não pode estar vazio.");
            return false;
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            System.err.println("Erro: O ISBN não pode estar vazio.");
            return false;
        }
        if (qtdDisponivel < 0) {
            System.err.println("Erro: A quantidade disponível não pode ser negativa.");
            return false;
        }

        LivroModel livroExistente = livroRepository.buscarPorIsbn(isbn);
        if (livroExistente != null) {

            if (id == null) {
                System.err.println("Erro: O ISBN informado já está cadastrado.");
                return false;
            }

            if (!livroExistente.getId().equals(id)) {
                System.err.println("Erro: O ISBN informado já pertence a outro livro.");
                return false;
            }
        }


        LivroModel livro = new LivroModel();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setTema(tema);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setDataPublicacao(dataPublicacao);
        livro.setQtdDisponivel(qtdDisponivel);


        try {
            livroRepository.salvar(livro);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar livro no controller: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirLivro(Long id) {
        if (id == null) {
            System.err.println("Erro: ID do livro não pode ser nulo para exclusão.");
            return false;
        }

        try {
            LivroModel livro = livroRepository.buscarPorId(id);
            if (livro == null) {
                System.err.println("Erro: Livro não encontrado para exclusão.");
                return false;
            }


            List<EmprestimoModel> emprestimosAtivos = emprestimoRepository.buscarAtivosPorLivro(livro);
            if (emprestimosAtivos != null && !emprestimosAtivos.isEmpty()) {
                System.err.println("Erro: Não é possível excluir o livro. Ele possui " + emprestimosAtivos.size() + " empréstimo(s) ativo(s).");
                return false;
            }

            List<EmprestimoModel> historicoEmprestimos = emprestimoRepository.buscarTodosPorLivro(livro);
            if (historicoEmprestimos != null) {
                for (EmprestimoModel emprestimoAntigo : historicoEmprestimos) {
                    emprestimoRepository.excluir(emprestimoAntigo.getId());
                }
            }
            livroRepository.excluir(id);
            return true;

        } catch (Exception e) {
            System.err.println("Erro ao excluir livro (possível falha ao limpar histórico): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public LivroModel buscarLivroPorId(Long id) {
        return livroRepository.buscarPorId(id);
    }

    public List<LivroModel> listarTodosLivros() {
        return livroRepository.listarTodos();
    }

    public List<LivroModel> listarLivrosDisponiveis() {
        return livroRepository.listarDisponiveis();
    }


    public List<LivroModel> buscarLivrosPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return livroRepository.listarTodos();
        }
        return livroRepository.buscarPorTitulo(titulo);
    }
}