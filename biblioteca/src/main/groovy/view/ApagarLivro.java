package view;

import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ApagarLivro {
    private JPanel ListaApagaAluno;
    private JTextField textAlunoLivro;
    private JButton buttonBuscarLivroA;
    private JTable tableLivrosA;
    private JButton buttonApagarLivro;

    private LivroController livroController;
    private DefaultTableModel tableModel;

    public ApagarLivro() {
        this.livroController = new LivroController();
        configurarTabela();
        carregarLivrosNaTabela();

        buttonBuscarLivroA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarLivros();
            }
        });


        buttonApagarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirLivroSelecionado();
            }
        });
    }


    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new Object[][]{},

                new String[]{"ID", "Título", "Autor", "ISBN", "Qtd. Disponível"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLivrosA.setModel(tableModel);
    }


    private void preencherTabela(List<LivroModel> livros) {
        tableModel.setRowCount(0);
        if (livros == null) return;

        for (LivroModel livro : livros) {
            tableModel.addRow(new Object[]{
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.getQtdDisponivel()
            });
        }
    }


    private void carregarLivrosNaTabela() {
        List<LivroModel> livros = livroController.buscarLivrosPorTitulo("");
        preencherTabela(livros);
    }

    private void buscarLivros() {
        String titulo = textAlunoLivro.getText();
        List<LivroModel> livros = livroController.buscarLivrosPorTitulo(titulo);
        preencherTabela(livros);
    }


    private void excluirLivroSelecionado() {
        int linhaSelecionada = tableLivrosA.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(ListaApagaAluno,
                    "Por favor, selecione um livro na tabela.",
                    "Nenhum Livro Selecionado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) tableModel.getValueAt(linhaSelecionada, 0);
        String titulo = (String) tableModel.getValueAt(linhaSelecionada, 1);

        Object[] opcoes = {"Sim, excluir", "Não, cancelar"};
        int confirmacao = JOptionPane.showOptionDialog(
                ListaApagaAluno,

                "Tem certeza que deseja excluir o livro: " + titulo + "?\n(O histórico de empréstimos dele também será apagado)",
                "Confirmar Exclusão",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opcoes,
                opcoes[1]
        );

        if (confirmacao == 0) {

            boolean sucesso = livroController.excluirLivro(id);

            if (sucesso) {
                JOptionPane.showMessageDialog(ListaApagaAluno,
                        "Livro e seu histórico foram excluídos com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                buscarLivros();
            } else {

                JOptionPane.showMessageDialog(ListaApagaAluno,
                        "Não foi possível excluir o livro.\nVerifique se ele possui empréstimos ATIVOS (pendentes).",
                        "Erro ao Excluir",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JPanel getPanel() {
        return ListaApagaAluno;
    }
}

