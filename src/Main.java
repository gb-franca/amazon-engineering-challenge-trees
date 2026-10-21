import java.util.Scanner;
import avl.ArvoreAVL;
import rubronegra.ArvoreRubroNegra;
import utils.TestesComparativos;

/**
 * Ponto de entrada do Sistema de Logística da Amazon (Black Friday Challenge)
 * Permite ao operador/engenheiro alternar entre Árvore AVL e Rubro-Negra,
 * realizar operações interativas e executar a suíte de testes comparativos.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n==========================================================");
            System.out.println("   AMAZON LOGISTICS ENGINEERING - BLACK FRIDAY TRACKER   ");
            System.out.println("==========================================================");
            System.out.println("1. Modo Interativo: Árvore AVL");
            System.out.println("2. Modo Interativo: Árvore Rubro-Negra");
            System.out.println("3. Bateria de Testes Comparativos e Métricas (Benchmark)");
            System.out.println("4. Demonstração Visual de Rotações e Balanceamento (Passo a Passo)");
            System.out.println("0. Encerrar Sistema");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1:
                    menuOperacoesAVL(scanner);
                    break;
                case 2:
                    menuOperacoesRubroNegra(scanner);
                    break;
                case 3:
                    TestesComparativos.executarBateriaCompleta();
                    break;
                case 4:
                    TestesComparativos.testarDemonstracaoRotacoes();
                    break;
                case 0:
                    System.out.println("\nEncerrando o sistema da Amazon Logistics. Até logo!");
                    break;
                default:
                    System.out.println("\n[!] Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void menuOperacoesAVL(Scanner scanner) {
        ArvoreAVL avl = new ArvoreAVL();
        int opcaoAVL;

        do {
            System.out.println("\n--- Painel de Controle: Árvore AVL ---");
            System.out.println("1. Inserir identificador de encomenda");
            System.out.println("2. Remover identificador de encomenda");
            System.out.println("3. Buscar identificador");
            System.out.println("4. Percurso Em Ordem (Crescente)");
            System.out.println("5. Visualizar Estrutura da Árvore");
            System.out.println("6. Exibir Métricas e Validação de Propriedades");
            System.out.println("7. Carregar dados de demonstração (50, 30, 70, 20, 40)");
            System.out.println("8. Limpar árvore");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Opção: ");

            opcaoAVL = lerInteiro(scanner);

            switch (opcaoAVL) {
                case 1:
                    System.out.print("Digite o identificador para inserir: ");
                    int valIns = lerInteiro(scanner);
                    avl.inserir(valIns);
                    System.out.println("Elemento processado. Rotações totais: " + avl.getTotalRotacoes());
                    break;
                case 2:
                    System.out.print("Digite o identificador para remover: ");
                    int valRem = lerInteiro(scanner);
                    avl.remover(valRem);
                    break;
                case 3:
                    System.out.print("Digite o identificador para buscar: ");
                    int valBusca = lerInteiro(scanner);
                    if (avl.buscar(valBusca)) {
                        System.out.println("[✓] Encomenda ID " + valBusca + " LOCALIZADA no sistema!");
                    } else {
                        System.out.println("[X] Encomenda ID " + valBusca + " NÃO encontrada.");
                    }
                    break;
                case 4:
                    avl.imprimirEmOrdem();
                    break;
                case 5:
                    avl.imprimirEstrutura();
                    break;
                case 6:
                    System.out.println("\n=== Diagnóstico da Árvore AVL ===");
                    System.out.println("Quantidade de nós: " + avl.getQuantidadeNos());
                    System.out.println("Altura da árvore: " + avl.getAltura());
                    System.out.println("Total de rotações: " + avl.getTotalRotacoes() + " (Simples: " + avl.getRotacoesSimples() + ", Duplas: " + avl.getRotacoesDuplas() + ")");
                    System.out.println("Propriedades AVL preservadas (|FB| <= 1): " + (avl.validarPropriedades() ? "[VÁLIDA]" : "[VIOLADA]"));
                    break;
                case 7:
                    int[] demo = {50, 30, 70, 20, 40};
                    for (int d : demo) avl.inserir(d);
                    System.out.println("[✓] 5 identificadores de demonstração inseridos!");
                    break;
                case 8:
                    avl = new ArvoreAVL();
                    System.out.println("[✓] Árvore reinicializada!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("[!] Opção inválida!");
            }
        } while (opcaoAVL != 0);
    }

    private static void menuOperacoesRubroNegra(Scanner scanner) {
        ArvoreRubroNegra rn = new ArvoreRubroNegra();
        int opcaoRN;

        do {
            System.out.println("\n--- Painel de Controle: Árvore Rubro-Negra ---");
            System.out.println("1. Inserir identificador de encomenda");
            System.out.println("2. Remover identificador de encomenda");
            System.out.println("3. Buscar identificador");
            System.out.println("4. Percurso Em Ordem (Crescente)");
            System.out.println("5. Visualizar Estrutura da Árvore");
            System.out.println("6. Exibir Métricas e Validação de Propriedades");
            System.out.println("7. Carregar dados de demonstração (50, 30, 70, 20, 40)");
            System.out.println("8. Limpar árvore");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Opção: ");

            opcaoRN = lerInteiro(scanner);

            switch (opcaoRN) {
                case 1:
                    System.out.print("Digite o identificador para inserir: ");
                    int valIns = lerInteiro(scanner);
                    rn.inserir(valIns);
                    System.out.println("Elemento processado. Rotações: " + rn.getTotalRotacoes() + " | Recolorações: " + rn.getTotalRecoloracoes());
                    break;
                case 2:
                    System.out.print("Digite o identificador para remover: ");
                    int valRem = lerInteiro(scanner);
                    rn.remover(valRem);
                    break;
                case 3:
                    System.out.print("Digite o identificador para buscar: ");
                    int valBusca = lerInteiro(scanner);
                    if (rn.buscar(valBusca)) {
                        System.out.println("[✓] Encomenda ID " + valBusca + " LOCALIZADA no sistema!");
                    } else {
                        System.out.println("[X] Encomenda ID " + valBusca + " NÃO encontrada.");
                    }
                    break;
                case 4:
                    rn.imprimirEmOrdem();
                    break;
                case 5:
                    rn.imprimirEstrutura();
                    break;
                case 6:
                    System.out.println("\n=== Diagnóstico da Árvore Rubro-Negra ===");
                    System.out.println("Quantidade de nós: " + rn.getQuantidadeNos());
                    System.out.println("Altura da árvore: " + rn.getAltura());
                    System.out.println("Altura negra (Black Height): " + rn.getAlturaNegra());
                    System.out.println("Total de rotações: " + rn.getTotalRotacoes());
                    System.out.println("Total de recolorações: " + rn.getTotalRecoloracoes());
                    System.out.println("Propriedades Rubro-Negras preservadas: " + (rn.validarPropriedades() ? "[VÁLIDA]" : "[VIOLADA]"));
                    break;
                case 7:
                    int[] demo = {50, 30, 70, 20, 40};
                    for (int d : demo) rn.inserir(d);
                    System.out.println("[✓] 5 identificadores de demonstração inseridos!");
                    break;
                case 8:
                    rn = new ArvoreRubroNegra();
                    System.out.println("[✓] Árvore reinicializada!");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("[!] Opção inválida!");
            }
        } while (opcaoRN != 0);
    }

    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("[!] Entrada inválida. Digite um número inteiro: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}