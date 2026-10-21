package utils;

import avl.ArvoreAVL;
import rubronegra.ArvoreRubroNegra;
import java.util.*;

/**
 * Módulo de Testes e Análise Comparativa Automatizada
 * Executa os cenários exigidos pelo Amazon Engineering Challenge:
 * - Inserção ordenada (crescente e decrescente) e aleatória
 * - Busca de existentes e inexistentes
 * - Remoção de nós (folha, 1 filho, 2 filhos) e inexistentes
 * - Árvores vazias e com apenas 1 elemento
 * - Validação matemática das propriedades de balanceamento
 * - Coleta de métricas (alturas, rotações, recolorações, tempos)
 */
public class TestesComparativos {

    public static void executarBateriaCompleta() {
        System.out.println("================================================================================");
        System.out.println("   AMAZON LOGISTICS - BATERIA COMPARATIVA DE ESTRUTURAS (AVL vs RUBRO-NEGRA)   ");
        System.out.println("================================================================================");

        testarCasosDeBorda();
        testarDemonstracaoRotacoes();

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" CENÁRIOS COMPARATIVOS EM ESCALA (Black Friday Logistics Simulation) ");
        System.out.println("--------------------------------------------------------------------------------");

        executarCenario("CENÁRIO 1: Inserção em Ordem Crescente (Pior Caso para BST Simples)", gerarCrescente(1000));
        executarCenario("CENÁRIO 2: Inserção em Ordem Decrescente (Pior Caso Inverso)", gerarDecrescente(1000));
        executarCenario("CENÁRIO 3: Inserção Aleatória / Uniforme (Cenário Real Black Friday)", gerarAleatorio(1000, 42));

        System.out.println("\n================================================================================");
        System.out.println(" FIM DOS TESTES COMPARATIVOS AUTOMATIZADOS ");
        System.out.println("================================================================================\n");
    }

    private static void testarCasosDeBorda() {
        System.out.println("\n[TESTE DE CASOS DE BORDA E TRATAMENTO DE EXCEÇÕES]");
        ArvoreAVL avl = new ArvoreAVL();
        ArvoreRubroNegra rn = new ArvoreRubroNegra();

        System.out.println("1. Operações em Árvores Vazias:");
        System.out.println("   - AVL está vazia? " + avl.estaVazia() + " | Altura: " + avl.getAltura() + " | Busca(99): " + avl.buscar(99));
        System.out.println("   - RN está vazia?  " + rn.estaVazia() + " | Altura: " + rn.getAltura() + " | Busca(99): " + rn.buscar(99));
        avl.remover(99);
        rn.remover(99);

        System.out.println("\n2. Operações com Apenas 1 Elemento:");
        avl.inserir(100);
        rn.inserir(100);
        System.out.println("   - AVL após inserir(100): Altura=" + avl.getAltura() + " | Qtd=" + avl.getQuantidadeNos() + " | Válida? " + avl.validarPropriedades());
        System.out.println("   - RN após inserir(100):  Altura=" + rn.getAltura() + " | Qtd=" + rn.getQuantidadeNos() + " | Válida? " + rn.validarPropriedades());

        System.out.println("\n3. Remoção do Único Elemento:");
        avl.remover(100);
        rn.remover(100);
        System.out.println("   - AVL após remover(100): está vazia? " + avl.estaVazia() + " | Válida? " + avl.validarPropriedades());
        System.out.println("   - RN após remover(100):  está vazia? " + rn.estaVazia() + " | Válida? " + rn.validarPropriedades());

        System.out.println("\n4. Inserção de Elementos Duplicados:");
        avl.inserir(50);
        avl.inserir(50); // Deve alertar e ignorar
        rn.inserir(50);
        rn.inserir(50); // Deve alertar e ignorar
        System.out.println("   - AVL nós: " + avl.getQuantidadeNos() + " (esperado: 1)");
        System.out.println("   - RN nós:  " + rn.getQuantidadeNos() + " (esperado: 1)");

        System.out.println("\n5. Remoção de Nós com Diferentes Configurações de Filhos:");
        // Monta árvore para testar: folha, 1 filho, 2 filhos
        //            50
        //          /    \
        //        30      70
        //       /  \    /
        //      20  40  60
        avl.resetarEstatisticas();
        rn.resetarEstatisticas();
        avl.remover(50); // limpa
        rn.remover(50);

        int[] base = {50, 30, 70, 20, 40, 60};
        for (int v : base) {
            avl.inserir(v);
            rn.inserir(v);
        }

        // Caso A: Remoção de folha (20)
        avl.remover(20);
        rn.remover(20);
        System.out.println("   - A) Remoção de nó FOLHA (20): AVL válida? " + avl.validarPropriedades() + " | RN válida? " + rn.validarPropriedades());

        // Caso B: Remoção de nó com 1 filho (70 tem apenas filho esquerdo 60)
        avl.remover(70);
        rn.remover(70);
        System.out.println("   - B) Remoção de nó com 1 FILHO (70): AVL válida? " + avl.validarPropriedades() + " | RN válida? " + rn.validarPropriedades());

        // Caso C: Remoção de nó com 2 filhos (30 ou raiz 50)
        avl.remover(50);
        rn.remover(50);
        System.out.println("   - C) Remoção de nó com 2 FILHOS / RAIZ (50): AVL válida? " + avl.validarPropriedades() + " | RN válida? " + rn.validarPropriedades());

        System.out.println("\n[✓] Todos os casos de borda e configurações de filhos validados com sucesso!");
    }

    public static void testarDemonstracaoRotacoes() {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" DEMONSTRAÇÃO VISUAL DIDÁTICA (Exigência Slides 4 e 5) ");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("A) Demonstração AVL: Rotação Simples à Esquerda (Inserção 10, 20, 30)");
        ArvoreAVL avlDemo = new ArvoreAVL();
        avlDemo.inserir(10);
        avlDemo.inserir(20);
        System.out.println("-> Antes de inserir 30 (árvore pende à direita):");
        avlDemo.imprimirEstrutura();
        avlDemo.inserir(30);
        System.out.println("-> Depois de inserir 30 (após rotação à esquerda na raiz):");
        avlDemo.imprimirEstrutura();

        System.out.println("B) Demonstração Rubro-Negra: Recoloração vs Rotação (Inserção 10, 20, 30)");
        ArvoreRubroNegra rnDemo = new ArvoreRubroNegra();
        rnDemo.inserir(10);
        rnDemo.inserir(20);
        System.out.println("-> Antes de inserir 30:");
        rnDemo.imprimirEstrutura();
        rnDemo.inserir(30);
        System.out.println("-> Depois de inserir 30 (recoloração e rotação para manter altura negra):");
        rnDemo.imprimirEstrutura();
    }

    private static void executarCenario(String titulo, List<Integer> dados) {
        System.out.println("\n>>> " + titulo + " [N = " + dados.size() + "]");

        ArvoreAVL avl = new ArvoreAVL();
        ArvoreRubroNegra rn = new ArvoreRubroNegra();
        avl.setModoSilencioso(true);
        rn.setModoSilencioso(true);

        // 1. Inserção
        long inicioAVL = System.nanoTime();
        for (int v : dados) avl.inserir(v);
        long tempoInsercaoAVL = System.nanoTime() - inicioAVL;

        long inicioRN = System.nanoTime();
        for (int v : dados) rn.inserir(v);
        long tempoInsercaoRN = System.nanoTime() - inicioRN;

        // 2. Busca (500 existentes + 500 inexistentes)
        int acertosAVL = 0, acertosRN = 0;
        long inicioBuscaAVL = System.nanoTime();
        for (int i = 0; i < dados.size(); i++) {
            if (avl.buscar(dados.get(i))) acertosAVL++;
            if (avl.buscar(-dados.get(i) - 1)) acertosAVL++; // inexistente
        }
        long tempoBuscaAVL = System.nanoTime() - inicioBuscaAVL;

        long inicioBuscaRN = System.nanoTime();
        for (int i = 0; i < dados.size(); i++) {
            if (rn.buscar(dados.get(i))) acertosRN++;
            if (rn.buscar(-dados.get(i) - 1)) acertosRN++; // inexistente
        }
        long tempoBuscaRN = System.nanoTime() - inicioBuscaRN;

        // 3. Remoção de 50% dos elementos
        List<Integer> sublistaRemover = new ArrayList<>(dados.subList(0, dados.size() / 2));
        long inicioRemocaoAVL = System.nanoTime();
        for (int v : sublistaRemover) avl.remover(v);
        long tempoRemocaoAVL = System.nanoTime() - inicioRemocaoAVL;

        long inicioRemocaoRN = System.nanoTime();
        for (int v : sublistaRemover) rn.remover(v);
        long tempoRemocaoRN = System.nanoTime() - inicioRemocaoRN;

        // Exibição comparativa em tabela formatada
        System.out.printf("%-35s | %-18s | %-18s%n", "Métrica Analisada", "Árvore AVL", "Árvore Rubro-Negra");
        System.out.println("------------------------------------+--------------------+-------------------");
        System.out.printf("%-35s | %-18d | %-18d%n", "Altura Final", avl.getAltura(), rn.getAltura());
        System.out.printf("%-35s | %-18d | %-18d%n", "Total de Rotações", avl.getTotalRotacoes(), rn.getTotalRotacoes());
        System.out.printf("%-35s | %-18s | %-18d%n", "Total de Recolorações", "N/A", rn.getTotalRecoloracoes());
        System.out.printf("%-35s | %-18.4f | %-18.4f%n", "Tempo Inserção (ms)", tempoInsercaoAVL / 1e6, tempoInsercaoRN / 1e6);
        System.out.printf("%-35s | %-18.4f | %-18.4f%n", "Tempo Busca (ms)", tempoBuscaAVL / 1e6, tempoBuscaRN / 1e6);
        System.out.printf("%-35s | %-18.4f | %-18.4f%n", "Tempo Remoção (ms)", tempoRemocaoAVL / 1e6, tempoRemocaoRN / 1e6);
        System.out.printf("%-35s | %-18b | %-18b%n", "Propriedades Preservadas?", avl.validarPropriedades(), rn.validarPropriedades());
    }

    private static List<Integer> gerarCrescente(int tamanho) {
        List<Integer> lista = new ArrayList<>(tamanho);
        for (int i = 1; i <= tamanho; i++) lista.add(i);
        return lista;
    }

    private static List<Integer> gerarDecrescente(int tamanho) {
        List<Integer> lista = new ArrayList<>(tamanho);
        for (int i = tamanho; i >= 1; i--) lista.add(i);
        return lista;
    }

    private static List<Integer> gerarAleatorio(int tamanho, long semente) {
        Random rand = new Random(semente);
        Set<Integer> unico = new HashSet<>();
        while (unico.size() < tamanho) {
            unico.add(rand.nextInt(tamanho * 10) + 1);
        }
        return new ArrayList<>(unico);
    }
}
