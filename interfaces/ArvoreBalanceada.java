package interfaces;

/**
 * Interface comum para Árvores Binárias de Busca Balanceadas
 * utilizadas no Desafio de Engenharia de Software da Amazon (Black Friday).
 */
public interface ArvoreBalanceada {
    // Inserção de elementos
    void inserir(int valor);
    
    // Remoção de elementos
    void remover(int valor);
    
    // Busca de elementos
    boolean buscar(int valor);
    
    // Percurso em ordem, apresentando os elementos em ordem crescente
    void imprimirEmOrdem();
    
    // Impressão ou visualização da estrutura da árvore
    void imprimirEstrutura();

    // Altura total da árvore
    int getAltura();

    // Total de rotações realizadas para balanceamento
    int getTotalRotacoes();

    // Quantidade total de nós presentes na árvore
    int getQuantidadeNos();

    // Verifica se a árvore está vazia
    boolean estaVazia();

    // Reinicia contadores de rotações e estatísticas
    void resetarEstatisticas();

    // Valida se as propriedades invariantes da estrutura estão preservadas
    boolean validarPropriedades();
}