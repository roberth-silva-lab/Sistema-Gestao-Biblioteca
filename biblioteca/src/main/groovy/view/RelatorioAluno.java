package view;

import model.AlunoModel;
import controller.AlunoController;
import model.EmprestimoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RelatorioAluno {
    private JPanel RelatorioJpane;
    private JTextField textBuscandoRelatos;
    private JButton buttonRelatorio;
    private JTable table1;


    private AlunoController alunoController;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioAluno() {
        this.alunoController = new AlunoController();
        configurarTabela();

        buttonRelatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio();
            }
        });
    }


    private void configurarTabela() {
        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Empr.", "Livro", "Data Empréstimo", "Data Devolução", "Danificado?"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);
    }

    private void preencherTabela(List<EmprestimoModel> historico) {
        tableModel.setRowCount(0);
        if (historico == null || historico.isEmpty()) {
            JOptionPane.showMessageDialog(RelatorioJpane,
                    "Este aluno não possui nenhum empréstimo no histórico.",
                    "Relatório Vazio",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (EmprestimoModel emp : historico) {
            String dataDevolucao;
            if (emp.getDataDevolucaoReal() != null) {
                dataDevolucao = emp.getDataDevolucaoReal().format(dateFormatter);
            } else {
                dataDevolucao = "PENDENTE";
            }


            String danificado = emp.isComAvaria() ? "Sim" : "Não";

            tableModel.addRow(new Object[]{
                    emp.getId(),
                    emp.getLivro().getTitulo(),
                    emp.getDataEmprestimo().format(dateFormatter),
                    dataDevolucao,
                    danificado
            });
        }
    }


    private void gerarRelatorio() {
        String nome = textBuscandoRelatos.getText();
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(RelatorioJpane,
                    "Por favor, digite um nome para buscar.",
                    "Erro de Busca",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<AlunoModel> alunosEncontrados = alunoController.buscarAlunosPorNome(nome);


        if (alunosEncontrados == null || alunosEncontrados.isEmpty()) {
            JOptionPane.showMessageDialog(RelatorioJpane,
                    "Nenhum aluno encontrado com este nome.",
                    "Erro de Busca",
                    JOptionPane.ERROR_MESSAGE);
            tableModel.setRowCount(0);
            return;
        }
        if (alunosEncontrados.size() > 1) {
            JOptionPane.showMessageDialog(RelatorioJpane,
                    "Mais de um aluno encontrado com este nome.\nPor favor, seja mais específico (use o nome completo).",
                    "Erro de Busca",
                    JOptionPane.WARNING_MESSAGE);
            tableModel.setRowCount(0);
            return;
        }


        AlunoModel aluno = alunosEncontrados.get(0);


        List<EmprestimoModel> historico = alunoController.getHistoricoCompleto(aluno);


        preencherTabela(historico);
    }

    public JPanel getPanel() {
        return RelatorioJpane;
    }
}